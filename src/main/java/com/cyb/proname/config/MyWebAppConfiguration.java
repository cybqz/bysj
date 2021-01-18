package com.cyb.proname.config;

import com.cyb.proname.interceptor.MyInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class MyWebAppConfiguration extends WebMvcConfigurationSupport {

    @Value("${web.upload-path}")
    private String uploadPath;

    public static String V_PATH = "/path/";

    /**
     * @Description:
     * 对文件的路径进行配置, 创建一个虚拟路径/Path/**
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        log.info("UPLOAD_PATH:" + uploadPath);
        /*registry.addResourceHandler(V_PATH + "**")
                .addResourceLocations("file:///" + uploadPath);*/

        //默认访问static文件
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * @Author 陈迎博
     * @Title 注册拦截器
     * @Description 注册拦截器
     * @Date 2021/1/17
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration registration = registry.addInterceptor(new MyInterceptor());

        //所有路径都被拦截
        registration.addPathPatterns("/**");

        //添加不拦截路径
        registration.excludePathPatterns(
                "/login/*",
                "/**/*.html",
                "/**/*.js",
                "/**/*.css",
                "/**/*.woff",
                "/**/*.ttf"
        );
        log.info("自定义拦截器注册完成......");
    }

}
