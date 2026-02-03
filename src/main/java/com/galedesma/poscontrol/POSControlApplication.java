package com.galedesma.poscontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.galedesma.poscontrol.repository")
public class POSControlApplication {

    public static void main(String[] args) {
        SpringApplication.run(POSControlApplication.class, args);
    }

}
