package edu.ncu.chattingsys.friend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class FriendMain {
    public static void main(String[] args) {
        SpringApplication.run(FriendMain.class,args);
    }
}