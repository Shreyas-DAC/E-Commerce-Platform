package com.example.E_commerce_platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ={"package com.example.E_commerce_platform"})
public class ECommercePlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommercePlatformApplication.class, args);
    }

}