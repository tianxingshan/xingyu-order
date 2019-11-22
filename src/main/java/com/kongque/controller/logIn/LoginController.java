package com.kongque.controller.logIn;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kongque.dto.LoginDto;
import com.kongque.service.logIn.ILoginService;
import com.kongque.util.Result;


@Controller
public class LoginController {
	private final static Logger log = LoggerFactory.getLogger(LoginController.class);
	@Resource
	private ILoginService service;

	/**
	 * 登录接口
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/login", method= RequestMethod.POST)
	public @ResponseBody Result login(@RequestBody LoginDto dto,HttpServletRequest request, HttpServletResponse response) {
		log.error("testlog");
		return service.login(dto,request,response);
	}
	
	
	/**
	 * 登出接口
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/logout", method= RequestMethod.GET)
	public @ResponseBody Result logout(HttpServletRequest request, HttpServletResponse response,String token) {
		log.error("testlog");
		return service.logout(request,response,token);
	}
	
}
