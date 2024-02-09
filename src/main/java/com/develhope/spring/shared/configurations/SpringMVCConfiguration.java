package com.develhope.spring.shared.configurations;

import com.develhope.spring.admin.AdminInterceptor;
import com.develhope.spring.customer.CustomerInterceptor;
import com.develhope.spring.seller.SellerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class SpringMVCConfiguration implements WebMvcConfigurer {
    @Autowired
    private CustomerInterceptor customerInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;
    @Autowired
    private SellerInterceptor sellerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customerInterceptor).addPathPatterns("/v1/client/**");
        registry.addInterceptor(adminInterceptor).addPathPatterns("/v1/admin/**");
        registry.addInterceptor(sellerInterceptor).addPathPatterns("/v1/seller/**");
    }


}
