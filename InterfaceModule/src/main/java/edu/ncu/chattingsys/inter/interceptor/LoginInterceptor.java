package edu.ncu.chattingsys.inter.interceptor;

import edu.ncu.chattingsys.ClientInterMain;
import edu.ncu.chattingsys.inter.config.RedisConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("拦截地址:"+request.getRequestURL());

        String Uuid = request.getParameter("uid");
        String Utoken = request.getParameter("token");
        if (Uuid==null||Utoken==null){
            log.info("uid和token为空");
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        ApplicationContext context = new AnnotationConfigApplicationContext(RedisConfig.class);
        redisTemplate= (RedisTemplate) context.getBean("redisTemplate");
        String token = (String) redisTemplate.opsForValue().get(Uuid+"_token");
        if (token==null){
            log.info("token过期");
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
        if (token.equals(Utoken))   return true;
        else{
            log.info("token不一致");
            response.sendRedirect(request.getContextPath()+"/login");
            return false;
        }
    }
}
