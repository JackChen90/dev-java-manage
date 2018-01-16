package cn.edu.njtech.manage.controller;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author jackie chen
 * @create 2018/1/11
 * @description UserController
 */
@Controller
public class UserController {

	@RequestMapping(value = "/")
	public ModelAndView home(HttpServletRequest request) {
		request.getContentType();
		ModelAndView result = new ModelAndView("home");
		return result;
	}

//	@RequestMapping(value = "/login")
//	public ModelAndView login(HttpServletRequest request) {
//		request.getContentType();
//		ModelAndView result = new ModelAndView("login");
//		return result;
//	}
}
