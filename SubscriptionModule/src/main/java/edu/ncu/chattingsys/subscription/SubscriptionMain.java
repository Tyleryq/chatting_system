package edu.ncu.chattingsys.subscription;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SubscriptionMain {
    public static void main(String[] args) {
        SpringApplication.run(SubscriptionMain.class,args);
    }
}
