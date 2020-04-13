package com.cyb.goodsms.controller;

import com.cyb.authority.base.BaseController;
import com.cyb.common.pagenation.Pagenation;
import com.cyb.common.tips.Tips;
import com.cyb.goodsms.common.Constant;
import com.cyb.goodsms.dao.CarBuyingPeopleExample;
import com.cyb.goodsms.dao.CarBuyingPeopleMapper;
import com.cyb.goodsms.domain.CarBuyingPeople;
import com.cyb.goodsms.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 销售管理Controller
 */
@Controller
@RequestMapping(value = "/"+Constant.SALE)
public class SaleController extends BaseController {

	private final static String MODEL_NAME = "销售管理";

	@Resource
	private CarBuyingPeopleMapper carBuyingPeopleMapper;

	@GetMapping("/")
	public String index(HttpServletRequest request) {
		request.setAttribute("model", MODEL_NAME);
		request.setAttribute("title", MODEL_NAME +"-列表");
		return Constant.DEFAULT_PAGE_SALE;
	}

	@RequestMapping(Constant.SALE_ADD)
	public String add(HttpServletRequest request) {
		request.setAttribute("title", MODEL_NAME +"-新增");
		return Constant.DEFAULT_PAGE_PREFIX +Constant.SALE + Constant.SALE_ADD;
	}

	@PostMapping("/save")
	@ResponseBody
	public Tips save(CarBuyingPeople carBuyingPeople) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("新增失败", true, false);
			carBuyingPeople.setId(MyUtils.getPrimaryKey());
			carBuyingPeople.setCreateDateTime(new Date());
			int count = carBuyingPeopleMapper.insert(carBuyingPeople);
			if(count > 0){
				tips = new Tips("新增成功", true, true);
			}
		}
		return tips;
	}

	@PostMapping("/delete")
	@ResponseBody
	public Tips delete(String id) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("删除失败", true, false);
			if(StringUtils.isNotEmpty(id)){
				CarBuyingPeopleExample carBuyingPeopleExample = new CarBuyingPeopleExample();
				carBuyingPeopleExample.createCriteria().andIdEqualTo(id);
				int count = carBuyingPeopleMapper.deleteByExample(carBuyingPeopleExample);
				if(count > 0){
					tips = new Tips("删除成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}

	@RequestMapping(Constant.SALE_UPDATE)
	public String update(String id, HttpServletRequest request) {
		request.setAttribute("title", MODEL_NAME +"-更新");
		request.setAttribute("opreationId", id);
		return Constant.DEFAULT_PAGE_PREFIX +Constant.SALE + Constant.SALE_UPDATE;
	}

	@PostMapping("/doupdate")
	@ResponseBody
	public Tips doupdate(CarBuyingPeople carBuyingPeople) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("更新失败", true, false);
			if(StringUtils.isNotEmpty(carBuyingPeople.getId())){
				CarBuyingPeopleExample carBuyingPeopleExample = new CarBuyingPeopleExample();
				carBuyingPeopleExample.createCriteria().andIdEqualTo(carBuyingPeople.getId());
				int count = carBuyingPeopleMapper.updateByExample(carBuyingPeople, carBuyingPeopleExample);
				if(count > 0){
					tips = new Tips("更新成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}



	@PostMapping("/detail")
	@ResponseBody
	public Tips detail(String id) {

		super.validLogined();
		if(isLogined) {
			tips.setMsg("查询失败");
			CarBuyingPeopleExample carBuyingPeopleExample = new CarBuyingPeopleExample();
			CarBuyingPeopleExample.Criteria criteria = carBuyingPeopleExample.createCriteria();
			if(StringUtils.isNotEmpty(id)){
				criteria.andIdEqualTo(id);
				List<CarBuyingPeople> list = carBuyingPeopleMapper.selectByExample(carBuyingPeopleExample);
				if(null != list && !list.isEmpty()){
					tips = new Tips("查询成功",  true, list.get(0));
				}
			}
		}
		return tips;
	}

	@PostMapping("/page")
	@ResponseBody
	public Tips page(CarBuyingPeople carBuyingPeople, Pagenation pagenation) {
		super.validLogined();
		if(isLogined) {
			CarBuyingPeopleExample carBuyingPeopleExample = getCarBuyingPeopleExample(carBuyingPeople);
			List<CarBuyingPeople> list = carBuyingPeopleMapper.selectByExample(carBuyingPeopleExample);
			tips = new Tips("查询成功",  true, list);
		}
		return tips;
	}

	@PostMapping("/count")
	@ResponseBody
	public Tips count(CarBuyingPeople carBuyingPeople) {
		super.validLogined();
		if(isLogined) {
			CarBuyingPeopleExample carBuyingPeopleExample = getCarBuyingPeopleExample(carBuyingPeople);
			int count = carBuyingPeopleMapper.countByExample(carBuyingPeopleExample);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}

	private CarBuyingPeopleExample getCarBuyingPeopleExample(CarBuyingPeople carBuyingPeople){
		CarBuyingPeopleExample carBuyingPeopleExample = new CarBuyingPeopleExample();
		CarBuyingPeopleExample.Criteria criteria = carBuyingPeopleExample.createCriteria();
		if(StringUtils.isNotEmpty(carBuyingPeople.getBuyName())){
			criteria.andBuyNameEqualTo(carBuyingPeople.getBuyName());
		}
		return carBuyingPeopleExample;
	}
}
