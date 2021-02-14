package com.cyb.proname.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.base.BaseController;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.annotation.ModelInfo;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.constant.SysCfgConstant;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.business.service.ModelService;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 模块管理控制层
 */
@Controller
@RequestMapping(value= "/model")
@ModelInfo(title = "模板模块管理", navbar ="", prefix = "jsp/model")
public class ModelController extends BasicController {

	@Resource
	private ModelService modelService;

	@Authentication(name = "保存", roleNames = {"teamMeb"})
	@PostMapping(SysCfgConstant.METHOD_URL_SAVE)
	@ResponseBody
	public Tips save(@RequestBody Model model) {
		tips = new Tips("新增失败", true, false);
		model.setId(MyUtils.getPrimaryKey());
		model.setCreateDateTime(LocalDateTime.now());
		int count = modelService.insert(model);
		if(count > 0){
			tips = new Tips("新增成功", true, true);
		}
		return tips;
	}

	@Authentication(name = "删除", roleNames = {"teamMeb"})
	@PostMapping(SysCfgConstant.METHOD_URL_DELETE)
	@ResponseBody
	public Tips delete(@RequestBody JSONObject param) {
		tips = new Tips("删除失败", true, false);
		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			int count = modelService.deleteById(id);
			if(count > 0){
				tips = new Tips("删除成功", true, true);
			}
		}else{
			tips.setMsg("编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "更新", roleNames = {"teamMeb"})
	@PostMapping(SysCfgConstant.METHOD_URL_DO_UPDATE)
	@ResponseBody
	public Tips doUpdate(@RequestBody Model model) {
		tips = new Tips("更新失败", true, false);
		if(StringUtils.isNotEmpty(model.getId())){
			model.setUpdateDateTime(LocalDateTime.now());
			boolean success = modelService.updateById(model);
			if(success){
				tips = new Tips("更新成功", true, true);
			}
		}else{
			tips.setMsg("编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "查询详情", roleNames = {"teamMeb"})
	@PostMapping(SysCfgConstant.METHOD_URL_DETAIL)
	@ResponseBody
	public Tips detail(@RequestBody JSONObject param) {

		tips.setMsg("查询失败");
		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			Model customerInfo = modelService.selectById(id);
			if(null != customerInfo){
				tips = new Tips("查询成功",  true, customerInfo);
			}
		}
		return tips;
	}

	@Authentication(name = "查询列表", roleNames = {"teamMeb"})
	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
		Model model = param.getObject("model", Model.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = modelService.count(model);
		if(count > 0){
			IPage<Model> page = modelService.selectPage(model, pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询总数", roleNames = {"teamMeb"})
	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody Model model) {
		int count = modelService.count(model);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}
}
