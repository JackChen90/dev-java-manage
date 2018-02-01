package cn.edu.njtech.manage.service.impl;

import cn.edu.njtech.manage.constant.RedisConstant;
import cn.edu.njtech.manage.dao.MenuInfoMapper;
import cn.edu.njtech.manage.domain.MenuInfo;
import cn.edu.njtech.manage.dto.MenuInfoDTO;
import cn.edu.njtech.manage.service.IMenuService;
import cn.edu.njtech.manage.util.GsonUtil;
import cn.edu.njtech.manage.util.RedisUtil;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author jackie chen
 * @create 2018/1/14
 * @description MenuServiceImpl
 */
@Service
public class MenuServiceImpl implements IMenuService {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根节点标识
	 */
	private static final Integer ROOT = -1;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	@Qualifier("M_MenuInfo")
	private MenuInfoMapper menuInfoMapper;

	@Override
	public List<MenuInfoDTO> queryMenuList(List<Integer> roleIds, Integer type) {
		logger.info("=== queryMenuList roleIds:{}, type:{} ===", roleIds, type);
		//获取角色对应的菜单列表
		List<MenuInfoDTO> menus = this.queryMenus(roleIds, type);
		//调整结果顺序，调整数据为树形结构（子节点在父节点下面）
		menus = sortMenus(menus);
		logger.info("=== queryMenuList success ===, menus size:{}", menus == null ? null : menus.size());
		return menus;
	}

	@Override
	public List<MenuInfoDTO> queryMenus(List<Integer> roleIds, Integer type) {
		logger.info("=== queryMenus roleIds:{}, type:{} ===", roleIds, type);

		List<MenuInfoDTO> menus = new ArrayList<>();
		List<MenuInfoDTO> items;
		//MenuInfoDTO list type
		Type typeToken = new TypeToken<ArrayList<MenuInfoDTO>>() {
		}.getType();
		for (Integer roleId :
				roleIds) {
			//redis中获取角色对应menu列表
			String menusStr = redisUtil.hGet(RedisConstant.KEY_ROLE_MENU, roleId + "_" + type + RedisConstant.ROLE_SUFFIX);
			if (StringUtils.isEmpty(menusStr)) {
				//redis中不存在，取db
				items = menuInfoMapper.queryMenuByRoleId(roleId, type);
				//入redis
				String menuItem = new GsonUtil().getDateSafeGson().toJson(items, typeToken);
				redisUtil.hSet(RedisConstant.KEY_ROLE_MENU, roleId + "_" + type + RedisConstant.ROLE_SUFFIX, menuItem);
			} else {
				//反序列化
				items = new GsonUtil().getDateSafeGson().fromJson(menusStr, typeToken);
			}
			menus.addAll(items);
		}
		logger.info("=== queryMenus success ===, menus size:{}", menus == null ? null : menus.size());

		return menus;
	}

	/**
	 * 调整结果顺序，调整数据为树形结构（子节点在父节点下面）
	 *
	 * @param menus db查询结果
	 */
	private List<MenuInfoDTO> sortMenus(List<MenuInfoDTO> menus) {
		//调整后的结果列表
		List<MenuInfoDTO> result = new ArrayList<>();
		//关系列表（记录父子关系）
		Map<Integer, Integer> relation = new HashMap<>();
		for (MenuInfoDTO dto :
				menus) {
			//为根节点
			if (dto.getParentId().equals(ROOT)) {
				//入结果list
				result.add(dto);
				//加入关系map
				relation.put(dto.getId(), dto.getParentId());
			} else {//不为父节点，则插入某个父节点的children列表
				//将节点插入某个父节点的children列表
				addChildren(result, dto, relation);
			}
		}
		return result;
	}

	/**
	 * 将节点插入某个父节点的children列表
	 *
	 * @param result   结果列表
	 * @param dto      当前节点
	 * @param relation 关系列表（记录父子关系）
	 */
	private void addChildren(List<MenuInfoDTO> result, MenuInfoDTO dto,
							 Map<Integer, Integer> relation) {
		//获取父节点列表
		List<Integer> parents = getParents(relation, dto);
		//当前父节点位置，逆序开始
		int index = parents.size();
		if (index > 0) {
			//递归添加menuTree子节点
			add(result, dto, parents, --index);
		}
	}

	/**
	 * 递归添加menuTree子节点
	 *
	 * @param list    children列表
	 * @param child   子节点
	 * @param parents 父节点列表
	 * @param index   当前父节点位置
	 */
	private void add(List<MenuInfoDTO> list, MenuInfoDTO child, List<Integer> parents,
					 int index) {
		//当前parentId
		Integer id = parents.get(index);
		for (MenuInfoDTO dto :
				list) {
			if (dto.getId().equals(id)) {
				//初始化children列表
				if (dto.getChildren() == null) {
					dto.setChildren(new ArrayList<>());
				}
				if (index == 0) {
					//增加子节点
					dto.getChildren().add(child);
				} else {
					list = dto.getChildren();
					index--;
					//递归增加child
					add(list, child, parents, index);
				}
			}
		}
	}

	/**
	 * 获取父节点列表
	 *
	 * @param relation 关系列表（记录父子关系）
	 * @param dto      当前节点
	 * @return
	 */
	private List<Integer> getParents(Map<Integer, Integer> relation, MenuInfoDTO dto) {
		List<Integer> parents = new ArrayList<>();
		//逐级增加父节点
		addParent(parents, relation, dto.getParentId());
		return parents;
	}

	/**
	 * 逐级获取父节点id并放入list
	 *
	 * @param parents  父节点id列表
	 * @param relation 关系列表
	 * @param parentId 父节点id
	 */
	private void addParent(List<Integer> parents, Map<Integer, Integer> relation,
						   Integer parentId) {
		if (!parentId.equals(ROOT)) {
			//sql查出来是按照层级正序排列，父节点一定在relation中
			Integer tempParent = relation.get(parentId);
			if (null != tempParent) {
				parents.add(parentId);
				parentId = tempParent;
				//递归获取parents节点
				addParent(parents, relation, parentId);
			}
		}
	}
}
