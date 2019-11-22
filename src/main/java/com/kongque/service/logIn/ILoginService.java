package com.kongque.service.logIn;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.dto.LoginDto;
import com.kongque.util.Result;

public interface ILoginService {
	Result login(LoginDto dto,HttpServletRequest request, HttpServletResponse response);
	
	Result logout(HttpServletRequest request, HttpServletResponse response,String token);

}
