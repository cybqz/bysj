package com.cyb.engcostms.controller;

import com.cyb.common.tips.Tips;
import com.cyb.engcostms.service.ParamesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/parames")
public class ParamesController {

	@Autowired
	private ParamesServices paramesServices;
}
