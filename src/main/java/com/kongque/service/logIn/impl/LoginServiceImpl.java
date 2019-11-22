package com.kongque.service.logIn.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kongque.entity.basics.VBasicDept;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.kongque.component.IRedisClient;
import com.kongque.constants.Constants;
import com.kongque.dao.IDaoService;
import com.kongque.dto.LoginDto;
import com.kongque.entity.basics.Dept;
import com.kongque.entity.basics.Tenant;
import com.kongque.entity.user.User;
import com.kongque.entity.user.UserDeptRelation;
import com.kongque.entity.user.UserRole;
import com.kongque.entity.user.UserRoleRelation;
import com.kongque.entity.user.UserSysRelation;
import com.kongque.model.LogInModel;
import com.kongque.service.logIn.ILoginService;
import com.kongque.service.user.IUserService;
import com.kongque.util.JsonUtil;
import com.kongque.util.Result;

@Service
public class LoginServiceImpl implements ILoginService {

	@Resource
	private IDaoService dao;
	
	@Resource
	private IUserService userService;
	
	@Resource
	private IRedisClient redisClient;

	@Override
	public Result login(LoginDto dto,HttpServletRequest request, HttpServletResponse response) {
		User findedUser = dao.findUniqueByProperty(User.class, "userName", dto.getUserName());
		if (findedUser == null) {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "您尚未注册！");
		}
		if (findedUser.getStatus().equals("1")) {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "您的账户无效！");
		}
		//1、判断用户名密码
		if (findedUser.getPassword().equals(DigestUtils.md5Hex(dto.getPassword()))) {
			//判断用户是否有登录此系统的权限
	        if(loginMenu(dto,findedUser)){
	        	// 2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
		        String token = UUID.randomUUID().toString().replaceAll("\\-", "");
		        // 3、把用户信息保存到redis。Key就是token，value就是findedUser对象转换成json。
		        // 4、使用String类型保存Session信息。可以使用token为key
		        redisClient.set(token,JsonUtil.objToJson(findedUser).toString());
		        // 5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
		        redisClient.expire(token, 1800);
		        //6、向cookie里保存值,用于单点登录
		        Cookie userCookie=new Cookie("sso",token); 
	            userCookie.setMaxAge(30*24*60*60);   //存活期为一个月 30*24*60*60
	            userCookie.setPath("/");
	            response.addCookie(userCookie);
		        LogInModel model = new LogInModel();
		        try {
					BeanUtils.copyProperties(model, findedUser);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
		        UserDeptRelation uerDeptRelation = dao.findUniqueByProperty(UserDeptRelation.class, "userId", findedUser.getId());
//		        Dept dept = dao.findById(Dept.class, uerDeptRelation.getDeptId());
//		        Tenant tenant = dept.getDeptTenantId();
				VBasicDept dept = dao.findById(VBasicDept.class, uerDeptRelation.getDeptId());
				Tenant tenant = dao.findById(Tenant.class,dept.getTenantId());
		        model.setTenant(tenant);
		        model.setToken(token);
		        model.setSysId(dto.getSysId()); 
		        Criteria criteria = dao.createCriteria(UserRoleRelation.class);
		        criteria.add(Restrictions.eq("userId", findedUser.getId()));
		        @SuppressWarnings("unchecked")
				List<UserRoleRelation> list = criteria.list();
		        List<UserRole> userRoleList = new ArrayList<>();
		        if(list!=null && list.size()>0){
		        	for (int i = 0; i < list.size(); i++) {
						UserRole role = dao.findById(UserRole.class, list.get(i).getRoleId());
						userRoleList.add(role);
					}
		        }
		        model.setUserRoleList(userRoleList);
		        Result result = userService.getMenuRole(model);
				return result;
	        }else{
	        	return new Result("500", "该账号没有权限登录此系统！");
	        }
		} else {
			return new Result(Constants.RESULT_CODE.SYS_ERROR, "密码错误！");
		}
	}
	
	@Override
	public Result logout(HttpServletRequest request, HttpServletResponse response,String token){
		String loginInfo = redisClient.get(token);
		if(loginInfo!=null){
			redisClient.remove(token);//删除Redis中保存的该登出用户的登录信息数据
			System.out.println(redisClient.get(token));
			//删除cookie中的值
			Cookie cookies[] = request.getCookies();  
		      if (cookies != null) {  
		          for (int i = 0; i < cookies.length; i++){  
		              if (cookies[i].getName().equals("sso")){  
		                  Cookie cookie = new Cookie("sso","");//这边得用"",不能用null  
		                  cookie.setPath("/");//设置成跟写入cookies一样的  
		                 // cookie.setDomain(".wangwz.com");//设置成跟写入cookies一样的  
		                  cookie.setMaxAge(0);  
		                  response.addCookie(cookie);  
		              }  
		          }  
		      }  
		}
		return new Result();
	}
	
	public boolean loginMenu(LoginDto dto,User findedUser){
		Criteria criteria1 = dao.createCriteria(UserSysRelation.class);
        criteria1.add(Restrictions.eq("userId", findedUser.getId()));
        @SuppressWarnings("unchecked")
		List<UserSysRelation> sysList = criteria1.list();
        for (UserSysRelation userSysRelation : sysList) {
        	if(userSysRelation.getSysId().equals(dto.getSysId())){
        		return true;
        	}
		}
        return false;
	}
	
}
