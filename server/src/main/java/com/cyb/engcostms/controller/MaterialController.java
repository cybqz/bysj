package com.cyb.engcostms.controller;

import com.cyb.authority.base.BaseController;
import com.cyb.common.pagination.Pagination;
import com.cyb.common.tips.Tips;
import com.cyb.common.tips.TipsPagination;
import com.cyb.engcostms.domain.Material;
import com.cyb.engcostms.domain.Parames;
import com.cyb.engcostms.domain.Supplier;
import com.cyb.engcostms.service.MaterialService;
import com.cyb.engcostms.service.ParamesServices;
import com.cyb.engcostms.service.SupplierService;
import com.cyb.engcostms.vo.MaterialVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
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

    @Autowired
    private ParamesServices paramesServices;

    @Autowired
    private SupplierService supplierService;

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
            tips.setValidate(false);
            if(StringUtils.isEmpty(material.getType())){
                tips.setMsg("物料类型不能为空");
            }else if(StringUtils.isEmpty(material.getMaterialName())){
                tips.setMsg("物料名称不能为空");
            }else{
                Material param = new Material();
                param.setType(material.getType());
                param.setMaterialName(material.getMaterialName());
                Material existMaterial = materialService.getOne(material);
                if(null == existMaterial){
                    int count = materialService.save(material);
                    if (count > 0) {
                        tips = new Tips("保存成功", true);
                    }
                }else{
                    tips.setMsg("相同物料已存在");
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
    public Tips deleteById(@RequestParam(value = "id")String id) {
        super.validLogined();
        if (isLogined) {
            tips.setValidate(false);
            int count = materialService.deleteById(id);
            if (count > 0) {
                tips = new Tips("删除成功", true);
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
            tips.setValidate(false);
            Material existMaterial = materialService.getOne(material);
            if(null == existMaterial){
                int count = materialService.update(material);
                if (count > 0) {
                    tips = new Tips("更新成功", true);
                }
            }else{
                tips.setMsg("相同物料已存在");
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
            List<Material> list = materialService.list(material, true);
            List<MaterialVO> resultList = setVO(list);
            tips.setData(resultList);
            tips.setMsg("查询成功");
        }
        return tips;
    }

    /**
     * 列表查询-根据物料名称查询供应商
     * @param materialName
     * @return
     */
    @ResponseBody
    @GetMapping("/getSuppListByMaterialName")
    public Tips getSuppListByMaterialName(@RequestParam("materialName") String materialName) {
        super.validLogined();

        if (isLogined) {
            tips.setValidate(false);
            if(StringUtils.isNotEmpty(materialName)){
                List<Supplier> supplierList = null;
                Material material = new Material();
                material.setMaterialName(materialName);
                List<Material> list = materialService.list(material, false);
                if(CollectionUtils.isNotEmpty(list)){
                    supplierList = new ArrayList<Supplier>(list.size());
                    for(Material m : list){
                        Supplier supplier = supplierService.detailBySupplierId(m.getSupplierId());
                        supplierList.add(supplier);
                    }
                }
                tips = new Tips("查询成功", true, supplierList);
            }else{
                tips.setMsg("物料名称不能为空");
            }
        }
        return tips;
    }

    /**
     * 分页列表查询
     * @param material
     * @param pagination
     * @return
     */
    @ResponseBody
    @GetMapping("/page")
    public TipsPagination<MaterialVO> page(Material material, Pagination pagination) {

        TipsPagination<MaterialVO> tipsPagination = new TipsPagination<>();
        super.validLogined();
        tipsPagination.convertFromTips(tips);
        if (isLogined) {
            Pagination<Material> result = materialService.page(material, pagination);
            if(null != result && CollectionUtils.isNotEmpty(result.getDatas())){

                Pagination<MaterialVO> paginationResult = new Pagination<MaterialVO>();
                BeanUtils.copyProperties(result, paginationResult, "data");
                List<MaterialVO> resultList = setVO(result.getDatas());
                paginationResult.setDatas(resultList);
                tipsPagination.setPagination(paginationResult);
            }
            tipsPagination.setMsg("查询成功");
        }
        return tipsPagination;
    }

    private List<MaterialVO> setVO(List<Material> list){
        List<MaterialVO> resultList = null;
        if(null != list && CollectionUtils.isNotEmpty(list)){
            resultList = new ArrayList<>(list.size());
            for(Material data : list){

                MaterialVO vo = new MaterialVO();
                BeanUtils.copyProperties(data, vo);

                Parames parames = paramesServices.selectByPrimaryKey(data.getType());
                if(null != parames){
                    vo.setTypeName(parames.getName());
                }

                Supplier supplier = supplierService.detailBySupplierId(data.getSupplierId());
                if(null != supplier){
                    vo.setSupplierName(supplier.getSupplierName());
                }
                resultList.add(vo);
            }
        }
        return resultList;
    }
}
