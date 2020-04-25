package com.cyb.engcostms.controller;

import com.cyb.authority.base.BaseController;
import com.cyb.common.tips.Tips;
import com.cyb.engcostms.common.Constant;
import com.cyb.engcostms.domain.Material;
import com.cyb.engcostms.domain.Parames;
import com.cyb.engcostms.service.ParamesServices;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/parames")
public class ParamesController extends BaseController {

	@Autowired
	private ParamesServices paramesServices;

	/**
	 * 新增物料类型
	 * @param name
	 * @return
	 */
	@ResponseBody
	@PostMapping("/saveMaterialType")
	public Tips saveMaterialType(String name) {
		super.validLogined();
		if (isLogined) {
			Parames parames = new Parames(null, name, null, Constant.MATERIAL_TYPE);
			List<Parames> paramesList  = paramesServices.selectByParames(parames);
			if(null == paramesList || CollectionUtils.isEmpty(paramesList)){
				parames.setValue(name);
				int count = paramesServices.insert(parames);
				if (count > 0) {
					tips.setMsg("保存成功");
				}
			}else{
				tips.setMsg("物料类型已存在");
			}
		}
		return tips;
	}

	/**
	 * 查询物料类型
	 * @return
	 */
	@ResponseBody
	@GetMapping("/getMaterialType")
	public Tips getMaterialType() {
		super.validLogined();
		if (isLogined) {
			Parames parames = new Parames(null, null, null, Constant.MATERIAL_TYPE);
			parames.setGroup(Constant.MATERIAL_TYPE);
			List<Parames> paramesList  = paramesServices.selectByParames(parames);
			tips = new Tips("查询成功", true, paramesList);
		}
		return tips;
	}
}
