package com.cyb.proname.business.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.User;
import com.cyb.authority.service.UserService;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.annotation.ModelInfo;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.business.utils.UserUtilService;
import com.cyb.proname.constant.SysCfgConstant;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * @Author 陈迎博
 * @Description 用户管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/userBasicManage")
@ModelInfo(title = "用户基础信息管理", navbar = "基础信息管理", prefix = "user/basic")
public class UserBasicManageController extends BasicController {

	@Resource
	private UserService userService;

	@Resource
	private UserUtilService userUtilService;

	@Authentication(name = "保存用户", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_SAVE)
	@ResponseBody
	public Tips save(@RequestBody User user, HttpSession session) {
		tips = new Tips("新增失败", true, false);
		User userTemp = userService.selectByUserName(user.getUserName());
		if(null == userTemp){
			user.setId(MyUtils.getPrimaryKey());
			String url = session.getServletContext().getRealPath("/");
			int count = userService.insert(user, url);
			if(count > 0){
				tips = new Tips("新增成功", true, true);
			}
		}
		tips.setMsg("用户已存在");
		return tips;
	}

	@Authentication(name = "删除用户", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DELETE)
	@ResponseBody
	public Tips delete(@RequestBody JSONObject param) {
		tips = new Tips("删除失败", true, false);
		String id = MyUtils.getByJSONObject(param, "id");
		if(StringUtils.isNotEmpty(id)){
			int count = userService.deleteById(id);
			if(count > 0){
				tips = new Tips("删除成功", true, true);
			}
		}else{
			tips.setMsg("编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "更新用户", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DO_UPDATE)
	@ResponseBody
	public Tips doUpdate(@RequestBody User user) {
		tips = new Tips("更新失败", true, false);
		if(StringUtils.isNotEmpty(user.getId())){
			boolean success = userService.updateById(user);
			if(success){
				tips = new Tips("更新成功", true, true);
			}
		}else{
			tips.setMsg("编号不能为空");
		}
		return tips;
	}

	@Authentication(name = "查询用户详情", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_DETAIL)
	@ResponseBody
	public Tips detail(@RequestBody JSONObject param) {

		tips.setMsg("查询失败");
		User user = userUtilService.detail(param);
		if(null != user){
			tips = new Tips("查询成功",  true, user);
		}
		return tips;
	}

	@Authentication(name = "查询用户列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
		userUtilService.page(param, tipsPagination);
		return tipsPagination;
	}

	@Authentication(name = "查询用户总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody User user) {
		return userUtilService.selectCount(user);
	}
}
