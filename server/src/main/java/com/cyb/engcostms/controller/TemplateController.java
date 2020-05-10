package com.cyb.engcostms.controller;

import com.cyb.authority.base.BaseController;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.engcostms.domain.Template;
import com.cyb.engcostms.service.TemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Template控制层
 *
 * @author generation
 */
@Controller
@CrossOrigin
@RequestMapping("/template")
public class TemplateController extends BaseController {
    @Autowired
    private TemplateService templateService;

    /**
     * 新增
     * @param template
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Tips save(Template template) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            if(StringUtils.isNotEmpty(template.getContains())){
                int count = templateService.save(template);
                if (count > 0) {
                    tips.setValidate(true);
                    tips.setMsg("保存成功");
                }
            }else{
                tips.setMsg("模板内容不能为空");
            }
        }
        return tips;
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/deleteById")
    public Tips deleteById(@RequestParam(value = "id") String id) {
        super.validLogined();
        if (isLogined) {
            int count = templateService.deleteById(id);
            if (count > 0) {
                tips.setMsg("删除成功");
            }
        }
        return tips;
    }

    /**
     * 更新
     * @param template
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Tips update(Template template) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            if(StringUtils.isEmpty(template.getId())){
                tips.setMsg("ID不能为空");
            }else if(StringUtils.isEmpty(template.getContains())){
                tips.setMsg("模板内容不能为空");
            }else{
                int count = templateService.update(template);
                if (count > 0) {
                    tips.setValidate(true);
                    tips.setMsg("更新成功");
                }
            }
        }
        return tips;
    }

    /**
     * 详情查询
     * @param id
     * @return
     */
    @ResponseBody
    @GetMapping("/detail")
    public Tips detail(@RequestParam(value = "id") String id) {
        super.validLogined();
        if (isLogined) {
            Template template = templateService.detail(id);
            tips.setData(template);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 列表查询
     * @param template
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Tips list(Template template) {
        super.validLogined();
        if (isLogined) {
            List<Template> list = templateService.list(template);
            tips.setData(list);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 分页列表查询
     * @param template
     * @param pagination
     * @return
     */
    @ResponseBody
    @GetMapping("/page")
    public TipsPagination<Template> page(Template template, Pagination pagination) {
        TipsPagination<Template> tipsPagination = new TipsPagination<Template>();
        super.validLogined();
        tipsPagination.convertFromTips(tips);
        if (isLogined) {
            Pagination<Template> result = templateService.page(template, pagination);
            tipsPagination.setPagination(result);
            tipsPagination.setMsg("查询成功");
        }
        return tipsPagination;
    }
}
