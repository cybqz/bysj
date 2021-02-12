package com.cyb.proname.business.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.RolePermission;
import com.cyb.authority.service.PermissionService;
import com.cyb.authority.service.RolePermissionService;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.annotation.ModelInfo;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.business.utils.RoleUtilService;
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
 * @Description 角色权限管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/rolePermissionManage")
@ModelInfo(title = "角色权限管理", prefix = "authority/rolepermission")
public class RolePermissionManageController extends BasicController {

	@Resource
	private RoleUtilService roleUtilService;

	@Resource
	private PermissionService permissionService;

	@Resource
	private RolePermissionService rolePermissionService;

	@RequestMapping("/editPermission")
	public String updateAuthority(String id, HttpServletRequest request) {
		setModelInfo("/rolePermissionManage");
		request.setAttribute("operationId", id);
		request.setAttribute("modelUrl", modelUrl);
		request.setAttribute("title", modelName +"-权限编辑");
		return prefix + "/editPermission";
	}

	@Authentication(name = "添加角色权限", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("addRolePermission")
	@ResponseBody
	public Tips addRolePermission(@RequestBody RolePermission rolePermission) {
		tips = new Tips("新增失败", true, false);

		if(StringUtils.isBlank(rolePermission.getRoleId())){
			tips.setMsg("角色编号不能为空");
		}else if(StringUtils.isBlank(rolePermission.getPermissionId())){
			tips.setMsg("权限编号不能为空");
		}else{

			int count = rolePermissionService.selectCount(rolePermission);
			if(count > 0){
				tips.setMsg("当前角色已拥有此权限");
			}else{
				rolePermission.setId(MyUtils.getPrimaryKey());
				rolePermissionService.insert(rolePermission);
				tips = new Tips("新增成功", true, true);
			}
		}
		return tips;
	}

	@Authentication(name = "删除角色权限", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("deleteRolePermission")
	@ResponseBody
	public Tips deleteRolePermission(@RequestBody RolePermission rolePermission) {
		tips = new Tips("新增失败", true, false);

		if(StringUtils.isBlank(rolePermission.getRoleId())){
			tips.setMsg("角色编号不能为空");
		}else if(StringUtils.isBlank(rolePermission.getPermissionId())){
			tips.setMsg("权限编号不能为空");
		}else{
			RolePermission rolePermissionTmp = rolePermissionService.selectOne(rolePermission);
			if(null != rolePermissionTmp){
				rolePermissionService.deleteById(rolePermissionTmp.getId());
				tips = new Tips("删除成功", true, true);
			}else{
				tips.setMsg("当前角色未拥有此权限");
			}
		}
		return tips;
	}

	@Authentication(name = "查询角色详情", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("roleDetail")
	@ResponseBody
	public Tips roleDetail(@RequestBody JSONObject param) {
		tips.setMsg("查询失败");
		Role role = roleUtilService.detail(param);
		if(null != role){
			tips = new Tips("查询成功",  true, role);
		}
		return tips;
	}

	@Authentication(name = "查询权限列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectPageHavNo")
	@ResponseBody
	public TipsPagination<Permission> selectPageHavNo(@RequestBody JSONObject param) {
		TipsPagination<Permission> tipsPagination = new TipsPagination<Permission>();
		tipsPagination.convertFromTips(tips);
		RolePermission rolePermission = param.getObject("rolePermission", RolePermission.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = permissionService.selectCountHavNo(rolePermission.getRoleId());
		if(count > 0){
			IPage<Permission> page = permissionService.selectPageHavNo(rolePermission.getRoleId(), pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询角色权限总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectPageHav")
	@ResponseBody
	public TipsPagination<Permission> selectPageHav(@RequestBody JSONObject param) {
		TipsPagination<Permission> tipsPagination = new TipsPagination<Permission>();
		tipsPagination.convertFromTips(tips);
		RolePermission rolePermission = param.getObject("rolePermission", RolePermission.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = rolePermissionService.selectCount(rolePermission);
		if(count > 0){
			IPage<Permission> page = permissionService.selectPageHav(rolePermission.getRoleId(), pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询角色未拥有权限总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectCountHavNo")
	@ResponseBody
	public Tips selectCountHavNo(@RequestBody JSONObject param) {
		String roleId = param.getString("roleId");
		int count = permissionService.selectCountHavNo(roleId);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}

	@Authentication(name = "查询角色权限总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectCountByUserId")
	@ResponseBody
	public Tips selectCountByUserId(@RequestBody JSONObject param) {
		String roleId = param.getString("roleId");
		RolePermission rolePermission = new RolePermission();
		rolePermission.setRoleId(roleId);
		int count = rolePermissionService.selectCount(rolePermission);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}

	@Authentication(name = "查询角色列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
		roleUtilService.page(param, tipsPagination);
		return tipsPagination;
	}

	@Authentication(name = "查询角色总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody Role role) {
		return roleUtilService.selectCount(role);
	}
}
