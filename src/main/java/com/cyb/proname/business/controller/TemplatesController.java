package com.cyb.proname.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.annotation.ModelInfo;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.business.vo.ModelSearchVO;
import com.cyb.proname.constant.SysCfgConstant;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.business.service.TemplatesService;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 模块管理控制层
 */
@Controller
@RequestMapping(value= "/templates")
@ModelInfo(title = "模板模块管理", navbar ="", prefix = "/templates")
public class TemplatesController extends BasicController {

	@Resource
	private TemplatesService templatesService;

	@Authentication(name = "保存", roleNames = {"teamMeb"})
	@PostMapping(SysCfgConstant.METHOD_URL_SAVE)
	@ResponseBody
	public Tips save(@RequestBody Model model) {
		tips = new Tips("新增失败", true, false);
		model.setId(MyUtils.getPrimaryKey());
		model.setCreateDateTime(LocalDateTime.now());
		int count = templatesService.insert(model);
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
			int count = templatesService.deleteById(id);
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
			boolean success = templatesService.updateById(model);
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
			Model customerInfo = templatesService.selectById(id);
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
		ModelSearchVO model = param.getObject("model", ModelSearchVO.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = templatesService.count(model);
		if(count > 0){
			IPage<Model> page = templatesService.selectPage(model, pagination);
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
	public Tips count(@RequestBody ModelSearchVO model) {
		int count = templatesService.count(model);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}
}
