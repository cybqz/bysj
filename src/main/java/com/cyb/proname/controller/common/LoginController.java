package com.cyb.proname.controller.common;


import com.cyb.authority.base.BaseController;
import com.cyb.authority.domain.User;
import com.cyb.authority.service.LoginService;
import com.cyb.common.tips.Tips;
import com.cyb.proname.constant.SysCfgConstant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用户登录控制层
 */
@Controller
@RequestMapping(value="/login")
public class LoginController extends BaseController {
	
	@Resource
	private LoginService loginService;

	/**
	 * 登陆
	 * @param user
	 * @return Tips
	 */
	@RequestMapping(value="/login")
	@ResponseBody
	public Tips login (User user) {
		super.validLogined();
		if(isLogined){
			tips.setMsg("不能重复登陆");
			return tips;
		}
		return loginService.login(user);
	}

	/**
	 * 管理员登陆
	 * @param user
	 * @return Tips
	 */
	@RequestMapping(value="/adminlogin")
	@ResponseBody
	public Tips adminLogin (User user) {
		super.validLogined();
		if(isLogined){
			tips.setMsg("不能重复登陆");
			return tips;
		}
		return loginService.adminLogin(user, SysCfgConstant.ROLE_ADMIN);
	}

	/**
	 * 登出
	 * @return
	 */
	@RequestMapping(value="/logout")
	@ResponseBody
	public Tips logout () {
		super.validLogined();
		if(!isLogined){
			return tips;
		}
		return loginService.logout();
	}
}
