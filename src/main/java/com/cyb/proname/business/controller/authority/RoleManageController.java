package com.cyb.proname.business.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.Role;
import com.cyb.authority.service.RoleService;
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
import javax.servlet.http.HttpSession;

/**
 * @Author 陈迎博
 * @Description 角色管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/roleManage")
@ModelInfo(title = "角色管理", prefix = "jsp/roleManage")
public class RoleManageController extends BasicController {

	@Resource
	private RoleService roleService;

	@RequestMapping("/editPermission")
	public String editPermission(String id, HttpServletRequest request) {
		setModelInfo("/roleManage");
		request.setAttribute("operationId", id);
		request.setAttribute("modelUrl", modelUrl);
		request.setAttribute("title", modelName +"-权限编辑");
		return prefix + "/editPermission";
	}

	@Authentication(name = "保存角色", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_SAVE)
	@ResponseBody
	public Tips save(@RequestBody Role role, HttpSession session) {
		tips = new Tips("新增失败", true, false);
		Role roleTemp = roleService.selectByName(role.getName());
		if(null == roleTemp){
			role.setId(MyUtils.getPrimaryKey());
			int count = roleService.insert(role);
			if(count > 0){
				tips = new Tips("新增成功", true, true);
			}
		}
		tips.setMsg("角色已存在");
		return tips;
	}

	@Authentication(name = "删除角色", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DELETE)
	@ResponseBody
	public Tips delete(@RequestBody JSONObject param) {
		tips = new Tips("删除失败", true, false);
		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			int count = roleService.deleteById(id);
			if(count > 0){
				tips = new Tips("删除成功", true, true);
			}
		}else{
			tips.setMsg("角色编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "更新角色", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DO_UPDATE)
	@ResponseBody
	public Tips doUpdate(@RequestBody Role role) {
		tips = new Tips("更新失败", true, false);
		if(StringUtils.isNotEmpty(role.getId())){
			boolean success = roleService.updateById(role);
			if(success){
				tips = new Tips("更新成功", true, true);
			}
		}else{
			tips.setMsg("角色编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "查询角色详情", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DETAIL)
	@ResponseBody
	public Tips detail(@RequestBody JSONObject param) {

		tips.setMsg("查询失败");
		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			Role role = roleService.selectById(id);
			if(null != role){
				tips = new Tips("查询成功",  true, role);
			}
		}
		return tips;
	}

	@Authentication(name = "查询角色列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
		Role role = param.getObject("role", Role.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = roleService.selectCount(role);
		if(count > 0){
			IPage<Role> page = roleService.selectPage(role, pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}


	@Authentication(name = "查询角色总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody Role role) {
		int count = roleService.selectCount(role);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}
}
