package com.cyb.proname.business.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.base.BaseController;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.service.PermissionService;
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
 * @Description 权限管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/permissionManage")
public class PermissionManageController extends BaseController {

	private final static String MODEL_URL = "/permissionManage";
	private final static String MODEL_NAME = "权限管理";

	@Resource
	private PermissionService permissionService;

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
	public Tips save(@RequestBody Permission permission, HttpSession session) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("新增失败", true, false);
			Permission permissionTemp = permissionService.selectByName(permission.getName());
			if(null == permissionTemp){
				permission.setId(MyUtils.getPrimaryKey());
				int count = permissionService.insert(permission);
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
				int count = permissionService.deleteById(id);
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
	public Tips doUpdate(@RequestBody Permission permission) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("更新失败", true, false);
			if(StringUtils.isNotEmpty(permission.getId())){
				boolean success = permissionService.updateById(permission);
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
				Permission permission = permissionService.selectById(id);
				if(null != permission){
					tips = new Tips("查询成功",  true, permission);
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
			Permission permission = param.getObject("permission", Permission.class);
			Pagination pagination = param.getObject("pagination", Pagination.class);
			int count = permissionService.selectCount(permission);
			if(count > 0){
				IPage<Permission> page = permissionService.selectPage(permission, pagination);
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
	public Tips count(@RequestBody Permission permission) {
		super.validLogined();
		if(isLogined) {
			int count = permissionService.selectCount(permission);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}
}
