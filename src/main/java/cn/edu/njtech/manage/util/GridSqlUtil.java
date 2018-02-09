package cn.edu.njtech.manage.util;

import cn.edu.njtech.manage.dto.GridDataDTO;
import org.springframework.stereotype.Component;

/**
 * @author jackie chen
 * @create 2018/2/8
 * @description GridSqlUtil
 */
public class GridSqlUtil {
	//分组标记
	private static final String GROUP_TAG = ",";

	/**
	 * 构建搜索sql语句
	 *
	 * @param dto 入参
	 * @return
	 */
	public static String createSearchSql(GridDataDTO dto) {
		StringBuilder searchBuilder = new StringBuilder();
		searchBuilder.append(dto.getSearchField());
		searchBuilder.append(GridSearchOperation.strToEnum(dto.getSearchOper()).getOperationStr(dto.getSearchString()));
		return searchBuilder.toString();
	}


	/**
	 * 构建排序sql
	 *
	 * @param sidx 排序列
	 * @param sord asc/desc
	 * @return
	 */
	public static String createOrderSql(String sidx, String sord) {
		sidx = sidx.trim();
		if (sidx.endsWith(GROUP_TAG)) {
			sidx = sidx.substring(0, sidx.length() - 1);
			return " order by " + sidx;
		} else {
			return " order by " + sidx + " " + sord;
		}
	}
}
