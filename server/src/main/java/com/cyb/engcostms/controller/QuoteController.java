package com.cyb.engcostms.controller;

import com.cyb.authority.base.BaseController;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.engcostms.domain.Quote;
import com.cyb.engcostms.service.QuoteService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


/**
 * Quote控制层
 *
 * @author generation
 */
@Controller
@CrossOrigin
@RequestMapping("/quote")
public class QuoteController extends BaseController {
    @Autowired
    private QuoteService quoteService;

    /**
     * 新增
     * @param quote
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Tips save(Quote quote) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            if(StringUtils.isEmpty(quote.getProjectName())){
                tips.setMsg("项目名称不能为空");
            }else if(StringUtils.isEmpty(quote.getTemplateId())){
                tips.setMsg("模板ID不能为空");
            }else if(StringUtils.isEmpty(quote.getContains())){
                tips.setMsg("报价内容不能为空");
            }else{

                Quote param = new Quote();
                param.setProjectName(quote.getProjectName());
                Quote quoteExist = quoteService.getOne(param);
                if(null == quoteExist){
                    int count = quoteService.save(quote);
                    if (count > 0) {
                        tips.setValidate(true);
                        tips.setMsg("保存成功");
                    }
                }else{
                    tips.setMsg("项目已存在");
                }
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
            int count = quoteService.deleteById(id);
            if (count > 0) {
                tips.setMsg("删除成功");
            }
        }
        return tips;
    }

    /**
     * 更新
     * @param quote
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Tips update(Quote quote) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            if(StringUtils.isEmpty(quote.getId())){
                tips.setMsg("ID不能为空");
            }else{
                int count = quoteService.update(quote);
                if (count > 0) {
                    tips.setValidate(true);
                    tips.setMsg("更新成功");
                }
            }
        }
        return tips;
    }

    /**
     * 审核
     * @param id
     * @return
     */
    @ResponseBody
    @PostMapping("/audit")
    public Tips audit(@RequestParam(value = "id") String id) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            if(StringUtils.isEmpty(id)){
                tips.setMsg("ID不能为空");
            }else{
                Quote quote = quoteService.detail(id);
                if(null != quote){

                    if(quote.getAuditStatus() != 0){
                        tips.setMsg("无效得审核状态");
                    }else{
                        quote.setAuditStatus(1);
                        int count = quoteService.update(quote);
                        if (count > 0) {
                            tips.setValidate(true);
                            tips.setMsg("审核通过");
                        }
                    }
                }else{
                    tips.setMsg("报价方案不存在");
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
            Quote quote = quoteService.detail(id);
            tips.setData(quote);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 列表查询
     * @param quote
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Tips list(Quote quote) {
        super.validLogined();
        if (isLogined) {
            List<Quote> list = quoteService.list(quote);
            tips.setData(list);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 分页列表查询
     * @param quote
     * @param pagination
     * @return
     */
    @ResponseBody
    @GetMapping("/page")
    public TipsPagination<Quote> page(Quote quote, Pagination pagination) {
        TipsPagination<Quote> tipsPagination = new TipsPagination<Quote>();
        super.validLogined();
        tipsPagination.convertFromTips(tips);

        if (isLogined) {
            Pagination<Quote> result = quoteService.page(quote, pagination);
            tipsPagination.setPagination(result);
            tipsPagination.setMsg("查询成功");
        }
        return tipsPagination;
    }
}
