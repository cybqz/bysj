package com.cyb.proname.business.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.domain.Role;
import com.cyb.authority.domain.UserRole;
import com.cyb.authority.service.RoleService;
import com.cyb.authority.service.UserRoleService;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.constant.SysCfgConstant;
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
 * @Description 用户角色管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/userRoleManage")
public class UserRoleManageController extends BasicController {

	public String modelUrl = "/userRoleManage";
	public String modelName = "用户角色管理";

	@Resource
	private RoleService roleService;

	@Resource
	private UserRoleService userRoleService;

	@PostMapping("addUserRole")
	@ResponseBody
	public Tips addUserRole(@RequestBody UserRole userRole) {
		super.validLogined();
		if(isLogined){
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
		}
		return tips;
	}

	@PostMapping("deleteUserRole")
	@ResponseBody
	public Tips deleteUserRole(@RequestBody UserRole userRole) {
		super.validLogined();
		if(isLogined){
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
		}
		return tips;
	}

	@PostMapping("selectPageHavNo")
	@ResponseBody
	public TipsPagination<Role> selectPageHavNo(@RequestBody JSONObject param) {
		TipsPagination<Role> tipsPagination = new TipsPagination<Role>();
		super.validLogined();
		tipsPagination.convertFromTips(tips);
		if(isLogined) {
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
		}
		return tipsPagination;
	}

	@PostMapping("selectPageHav")
	@ResponseBody
	public TipsPagination<Role> selectPageHav(@RequestBody JSONObject param) {
		TipsPagination<Role> tipsPagination = new TipsPagination<Role>();
		super.validLogined();
		tipsPagination.convertFromTips(tips);
		if(isLogined) {
			UserRole userRole = param.getObject("userRole", UserRole.class);
			Pagination pagination = param.getObject("pagination", Pagination.class);
			int count = userRoleService.selectCountByUserId(userRole.getUserId());
			if(count > 0){
				IPage<Role> page = roleService.selectPageHav(userRole.getUserId(), pagination);
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
			String userId = param.getString("userId");
			int count = roleService.selectCountHavNo(userId);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}

	@PostMapping("selectCountByUserId")
	@ResponseBody
	public Tips selectCountByUserId(@RequestBody JSONObject param) {
		super.validLogined();
		if(isLogined) {
			String userId = param.getString("userId");
			int count = userRoleService.selectCountByUserId(userId);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}
}
