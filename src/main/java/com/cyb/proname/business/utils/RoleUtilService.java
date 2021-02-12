package com.cyb.proname.business.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.Role;
import com.cyb.authority.service.RoleService;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.constant.SysCfgConstant;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author 陈迎博
 * @Description 角色管理辅助服务
 * @Date 2021/1/22
 */
@Component
public class RoleUtilService {

	@Resource
	private RoleService roleService;

	public void page(JSONObject param, TipsPagination<Model> tipsPagination) {
		Role role = param.getObject("role", Role.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = roleService.selectCount(role);
		if(count > 0){
			IPage<Role> page = roleService.selectPage(role, pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
	}

	public Role detail(JSONObject param) {

		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			return roleService.selectById(id);
		}
		return null;
	}

	public Tips selectCount(Role role) {
		int count = roleService.selectCount(role);
		return new Tips("查询成功",  true, count);
	}
}
