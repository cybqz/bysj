package com.cyb.proname.business.controller.common;

import com.cyb.proname.business.mapper.common.ParamesMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="/params")
public class ParamsController {

	@Resource
	private ParamesMapper paramesMapper;
}
