package com.cyb.proname.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyb.proname.business.controller.base.BasicController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.Map;

/**
   * @Author 陈迎博
   * @Description 应用启动完成监听类
   * @Date 2021/1/18
   */
@Slf4j
@Component
public class AppStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.port}")
    private String port;

    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        //获取所有Controller信息
        initControllerInfo();

        //输出首页访问地址
        printUrl();
    }

    private void initControllerInfo(){

        try {
            Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(RequestMapping.class);
            for (Map.Entry<String, Object> entry : controllers.entrySet()) {

                Class<?> clazz = AopUtils.getTargetClass(entry.getValue());
                RequestMapping requestMapping = clazz.getAnnotation(RequestMapping.class);
                String name = requestMapping.value()[0];

                Field modelUrlField = null;
                Field modelNameField = null;

                for(Field field : clazz.getDeclaredFields()){

                    if(field.getName().equals("modelUrl")){
                        modelUrlField = field;
                    }else if(field.getName().equals("modelName")){
                        modelNameField = field;
                    }

                    if(null != modelNameField && null != modelUrlField){

                        Object instance = clazz.newInstance();
                        String modelName = modelNameField.get(instance).toString();
                        String modelUrl = modelUrlField.get(instance).toString();

                        BasicController.MODEL_MAP.put(name, new String[]{modelUrl, modelName});
                    }
                }
            }

            log.info(String.format("Controller Info Init Success: %s", JSONObject.toJSONString(BasicController.MODEL_MAP)));

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println();
    }

    private void printUrl(){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>项目访问地址: http://127.0.0.1:" + port + contextPath);
    }
}
