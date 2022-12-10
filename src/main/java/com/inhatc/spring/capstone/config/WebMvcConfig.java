package com.inhatc.spring.capstone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 파일 관련 설정 파일
 * 업로드한 파일을 읽어올 경로를 설정
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {    // alt + shift + p -> 메소드 오버라이드

    @Value(value = "${uploadPath}")     // 프로퍼티 값 읽어오기
    String uploadPath;
    @Value(value = "${resourceHandlerUrl}")
    String resourceHandlerUrl;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	//  /images/~~라고 들어올 경우에 
    	//  로컬경로(uploadPath)에 실제 파일을 매핑시켜서 보여준다.
        registry.addResourceHandler(resourceHandlerUrl+"**")         
                .addResourceLocations(uploadPath);
    }
}
