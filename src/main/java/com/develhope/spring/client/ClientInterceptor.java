package com.develhope.spring.client;

import com.develhope.spring.loginSignup.IdLogin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ClientInterceptor implements HandlerInterceptor {

    @Autowired
    private IdLogin idLogin;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if(idLogin.getId()==null){
//            response.sendError(512, "Please log in");
//            return false;
//        }
//
//
//        if(!(idLogin.getType().equals("CLIENT"))){
//            response.sendError(401, "You are not authorized");
//            return false;
//        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }






    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
