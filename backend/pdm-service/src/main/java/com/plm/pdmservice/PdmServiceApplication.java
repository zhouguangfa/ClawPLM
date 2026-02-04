package com.plm.pdmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PdmServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PdmServiceApplication.class, args);
    }

}