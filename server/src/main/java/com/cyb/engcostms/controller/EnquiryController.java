package com.cyb.engcostms.controller;

import com.cyb.authority.base.BaseController;

import com.cyb.common.tips.Tips;

import com.cyb.engcostms.dao.MaterialMapper;
import com.cyb.engcostms.dao.SupplierMapper;
import com.cyb.engcostms.domain.Enquiry;
import com.cyb.engcostms.domain.Enquiry;
import com.cyb.engcostms.domain.Material;
import com.cyb.engcostms.domain.Supplier;
import com.cyb.engcostms.service.EnquiryService;
import com.cyb.engcostms.service.EnquiryService;

import com.cyb.engcostms.service.MaterialService;
import com.cyb.engcostms.service.SupplierService;
import com.cyb.engcostms.vo.EnquiryVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * Enquiry控制层
 *
 * @author generation
 */
@Controller
@CrossOrigin
@RequestMapping("/enquiry")
public class EnquiryController extends BaseController {

    @Autowired
    private EnquiryService enquiryService;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private SupplierService supplierService;

    /**
     * 新增
     * @param enquiry
     * @return
     */
    @ResponseBody
    @PostMapping("/save")
    public Tips save(Enquiry enquiry) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            if(saveValid(enquiry)){
                int count = enquiryService.save(enquiry);
                if (count > 0) {
                    tips = new Tips("保存成功", true);
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
    public Tips deleteById(@RequestParam(value = "id")
    String id) {
        super.validLogined();
        if (isLogined) {
            int count = enquiryService.deleteById(id);

            if (count > 0) {
                tips.setMsg("删除成功");
            }
        }
        return tips;
    }

    /**
     * 更新
     * @param enquiry
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public Tips update(Enquiry enquiry) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            if(saveValid(enquiry)){
                if(StringUtils.isEmpty(enquiry.getId())){
                    tips.setMsg("ID不能为空");
                }else if(StringUtils.isEmpty(enquiry.getBelongId())){
                    tips.setMsg("所属ID不能为空");
                }else{
                    int count = enquiryService.save(enquiry);
                    if (count > 0) {
                        tips = new Tips("更新成功", true);
                    }
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
    public Tips detail(@RequestParam(value = "id")String id) {
        super.validLogined();
        if (isLogined) {
            Enquiry enquiry = enquiryService.detail(id);
            tips.setData(enquiry);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 列表查询
     * @param enquiry
     * @return
     */
    @ResponseBody
    @GetMapping("/list")
    public Tips list(Enquiry enquiry) {
        super.validLogined();
        if (isLogined) {
            List<Enquiry> list = enquiryService.list(enquiry);
            List<EnquiryVO> voList = setVO(list);
            tips.setData(voList);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 历史列表查询
     * @param enquiry
     * @return
     */
    @ResponseBody
    @GetMapping("/historylist")
    public Tips historylist(Enquiry enquiry) {
        super.validLogined();
        if (isLogined) {
            if(StringUtils.isEmpty(enquiry.getBelongId())){
                tips.setMsg("所属ID不能为空");
            }
            List<Enquiry> list = enquiryService.list(enquiry);
            List<EnquiryVO> voList = setVO(list);
            tips.setData(voList);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    private List<EnquiryVO> setVO(List<Enquiry> list){

        List<EnquiryVO> voList = null;
        if(CollectionUtils.isNotEmpty(list)){
            voList = new ArrayList<>(list.size());
            for(Enquiry enquiry : list){
                EnquiryVO vo = new EnquiryVO();
                BeanUtils.copyProperties(enquiry, vo);

                Material materialParam = new Material();
                materialParam.setMaterialId(enquiry.getMaterialId());
                Material material = materialService.getOne(materialParam);
                vo.setMaterialName(material.getMaterialName());

                Supplier supplierParam = new Supplier();
                supplierParam.setSupplierId(enquiry.getSupplierId());
                Supplier supplier = supplierService.getOne(supplierParam);
                vo.setSupplierName(supplier.getSupplierName());

                voList.add(vo);
            }
        }
        return voList;
    }

    private boolean saveValid(Enquiry enquiry){

        if(null == enquiry.getPrice()){
            tips.setMsg("价格不能为空");
        }else if(StringUtils.isEmpty(enquiry.getUnit())){
            tips.setMsg("单位不能为空");
        }else if(StringUtils.isEmpty(enquiry.getMaterialId())){
            tips.setMsg("物料编号不能为空");
        }else if(StringUtils.isEmpty(enquiry.getSupplierId())){
            tips.setMsg("供应商编号不能为空");
        }else{
            Material materialParam = new Material();
            materialParam.setMaterialId(enquiry.getMaterialId());
            Material material = materialService.getOne(materialParam);
            if(null != material){

                Supplier supplierParam = new Supplier();
                supplierParam.setSupplierId(enquiry.getSupplierId());
                Supplier supplier = supplierService.getOne(supplierParam);
                if(null != supplier){
                    return true;
                }else{
                    tips.setMsg("所选供应商不存在");
                }
            }else{
                tips.setMsg("所选物料不存在");
            }

        }
        return false;
    }
}
