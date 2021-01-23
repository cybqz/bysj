package com.cyb.proname.business.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.base.BaseController;
import com.cyb.authority.domain.Role;
import com.cyb.authority.service.RoleService;
import com.cyb.authority.service.RoleService;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
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
public class RoleManageController extends BaseController {

	private final static String MODEL_URL = "/roleManage";
	private final static String MODEL_NAME = "角色管理";

	@Resource
	private RoleService roleService;

	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		request.setAttribute("model", MODEL_NAME);
		request.setAttribute("modelUrl", MODEL_URL);
		request.setAttribute("title", MODEL_NAME +"-列表");
		return SysCfgConstant.DEFAULT_PAGE_PREFIX + MODEL_URL + SysCfgConstant.DEFAULT_LIST_PAGE_SUFFIX;
	}

	@RequestMapping(SysCfgConstant.METHOD_URL_ADD)
	public String add(HttpServletRequest request) {
		request.setAttribute("modelUrl", MODEL_URL);
		request.setAttribute("title", MODEL_NAME +"-新增");
		return SysCfgConstant.DEFAULT_PAGE_PREFIX + MODEL_URL + SysCfgConstant.METHOD_URL_ADD;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_SAVE)
	@ResponseBody
	public Tips save(@RequestBody Role role, HttpSession session) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("新增失败", true, false);
			Role roleTemp = roleService.selectByName(role.getName());
			if(null == roleTemp){
				role.setId(MyUtils.getPrimaryKey());
				int count = roleService.insert(role);
				if(count > 0){
					tips = new Tips("新增成功", true, true);
				}
			}
			tips.setMsg("权限已存在");
		}
		return tips;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_DELETE)
	@ResponseBody
	public Tips delete(@RequestBody JSONObject param) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("删除失败", true, false);
			String id = MyUtils.getByJSONObject(param, "id");
			if(StringUtils.isNotEmpty(id)){
				int count = roleService.deleteById(id);
				if(count > 0){
					tips = new Tips("删除成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}

	@RequestMapping(SysCfgConstant.METHOD_URL_UPDATE)
	public String update(String id, HttpServletRequest request) {
		request.setAttribute("modelUrl", MODEL_URL);
		request.setAttribute("title", MODEL_NAME +"-更新");
		request.setAttribute("operationId", id);
		return SysCfgConstant.DEFAULT_PAGE_PREFIX + MODEL_URL + SysCfgConstant.METHOD_URL_UPDATE;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_DO_UPDATE)
	@ResponseBody
	public Tips doUpdate(@RequestBody Role role) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("更新失败", true, false);
			if(StringUtils.isNotEmpty(role.getId())){
				boolean success = roleService.updateById(role);
				if(success){
					tips = new Tips("更新成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_DETAIL)
	@ResponseBody
	public Tips detail(@RequestBody JSONObject param) {

		super.validLogined();
		if(isLogined) {
			tips.setMsg("查询失败");
			String id = MyUtils.getByJSONObject(param, "id");
			if(StringUtils.isNotEmpty(id)){
				Role role = roleService.selectById(id);
				if(null != role){
					tips = new Tips("查询成功",  true, role);
				}
			}
		}
		return tips;
	}

	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		super.validLogined();
		tipsPagination.convertFromTips(tips);
		if(isLogined) {
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
		}
		return tipsPagination;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody Role role) {
		super.validLogined();
		if(isLogined) {
			int count = roleService.selectCount(role);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}
}
