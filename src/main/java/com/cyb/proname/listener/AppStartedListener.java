package com.cyb.proname.listener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
   * @Author 陈迎博
   * @Description 应用启动完成监听类
   * @Date 2021/1/18
   */
@Component
public class AppStartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${server.port}")
    private String port;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {

        //输出首页访问地址
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>项目访问地址: http://127.0.0.1:" + port + contextPath);
    }
}
