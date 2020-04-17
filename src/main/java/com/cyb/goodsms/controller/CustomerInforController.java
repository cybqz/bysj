package com.cyb.goodsms.controller;

import com.cyb.authority.base.BaseController;
import com.cyb.common.pagenation.Pagenation;
import com.cyb.common.tips.Tips;
import com.cyb.goodsms.common.Constant;
import com.cyb.goodsms.dao.CarBuyingPeopleExample;
import com.cyb.goodsms.dao.CustomerInfoMapper;
import com.cyb.goodsms.dao.CustomerInfoExample;
import com.cyb.goodsms.domain.CarBuyingPeople;
import com.cyb.goodsms.domain.CustomerInfo;
import com.cyb.goodsms.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 客户信息管理Controller
 */
@Controller
@RequestMapping(value= "/"+Constant.CUSTOMER_INFO)
public class CustomerInforController extends BaseController {

	private final static String MODEL_NAME = "客户信息管理";

	@Resource
	private CustomerInfoMapper customerInfoMapper;

	@RequestMapping("/")
	public String index(HttpServletRequest request) {
		request.setAttribute("model", MODEL_NAME);
		request.setAttribute("title", MODEL_NAME +"-列表");
		return Constant.DEFAULT_PAGE_CUSTOMER_INFO;
	}

	@RequestMapping(Constant.DEFAULT_ADD)
	public String add(HttpServletRequest request) {
		request.setAttribute("title", MODEL_NAME +"-新增");
		return Constant.DEFAULT_PAGE_PREFIX +Constant.CUSTOMER_INFO + Constant.DEFAULT_ADD;
	}

	@PostMapping(Constant.DEFAULT_SAVE)
	@ResponseBody
	public Tips save(CustomerInfo customerInfo) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("新增失败", true, false);
			customerInfo.setId(MyUtils.getPrimaryKey());
			customerInfo.setCreateDateTime(new Date());
			int count = customerInfoMapper.insert(customerInfo);
			if(count > 0){
				tips = new Tips("新增成功", true, true);
			}
		}
		return tips;
	}

	@PostMapping(Constant.DEFAULT_DELETE)
	@ResponseBody
	public Tips delete(String id) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("删除失败", true, false);
			if(StringUtils.isNotEmpty(id)){
				CustomerInfoExample customerInfo = new CustomerInfoExample();
				customerInfo.createCriteria().andIdEqualTo(id);
				int count = customerInfoMapper.deleteByExample(customerInfo);
				if(count > 0){
					tips = new Tips("删除成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}

	@RequestMapping(Constant.DEFAULT_UPDATE)
	public String update(String id, HttpServletRequest request) {
		request.setAttribute("title", MODEL_NAME +"-更新");
		request.setAttribute("opreationId", id);
		return Constant.DEFAULT_PAGE_PREFIX +Constant.CUSTOMER_INFO + Constant.DEFAULT_UPDATE;
	}

	@PostMapping("/doupdate")
	@ResponseBody
	public Tips doupdate(CustomerInfo customerInfo) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("更新失败", true, false);
			if(StringUtils.isNotEmpty(customerInfo.getId())){
				CustomerInfoExample customerInfoExample = new CustomerInfoExample();
				customerInfoExample.createCriteria().andIdEqualTo(customerInfo.getId());
				int count = customerInfoMapper.updateByExample(customerInfo, customerInfoExample);
				if(count > 0){
					tips = new Tips("更新成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}

	@PostMapping(Constant.DEFAULT_DETAIL)
	@ResponseBody
	public Tips detail(String id) {

		super.validLogined();
		if(isLogined) {
			tips.setMsg("查询失败");
			CustomerInfoExample customerInfoExample = new CustomerInfoExample();
			CustomerInfoExample.Criteria criteria = customerInfoExample.createCriteria();
			if(StringUtils.isNotEmpty(id)){
				criteria.andIdEqualTo(id);
				List<CustomerInfo> list = customerInfoMapper.selectByExample(customerInfoExample);
				if(null != list && !list.isEmpty()){
					tips = new Tips("查询成功",  true, list.get(0));
				}
			}
		}
		return tips;
	}

	@PostMapping(Constant.DEFAULT_PAGE)
	@ResponseBody
	public Tips page(CustomerInfo customerInfo, Pagenation pagenation) {

		super.validLogined();
		if(isLogined) {

			CustomerInfoExample customerInfoExample = getCustomerInfoExample(customerInfo);
			List<CustomerInfo> list = customerInfoMapper.selectByExample(customerInfoExample);
			tips = new Tips("查询成功",  true, list);
		}
		return tips;
	}

	@PostMapping(Constant.DEFAULT_COUNT)
	@ResponseBody
	public Tips count(CustomerInfo customerInfo) {
		super.validLogined();
		if(isLogined) {
			CustomerInfoExample customerInfoExample = getCustomerInfoExample(customerInfo);
			int count = customerInfoMapper.countByExample(customerInfoExample);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}

	private CustomerInfoExample getCustomerInfoExample(CustomerInfo customerInfo){
		CustomerInfoExample customerInfoExample = new CustomerInfoExample();
		CustomerInfoExample.Criteria criteria = customerInfoExample.createCriteria();
		if(StringUtils.isNotEmpty(customerInfo.getCustomerName())){
			criteria.andCustomerNameEqualTo(customerInfo.getCustomerName());
		}
		return customerInfoExample;
	}
}
