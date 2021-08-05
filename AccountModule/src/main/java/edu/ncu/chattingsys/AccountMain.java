package edu.ncu.chattingsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AccountMain {
    public static void main(String[] args) {
        SpringApplication.run(AccountMain.class,args);
    }
}

