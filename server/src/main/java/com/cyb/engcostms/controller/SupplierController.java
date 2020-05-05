package com.cyb.engcostms.controller;

import com.cyb.authority.base.BaseController;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.engcostms.domain.Supplier;
import com.cyb.engcostms.service.SupplierService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Supplier控制层
 *
 * @author generation
 */
@Controller
@CrossOrigin
@RequestMapping("/supplier")
public class SupplierController extends BaseController {
    @Autowired
    private SupplierService supplierService;

    /**
     * 新增
     * @param supplier
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Tips save(Supplier supplier) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            if(StringUtils.isNotEmpty(supplier.getSupplierName())){
                Supplier existSupplier = supplierService.getOne(supplier);
                if(null == existSupplier){
                    int count = supplierService.save(supplier);
                    if (count > 0) {
                        tips = new Tips("保存成功", true);
                    }
                }else{
                    tips.setMsg("供应商已存在");
                }
            }else{
                tips.setMsg("供应商名称不能为空");
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
            tips.setValidate(false);
            int count = supplierService.deleteById(id);
            if (count > 0) {
                tips = new Tips("删除成功", true);
            }
        }

        return tips;
    }

    /**
     * 更新
     * @param supplier
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Tips update(Supplier supplier) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            Supplier existSupplier = supplierService.getOne(supplier);
            if(null == existSupplier){
                int count = supplierService.update(supplier);
                if (count > 0) {
                    tips = new Tips("更新成功", true);
                }
            }else{
                tips.setMsg("供应商已存在");
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
    public Tips detail(@RequestParam(value = "id", required = true)
    String id) {
        super.validLogined();
        if (isLogined) {
            Supplier supplier = supplierService.detail(id);
            tips.setData(supplier);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 列表查询
     * @param supplier
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Tips list(Supplier supplier) {
        super.validLogined();
        if (isLogined) {
            List<Supplier> list = supplierService.list(supplier);
            tips.setData(list);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 分页列表查询
     * @param supplier
     * @param pagination
     * @return
     */
    @ResponseBody
    @GetMapping("/page")
    public TipsPagination<Supplier> page(Supplier supplier, Pagination pagination) {
        TipsPagination<Supplier> tipsPagination = new TipsPagination<Supplier>();
        super.validLogined();
        tipsPagination.convertFromTips(tips);
        if (isLogined) {
            Pagination<Supplier> result = supplierService.page(supplier, pagination);
            tipsPagination.setPagination(result);
            tipsPagination.setMsg("查询成功");
        }
        return tipsPagination;
    }
}
