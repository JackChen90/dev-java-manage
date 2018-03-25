package cn.edu.njtech.manage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * mvc路径与url对应配置
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/admin/login").setViewName("admin/login");
		registry.addViewController("/fontIconList").setViewName("fontIcoList");
		registry.addViewController("/admin/home").setViewName("admin/home");
//		registry.addViewController("/user/manage").setViewName("admin/userInfo");
	}
}