package com.cyb.proname.business.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.User;
import com.cyb.authority.domain.UserRole;
import com.cyb.authority.service.RoleService;
import com.cyb.authority.service.UserRoleService;
import com.cyb.authority.service.UserService;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.annotation.ModelInfo;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.constant.SysCfgConstant;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 陈迎博
 * @Description 用户角色管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/userRoleManage")
@ModelInfo(title = "用户角色管理", navbar = "角色管理", prefix = "user/role")
public class UserRoleManageController extends BasicController {

	@Resource
	private RoleService roleService;

	@Resource
	private UserService userService;

	@Resource
	private UserRoleService userRoleService;

	@RequestMapping("/editRole")
	public String updateAuthority(String id, HttpServletRequest request) {
		setModelInfo("/userRoleManage");
		request.setAttribute("operationId", id);
		request.setAttribute("modelUrl", modelUrl);
		request.setAttribute("title", modelName +"-角色编辑");
		return prefix + "/editRole";
	}

	@Authentication(name = "保存角色", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("addUserRole")
	@ResponseBody
	public Tips addUserRole(@RequestBody UserRole userRole) {
		tips = new Tips("新增失败", true, false);

		if(StringUtils.isBlank(userRole.getUserId())){
			tips.setMsg("用户编号不能为空");
		}else if(StringUtils.isBlank(userRole.getRoleId())){
			tips.setMsg("角色编号不能为空");
		}else{

			int count = userRoleService.selectCount(userRole);
			if(count > 0){
				tips.setMsg("当前用户已拥有此角色");
			}else{
				userRole.setId(MyUtils.getPrimaryKey());
				userRoleService.insert(userRole);
				tips = new Tips("新增成功", true, true);
			}
		}
		return tips;
	}

	@Authentication(name = "删除角色", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("deleteUserRole")
	@ResponseBody
	public Tips deleteUserRole(@RequestBody UserRole userRole) {
		tips = new Tips("新增失败", true, false);

		if(StringUtils.isBlank(userRole.getUserId())){
			tips.setMsg("用户编号不能为空");
		}else if(StringUtils.isBlank(userRole.getRoleId())){
			tips.setMsg("角色编号不能为空");
		}else{
			UserRole userRoleTmp = userRoleService.selectOne(userRole);
			if(null != userRoleTmp){
				userRoleService.deleteById(userRoleTmp.getId());
				tips = new Tips("删除成功", true, true);
			}else{
				tips.setMsg("当前用户未拥有此角色");
			}
		}
		return tips;
	}

	@Authentication(name = "查询未拥有角色列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectPageHavNo")
	@ResponseBody
	public TipsPagination<Role> selectPageHavNo(@RequestBody JSONObject param) {
		TipsPagination<Role> tipsPagination = new TipsPagination<Role>();
		tipsPagination.convertFromTips(tips);
		UserRole userRole = param.getObject("userRole", UserRole.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = roleService.selectCountHavNo(userRole.getUserId());
		if(count > 0){
			IPage<Role> page = roleService.selectPageHavNo(userRole.getUserId(), pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询角色列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectPageHav")
	@ResponseBody
	public TipsPagination<Role> selectPageHav(@RequestBody JSONObject param) {
		TipsPagination<Role> tipsPagination = new TipsPagination<Role>();
		tipsPagination.convertFromTips(tips);
		UserRole userRole = param.getObject("userRole", UserRole.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = userRoleService.selectCount(userRole);
		if(count > 0){
			IPage<Role> page = roleService.selectPageHav(userRole.getUserId(), pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询用户未拥有角色总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectCountHavNo")
	@ResponseBody
	public Tips selectCountHavNo(@RequestBody JSONObject param) {
		String userId = param.getString("userId");
		int count = roleService.selectCountHavNo(userId);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}

	@Authentication(name = "查询用户角色总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectCountByUserId")
	@ResponseBody
	public Tips selectCountByUserId(@RequestBody JSONObject param) {
		String userId = param.getString("userId");
		UserRole userRole = new UserRole();
		userRole.setUserId(userId);
		int count = userRoleService.selectCount(userRole);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}

	@Authentication(name = "查询用户列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
		User user = param.getObject("user", User.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = userService.selectCount(user);
		if(count > 0){
			IPage<User> page = userService.selectPage(user, pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询用户总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody User user) {
		int count = userService.selectCount(user);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}
}
