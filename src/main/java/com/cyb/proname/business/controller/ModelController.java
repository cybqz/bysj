package com.cyb.proname.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.base.BaseController;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.constant.SysCfgConstant;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.business.service.ModelService;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 客户信息管理Controller
 */
@Controller
@RequestMapping(value= "/model")
public class ModelController extends BaseController {

	private final static String MODEL_URL = "/model";
	private final static String MODEL_NAME = "模板模块管理";

	@Resource
	private ModelService modelService;

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
	public Tips save(@RequestBody Model model) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("新增失败", true, false);
			model.setId(MyUtils.getPrimaryKey());
			model.setCreateDateTime(new Date());
			int count = modelService.insert(model);
			if(count > 0){
				tips = new Tips("新增成功", true, true);
			}
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
				int count = modelService.deleteById(id);
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
	public Tips doUpdate(@RequestBody Model model) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("更新失败", true, false);
			if(StringUtils.isNotEmpty(model.getId())){
				boolean success = modelService.updateById(model);
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
				Model customerInfo = modelService.selectById(id);
				if(null != customerInfo){
					tips = new Tips("查询成功",  true, customerInfo);
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
		}
		return tipsPagination;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody Model model) {
		super.validLogined();
		if(isLogined) {
			int count = modelService.count(model);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}
}
