package com.cyb.engcostms.controller;

import com.cyb.authority.base.BaseController;

import com.cyb.common.tips.Tips;

import com.cyb.engcostms.domain.Supplier;
import com.cyb.engcostms.service.SupplierService;

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
            int count = supplierService.save(supplier);

            if (count > 0) {
                tips.setMsg("保存成功");
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
    public Tips deleteById(
        @RequestParam(value = "id", required = true)
    String id) {
        super.validLogined();

        if (isLogined) {
            int count = supplierService.deleteById(id);

            if (count > 0) {
                tips.setMsg("删除成功");
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
            int count = supplierService.update(supplier);

            if (count > 0) {
                tips.setMsg("更新成功");
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
}
