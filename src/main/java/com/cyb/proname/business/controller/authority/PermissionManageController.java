package com.cyb.proname.business.controller.authority;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.Permission;
import com.cyb.authority.service.PermissionService;
import com.cyb.authority.vo.PermissionSearchVO;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.common.validation.group.AddValid;
import com.cyb.common.validation.group.UpdateValid;
import com.cyb.proname.annotation.ModelInfo;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.constant.SysCfgConstant;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author 陈迎博
 * @Description 权限管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/permissionManage")
@ModelInfo(title = "权限管理", prefix = "authority/permission")
public class PermissionManageController extends BasicController {

	@Resource
	private PermissionService permissionService;

	@Authentication(name = "保存权限", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_SAVE)
	@ResponseBody
	public Tips save(@RequestBody @Validated(AddValid.class) Permission permission) {
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
		return tips;
	}

	@Authentication(name = "删除权限", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DELETE)
	@ResponseBody
	public Tips delete(@RequestBody JSONObject param) {
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
		return tips;
	}

	@Authentication(name = "更新权限", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DO_UPDATE)
	@ResponseBody
	public Tips doUpdate(@RequestBody @Validated(UpdateValid.class) Permission permission) {
		tips = new Tips("更新失败", true, false);
		if(StringUtils.isNotEmpty(permission.getId())){
			permission.setUpdateDateTime(LocalDateTime.now());
			boolean success = permissionService.updateById(permission);
			if(success){
				tips = new Tips("更新成功", true, true);
			}
		}else{
			tips.setMsg("编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "查询权限详情", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DETAIL)
	@ResponseBody
	public Tips detail(@RequestBody JSONObject param) {

		tips.setMsg("查询失败");
		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			Permission permission = permissionService.selectById(id);
			if(null != permission){
				tips = new Tips("查询成功",  true, permission);
			}
		}
		return tips;
	}

	@Authentication(name = "查询权限列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
		PermissionSearchVO permission = param.getObject("permission", PermissionSearchVO.class);
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
		return tipsPagination;
	}

	@Authentication(name = "查询权限总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody PermissionSearchVO permission) {
		int count = permissionService.selectCount(permission);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}
}
