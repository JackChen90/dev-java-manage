package cn.edu.njtech.manage;

import cn.edu.njtech.manage.dto.MenuInfoDTO;
import cn.edu.njtech.manage.service.IMenuService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jackie chen
 * @create 2018/1/16
 * @description MenuServiceTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ManageApplication.class)
public class MenuServiceTest {

	@Autowired
	private IMenuService menuService;

	@Test
	public void queryMenuListTest() {
		List<Integer> roles = new ArrayList<>();
		roles.add(1);
		Integer type = 1;

		List<MenuInfoDTO> result = menuService.queryMenuList(roles, type);
		Type t = new TypeToken<ArrayList<MenuInfoDTO>>() {
		}.getType();
		System.out.println(new Gson().toJson(result, t));
		Assume.assumeNotNull(result);
	}
}
