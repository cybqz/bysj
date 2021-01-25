package com.cyb.proname.business.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.domain.RolePermission;
import com.cyb.authority.service.PermissionService;
import com.cyb.authority.service.RolePermissionService;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

/**
 * @Author 陈迎博
 * @Description 角色权限管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/rolePermissionManage")
public class RolePermissionManageController extends BasicController {

	public String modelUrl = "/rolePermissionManage";
	public String modelName = "角色权限管理";

	@Resource
	private PermissionService permissionService;

	@Resource
	private RolePermissionService rolePermissionService;

	@PostMapping("addRolePermission")
	@ResponseBody
	public Tips addRolePermission(@RequestBody RolePermission rolePermission) {
		super.validLogined();
		if(isLogined){
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
		}
		return tips;
	}

	@PostMapping("deleteRolePermission")
	@ResponseBody
	public Tips deleteRolePermission(@RequestBody RolePermission rolePermission) {
		super.validLogined();
		if(isLogined){
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
		}
		return tips;
	}

	@PostMapping("selectPageHavNo")
	@ResponseBody
	public TipsPagination<Permission> selectPageHavNo(@RequestBody JSONObject param) {
		TipsPagination<Permission> tipsPagination = new TipsPagination<Permission>();
		super.validLogined();
		tipsPagination.convertFromTips(tips);
		if(isLogined) {
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
		}
		return tipsPagination;
	}

	@PostMapping("selectPageHav")
	@ResponseBody
	public TipsPagination<Permission> selectPageHav(@RequestBody JSONObject param) {
		TipsPagination<Permission> tipsPagination = new TipsPagination<Permission>();
		super.validLogined();
		tipsPagination.convertFromTips(tips);
		if(isLogined) {
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
		}
		return tipsPagination;
	}

	@PostMapping("selectCountHavNo")
	@ResponseBody
	public Tips selectCountHavNo(@RequestBody JSONObject param) {
		super.validLogined();
		if(isLogined) {
			String roleId = param.getString("roleId");
			int count = permissionService.selectCountHavNo(roleId);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}

	@PostMapping("selectCountByUserId")
	@ResponseBody
	public Tips selectCountByUserId(@RequestBody JSONObject param) {
		super.validLogined();
		if(isLogined) {
			String roleId = param.getString("roleId");
			RolePermission rolePermission = new RolePermission();
			rolePermission.setRoleId(roleId);
			int count = rolePermissionService.selectCount(rolePermission);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}
}
