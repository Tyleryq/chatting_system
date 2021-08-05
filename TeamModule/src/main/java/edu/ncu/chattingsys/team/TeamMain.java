package edu.ncu.chattingsys.team;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TeamMain {
    public static void main(String[] args) {
        SpringApplication.run(TeamMain.class,args);
    }
}
