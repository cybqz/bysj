package com.cyb.proname.business.controller.common;

import java.util.UUID;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.cyb.authority.domain.User;
import com.cyb.authority.service.UserService;
import com.cyb.common.tips.Tips;
import com.cyb.proname.business.vo.UserCreateVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author 陈迎博
 * @Description 用户注册控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value="/register")
public class RegisterController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping(value="/register")
	@ResponseBody
	public Tips register (UserCreateVO userCreate, HttpSession session) {
		Tips tips = new Tips("用户信息不能为空", false);
		if(userCreate != null) {
			String username = userCreate.getUserName();
			int sex = userCreate.getSex();
			if(StringUtils.isNotBlank(username) && StringUtils.isNotBlank(userCreate.getPassword()) &&
					(sex == 0 || sex == 1)) {
				{
					//检查用户名是否存在
					User userTemp = userService.selectByUserName(username);
					if(null != userTemp) {
						tips.setMsg("用户已存在！");
					}else {
						String url = session.getServletContext().getRealPath("/");
						User user = new User();
						BeanUtils.copyProperties(userCreate, user);
						String userId = UUID.randomUUID().toString();
						user.setId(userId);
						int count = userService.insert(user, url);
						if(count > 0) {
							tips = new Tips("注册成功", true);
						}
					}
				}
			}
		}
		return tips;
	}
}
