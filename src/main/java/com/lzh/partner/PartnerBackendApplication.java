package com.lzh.partner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PartnerBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(PartnerBackendApplication.class, args);
    }

}
