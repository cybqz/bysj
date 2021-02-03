package com.cyb.proname.business.controller.common;

import com.cyb.proname.business.mapper.common.DictionariesMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value="/dictionaries")
public class DictionariesController {

	@Resource
	private DictionariesMapper dictionariesMapper;
}
