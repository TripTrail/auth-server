package com.company.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import static com.company.application.authserver.constant.Constant.COMPONENT_SCAN_PATH;

@SpringBootApplication
@ComponentScan(COMPONENT_SCAN_PATH)
public class AuthServer {

    public static void main(String args[]){
        SpringApplication.run(AuthServer.class);
    }
}
