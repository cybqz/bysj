package com.cyb.proname.controller.common;

import com.cyb.proname.mapper.common.ParamesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="/params")
public class ParamsController {

	@Resource
	private ParamesMapper paramesMapper;
}
