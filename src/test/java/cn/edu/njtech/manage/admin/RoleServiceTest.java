package cn.edu.njtech.manage.admin;

import cn.edu.njtech.manage.ManageApplication;
import cn.edu.njtech.manage.dto.RoleInfoDTO;
import cn.edu.njtech.manage.service.admin.IRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author jackie chen
 * @create 2018/2/10
 * @description RoleServiceTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ManageApplication.class)
public class RoleServiceTest {

	@Autowired
	private IRoleService roleService;

	@Test
	public void test1(){
		List<RoleInfoDTO> result = roleService.queryRoleInfo4Select();
	}
}
