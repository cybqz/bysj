package com.cyb.engcostms.controller;

import com.cyb.authority.base.BaseController;

import com.cyb.common.tips.Tips;

import com.cyb.engcostms.domain.Material;
import com.cyb.engcostms.service.MaterialService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Material控制层
 *
 * @author generation
 */
@Controller
@CrossOrigin
@RequestMapping("/material")
public class MaterialController extends BaseController {
    @Autowired
    private MaterialService materialService;

    /**
     * 新增
     * @param material
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Tips save(Material material) {
        super.validLogined();

        if (isLogined) {
            int count = materialService.save(material);

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
            int count = materialService.deleteById(id);

            if (count > 0) {
                tips.setMsg("删除成功");
            }
        }

        return tips;
    }

    /**
     * 更新
     * @param material
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Tips update(Material material) {
        super.validLogined();

        if (isLogined) {
            int count = materialService.update(material);

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
            Material material = materialService.detail(id);
            tips.setData(material);
            tips.setMsg("查询成功");
        }

        return tips;
    }

    /**
     * 列表查询
     * @param material
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Tips list(Material material) {
        super.validLogined();

        if (isLogined) {
            List<Material> list = materialService.list(material);
            tips.setData(list);
            tips.setMsg("查询成功");
        }

        return tips;
    }
}
