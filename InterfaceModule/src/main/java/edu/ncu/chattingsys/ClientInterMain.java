package edu.ncu.chattingsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ClientInterMain {
    public static void main(String[] args) {
        SpringApplication.run(ClientInterMain.class,args);
    }
}
