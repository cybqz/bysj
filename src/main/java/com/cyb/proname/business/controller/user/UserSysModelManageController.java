package com.cyb.proname.business.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.annotation.Authentication;
import com.cyb.authority.domain.SysModel;
import com.cyb.authority.domain.User;
import com.cyb.authority.domain.UserSysModel;
import com.cyb.authority.service.*;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.proname.annotation.ModelInfo;
import com.cyb.proname.business.controller.base.BasicController;
import com.cyb.proname.business.domain.Model;
import com.cyb.proname.constant.SysCfgConstant;
import com.cyb.proname.utils.MyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 陈迎博
 * @Description 用户系统模块管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/userSysModelManage")
@ModelInfo(title = "用户系统模块管理", navbar = "系统模块管理", prefix = "user/sysmodel")
public class UserSysModelManageController extends BasicController {

	@Resource
	private SysModelService sysModelService;

	@Resource
	private UserService userService;

	@Resource
	private UserSysModelService userSysModelService;

	@RequestMapping("/editSysModel")
	public String updateAuthority(String id, HttpServletRequest request) {
		setModelInfo("/userSysModelManage");
		request.setAttribute("operationId", id);
		request.setAttribute("modelUrl", modelUrl);
		request.setAttribute("title", modelName +"-系统模块编辑");
		return prefix + "/editSysModel";
	}

	@Authentication(name = "保存系统模块", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("addUserSysModel")
	@ResponseBody
	public Tips addUserSysModel(@RequestBody UserSysModel userSysModel) {
		tips = new Tips("新增失败", true, false);

		if(StringUtils.isBlank(userSysModel.getUserId())){
			tips.setMsg("用户编号不能为空");
		}else if(StringUtils.isBlank(userSysModel.getSysModelId())){
			tips.setMsg("系统模块编号不能为空");
		}else{

			int count = userSysModelService.selectCount(userSysModel);
			if(count > 0){
				tips.setMsg("当前用户已拥有此系统模块");
			}else{
				userSysModel.setId(MyUtils.getPrimaryKey());
				userSysModelService.insert(userSysModel);
				tips = new Tips("新增成功", true, true);
			}
		}
		return tips;
	}

	@Authentication(name = "删除系统模块", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("deleteUserSysModel")
	@ResponseBody
	public Tips deleteUserSysModel(@RequestBody UserSysModel userSysModel) {
		tips = new Tips("新增失败", true, false);

		if(StringUtils.isBlank(userSysModel.getUserId())){
			tips.setMsg("用户编号不能为空");
		}else if(StringUtils.isBlank(userSysModel.getSysModelId())){
			tips.setMsg("系统模块编号不能为空");
		}else{
			UserSysModel userSysModelTmp = userSysModelService.selectOne(userSysModel);
			if(null != userSysModelTmp){
				userSysModelService.deleteById(userSysModelTmp.getId());
				tips = new Tips("删除成功", true, true);
			}else{
				tips.setMsg("当前用户未拥有此系统模块");
			}
		}
		return tips;
	}

	@Authentication(name = "查询未拥有系统模块列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectPageHavNo")
	@ResponseBody
	public TipsPagination<SysModel> selectPageHavNo(@RequestBody JSONObject param) {
		TipsPagination<SysModel> tipsPagination = new TipsPagination<SysModel>();
		tipsPagination.convertFromTips(tips);
		UserSysModel userSysModel = param.getObject("userSysModel", UserSysModel.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = sysModelService.selectCountHavNo(userSysModel.getUserId());
		if(count > 0){
			IPage<SysModel> page = sysModelService.selectPageHavNo(userSysModel.getUserId(), pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询系统模块列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectPageHav")
	@ResponseBody
	public TipsPagination<SysModel> selectPageHav(@RequestBody JSONObject param) {
		TipsPagination<SysModel> tipsPagination = new TipsPagination<SysModel>();
		tipsPagination.convertFromTips(tips);
		UserSysModel userSysModel = param.getObject("userSysModel", UserSysModel.class);
		Pagination pagination = param.getObject("pagination", Pagination.class);
		int count = userSysModelService.selectCount(userSysModel);
		if(count > 0){
			IPage<SysModel> page = sysModelService.selectPageHav(userSysModel.getUserId(), pagination);
			pagination.setDatas(page.getRecords());
			pagination.setTotal(count);
			tipsPagination.setPagination(pagination);
			tipsPagination.setValidate(true);
			tipsPagination.setMsg("查询成功");
		}
		return tipsPagination;
	}

	@Authentication(name = "查询用户未拥有系统模块总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectCountHavNo")
	@ResponseBody
	public Tips selectCountHavNo(@RequestBody JSONObject param) {
		String userId = param.getString("userId");
		int count = sysModelService.selectCountHavNo(userId);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}

	@Authentication(name = "查询用户系统模块总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping("selectCountByUserId")
	@ResponseBody
	public Tips selectCountByUserId(@RequestBody JSONObject param) {
		String userId = param.getString("userId");
		UserSysModel userSysModel = new UserSysModel();
		userSysModel.setUserId(userId);
		int count = userSysModelService.selectCount(userSysModel);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}

	@Authentication(name = "查询用户列表", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		tipsPagination.convertFromTips(tips);
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
		return tipsPagination;
	}

	@Authentication(name = "查询用户总数", roleNames = {SysCfgConstant.ROLE_ADMIN})
	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody User user) {
		int count = userService.selectCount(user);
		tips = new Tips("查询成功",  true, count);
		return tips;
	}
}
