package com.cyb.proname.business.controller.common;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyb.authority.domain.User;
import com.cyb.authority.service.UserService;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
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
import javax.servlet.http.HttpSession;

/**
 * @Author 陈迎博
 * @Description 用户管理控制层
 * @Date 2021/1/22
 */
@Controller
@RequestMapping(value= "/userManage")
public class UserManageController extends BasicController {

    public String modelUrl = "/userManage";
	public String modelName = "用户管理";

	@Resource
	private UserService userService;

	@RequestMapping("/editRole")
	public String updateAuthority(String id, HttpServletRequest request) {
		request.setAttribute("operationId", id);
		request.setAttribute("modelUrl", modelUrl);
		request.setAttribute("title", modelName +"-角色编辑");
		return SysCfgConstant.DEFAULT_PAGE_PREFIX + modelUrl + "/editRole";
	}

	@PostMapping(SysCfgConstant.METHOD_URL_SAVE)
	@ResponseBody
	public Tips save(@RequestBody User user, HttpSession session) {
		super.validLogined();
		if(isLogined){
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
		}
		return tips;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_DELETE)
	@ResponseBody
	public Tips delete(@RequestBody JSONObject param) {
		super.validLogined();
		if(isLogined){
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
		}
		return tips;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_DO_UPDATE)
	@ResponseBody
	public Tips doUpdate(@RequestBody User user) {
		super.validLogined();
		if(isLogined){
			tips = new Tips("更新失败", true, false);
			if(StringUtils.isNotEmpty(user.getId())){
				boolean success = userService.updateById(user);
				if(success){
					tips = new Tips("更新成功", true, true);
				}
			}else{
				tips.setMsg("编号不能为空");
			}
		}
		return tips;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_DETAIL)
	@ResponseBody
	public Tips detail(@RequestBody JSONObject param) {

		super.validLogined();
		if(isLogined) {
			tips.setMsg("查询失败");
			String id = MyUtils.getByJSONObject(param, "id");
			if(StringUtils.isNotEmpty(id)){
				User user = userService.selectById(id);
				if(null != user){
					tips = new Tips("查询成功",  true, user);
				}
			}
		}
		return tips;
	}

	@PostMapping(value = SysCfgConstant.METHOD_URL_PAGE)
	@ResponseBody
	public TipsPagination<Model> page(@RequestBody JSONObject param) {
		TipsPagination<Model> tipsPagination = new TipsPagination<Model>();
		super.validLogined();
		tipsPagination.convertFromTips(tips);
		if(isLogined) {
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
		return tipsPagination;
	}

	@PostMapping(SysCfgConstant.METHOD_URL_COUNT)
	@ResponseBody
	public Tips count(@RequestBody User user) {
		super.validLogined();
		if(isLogined) {
			int count = userService.selectCount(user);
			tips = new Tips("查询成功",  true, count);
		}
		return tips;
	}
}
