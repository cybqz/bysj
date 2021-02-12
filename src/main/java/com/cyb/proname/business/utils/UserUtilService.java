package com.cyb.proname.business.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.domain.User;
import com.cyb.authority.service.UserService;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.business.domain.Model;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @Author 陈迎博
 * @Description 用户管理辅助类
 * @Date 2021/1/22
 */
@Component
public class UserUtilService {

	@Resource
	private UserService userService;

	public void page(JSONObject param, TipsPagination<Model> tipsPagination) {
		User user = param.getObject("user", User.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = userService.selectCount(user);
		if(count > 0){
			IPage<User> page = userService.selectPage(user, pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
	}

	public Tips selectCount(User user) {
		int count = userService.selectCount(user);
		return new Tips("查询成功",  true, count);
	}
}
