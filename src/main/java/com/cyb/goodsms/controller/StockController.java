package com.cyb.goodsms.controller;

import com.cyb.authority.base.BaseController;
import com.cyb.goodsms.common.Constant;
import com.cyb.goodsms.service.ParamesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 库存管理Controller
 */
@Controller
@RequestMapping(value = "/"+Constant.STOCK)
public class StockController extends BaseController {

	private final static String MODEL_NAME = "库存管理";

	@GetMapping("/")
	public String index(HttpServletRequest request) {
		request.setAttribute("title", MODEL_NAME +"-列表");
		return Constant.DEFAULT_PAGE_STOCK;
	}
}
