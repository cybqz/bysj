package com.cyb.proname.business.controller.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import com.cyb.authority.base.BaseController;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.domain.RolePermission;
import com.cyb.authority.domain.User;
import com.cyb.authority.domain.UserRole;
import com.cyb.authority.service.PermissionService;
import com.cyb.authority.service.RolePermissionService;
import com.cyb.authority.service.UserRoleService;
import com.cyb.authority.service.UserService;
import com.cyb.common.tips.Tips;
import com.cyb.proname.constant.SysCfgConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.cyb.proname.business.vo.UserRolePermissionVO;
import com.cyb.proname.business.vo.RolePermissionVO;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 陈迎博
 * @Description 用户管理控制层
 * @Date 2021/1/30
 */
@Controller
@RequestMapping(value="/user")
public class UserController extends BaseController {

	@Resource
	private UserService userSerivce;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private PermissionService permissionService;
	@Resource
	private RolePermissionService rolePermissionService;
	
	@RequestMapping(value="/update")
	@ResponseBody
	public Tips updateIntroduce (User param) {
		super.validLogined();
		if(isLogined) {
			if(StringUtils.isBlank(param.getIntroduce())) {
				tips.setMsg("用户简介不能为空！");
			}else if(StringUtils.isBlank(param.getUserName())){
				tips.setMsg("用户名称不能为空！");
			}else {
				currentLoginedUser.setIntroduce(param.getIntroduce());
				boolean success = userSerivce.updateById(currentLoginedUser);
				if(success) {
					tips = new Tips("修改成功！", true);
				}else {
					tips.setMsg("修改失败！");
				}
			}
		}
		return tips;
	}

	@GetMapping("/updatePassword")
	public String updatePassword (HttpServletRequest request) {
		super.validLogined();
		if(!isLogined) {
			request.setAttribute("msg","请登录后再修改密码");
		}
		return SysCfgConstant.DEFAULT_PAGE_PREFIX + "update_password";
	}

	@RequestMapping(value="/doUpdatePassword")
	@ResponseBody
	public Tips doUpdatePassword (@Param("password") String password, @Param("oldPassword")String oldPassword) {
		super.validLogined();
		if(isLogined) {
			tips = userSerivce.updatePasswordById(currentLoginedUser.getId(), password, oldPassword, true);
		}
		return tips;
	}
	
	@RequestMapping(value="/updateImage")
	@ResponseBody
	public Tips updateImage (@RequestParam(value = "file", required = true) MultipartFile pictureFile) {
		super.validLogined();
		if(isLogined) {
            try {
            	if(pictureFile != null) {
            		// 图片新名字
                    String newName = UUID.randomUUID().toString();
                    // 图片原来的名字
                    String oldName = pictureFile.getOriginalFilename();
                    // 后缀
                    String sux = oldName.substring(oldName.lastIndexOf("."));
                     //新建本地文件流
                    //File file = new File("D:\\SSMBasic\\src\\main\\webapp\\WEB-INF\\img\\" + newName + sux);
                    // 写入本地磁盘
    				//pictureFile.transferTo(file);
    				tips = new Tips("true", true);
            	}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			}
		}
		return tips;
	}
	
	@RequestMapping(value="/getSignedIndUser")
	@ResponseBody
	public Tips getSignedIndUser () {
		super.validLogined();
		if(isLogined) {
			tips = new Tips("查询成功", true, currentLoginedUser);
		}
		return tips;
	}
}
