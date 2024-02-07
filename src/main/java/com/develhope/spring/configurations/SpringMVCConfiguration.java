package com.develhope.spring.configurations;

import com.develhope.spring.interceptors.AdminInterceptor;
import com.develhope.spring.interceptors.ClientInterceptor;
import com.develhope.spring.interceptors.SellerInterceptor;
import com.develhope.spring.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SpringMVCConfiguration implements WebMvcConfigurer {
    @Autowired
    private ClientInterceptor clientInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private SellerInterceptor sellerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(clientInterceptor).addPathPatterns("/v1/client/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/v1/admin/**");
        registry.addInterceptor(sellerInterceptor).addPathPatterns("/v1/seller/**");
    }


}
