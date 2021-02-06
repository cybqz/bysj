package com.cyb.proname.business.controller.base;

import com.cyb.authority.base.BaseController;
import com.cyb.proname.constant.SysCfgConstant;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 陈迎博
 * @Description 基础控制层
 * @Date 2021/1/24
 */
public class BasicController extends BaseController {

    public static Map<String, String[]> MODEL_MAP = new HashMap<String, String[]>();

    public String modelName = null;
    public String modelUrl = null;
    public String prefix = null;

    @RequestMapping("")
    public String all(HttpServletRequest request) {
        setModelInfo(request);
        request.setAttribute("model", modelName);
        request.setAttribute("modelUrl", modelUrl);
        request.setAttribute("title", modelName +"-列表");
        return prefix  + SysCfgConstant.DEFAULT_LIST_PAGE_SUFFIX;
    }

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
        setModelInfo(request);
        request.setAttribute("model", modelName);
        request.setAttribute("modelUrl", modelUrl);
        request.setAttribute("title", modelName +"-列表");
        return prefix  + SysCfgConstant.DEFAULT_LIST_PAGE_SUFFIX;
    }

    @RequestMapping(SysCfgConstant.METHOD_URL_ADD)
    public String add(HttpServletRequest request) {
        setModelInfo(request);
        request.setAttribute("modelUrl", modelUrl);
        request.setAttribute("title", modelName +"-新增");
        return prefix  + SysCfgConstant.METHOD_URL_ADD;
    }

    @RequestMapping(SysCfgConstant.METHOD_URL_VIEW)
    public String view(String id, HttpServletRequest request) {
        setModelInfo(request);
        request.setAttribute("operationId", id);
        request.setAttribute("modelUrl", modelUrl);
        request.setAttribute("title", modelName +"-查看");
        return prefix  + SysCfgConstant.METHOD_URL_VIEW;
    }

    @RequestMapping(SysCfgConstant.METHOD_URL_UPDATE)
    public String update(String id, HttpServletRequest request) {
        setModelInfo(request);
        request.setAttribute("operationId", id);
        request.setAttribute("modelUrl", modelUrl);
        request.setAttribute("title", modelName +"-更新");
        return prefix  + SysCfgConstant.METHOD_URL_UPDATE;
    }

    /**
     * @Author 陈迎博
     * @Title 根据请求路径设置Model信息
     * @Description
     * @Date 2021/1/24
     */
    private void setModelInfo(HttpServletRequest request) {

       String servletPath = request.getServletPath();
       setModelInfo(servletPath);
    }

    /**
     * @Author 陈迎博
     * @Title 根据请求路径设置Model信息
     * @Description
     * @Date 2021/1/24
     */
    public void setModelInfo(String servletPath) {

        if(servletPath.endsWith("/")){
            servletPath = servletPath.substring(0, servletPath.length() - 1);
        }
        if(MODEL_MAP.containsKey(servletPath)){
            String[] models = MODEL_MAP.get(servletPath);
            modelName = models[0];
            modelUrl = models[1];
            prefix = models[2];
        }
    }
}
