package edu.ncu.chattingsys.inter.config;

import edu.ncu.chattingsys.inter.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration registration=registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(
                    "/login",
                    "/loginCheck",
                    "/registerPage",
                    "/userRegister",
                    "/subscriptionRegisterPage",
                    "/SubscriptionRegister",
                    "/verifyCode",
                    "/sendMailCode",
                    "/decodeQRCode",
                    "/checkNameExist",
                    "/error",
                    "/testPage",
                    "/test",
                    "/**/*.css",
                    "/**/*.js",
                    "/**/*.ico",
                    "/**/*.gif",
                    "/**/*.woff2",
                    "/**/*.woff",
                    "/**/*.ttf"
                );
    }
}
