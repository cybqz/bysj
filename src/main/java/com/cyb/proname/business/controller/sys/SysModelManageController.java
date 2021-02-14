package com.cyb.proname.business.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.SysModel;
import com.cyb.authority.service.SysModelService;
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
 * @Description 系统模块管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/sysModelManage")
@ModelInfo(title ="系统模块管理", navbar = "系统模块管理", prefix = "sys/modelManage")
public class SysModelManageController extends BasicController {

	@Resource
	private SysModelService sysModelService;

	@Authentication(name = "保存系统模块", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_SAVE)
	@ResponseBody
	public Tips save(@RequestBody @Validated(AddValid.class) SysModel sysModel) {
		tips = new Tips("新增失败", true, false);
		SysModel sysModelTemp = sysModelService.selectByTitle(sysModel.getTitle());
		if(null == sysModelTemp){
			sysModel.setId(MyUtils.getPrimaryKey());
			int count = sysModelService.insert(sysModel);
			if(count > 0){
				tips = new Tips("新增成功", true, true);
			}
		}
		tips.setMsg("系统模块已存在");
		return tips;
	}

	@Authentication(name = "删除系统模块", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DELETE)
	@ResponseBody
	public Tips delete(@RequestBody JSONObject param) {
		tips = new Tips("删除失败", true, false);
		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			int count = sysModelService.deleteById(id);
			if(count > 0){
				tips = new Tips("删除成功", true, true);
			}
		}else{
			tips.setMsg("编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "更新系统模块", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DO_UPDATE)
	@ResponseBody
	public Tips doUpdate(@RequestBody @Validated(UpdateValid.class) SysModel sysModel) {
		tips = new Tips("更新失败", true, false);
		if(StringUtils.isNotEmpty(sysModel.getId())){
			SysModel updater = sysModelService.selectById(sysModel.getId());
			if(null != updater){
				if(null == sysModel.getSort()){
					tips.setMsg("排序值不能为空");
				}else if(StringUtils.isBlank(sysModel.getTitle())){
					tips.setMsg("标题不能为空");
				}else {
					if(StringUtils.isNotBlank(sysModel.getRemarks())){
						updater.setRemarks(sysModel.getRemarks());
					}
					if(StringUtils.isNotBlank(sysModel.getParentId())){
						updater.setParentId(sysModel.getParentId());
					}

					updater.setSort(sysModel.getSort());
					updater.setTitle(sysModel.getTitle());
					updater.setUpdateDateTime(LocalDateTime.now());

					boolean success = sysModelService.updateById(sysModel);
					if(success){
						tips = new Tips("更新成功", true, true);
					}else {
						tips.setMsg("更新失败");
					}
				}
			}else{
				tips.setMsg("系统模块不存在");
			}
		}else{
			tips.setMsg("编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "查询系统模块详情", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DETAIL)
	@ResponseBody
	public Tips detail(@RequestBody JSONObject param) {

		tips.setMsg("查询失败");
		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			SysModel sysModel = sysModelService.selectById(id);
			if(null != sysModel){
				tips = new Tips("查询成功",  true, sysModel);
			}
		}
		return tips;
	}

	@Authentication(name = "查询系统模块列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
		SysModel sysModel = param.getObject("sysModel", SysModel.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = sysModelService.selectCount(sysModel);
		if(count > 0){
			IPage<SysModel> page = sysModelService.selectPage(sysModel, pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询父栏目系统模块列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(value = "/parentPage")
	@ResponseBody
	public TipsPagination<Model> parentPage(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
		SysModel sysModel = param.getObject("sysModel", SysModel.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = sysModelService.selectParentCount(sysModel);
		if(count > 0){
			IPage<SysModel> page = sysModelService.selectParentPage(sysModel, pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询系统模块总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody SysModel sysModel) {
		int count = sysModelService.selectCount(sysModel);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}
}
