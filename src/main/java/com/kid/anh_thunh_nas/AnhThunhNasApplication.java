package com.kid.anh_thunh_nas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@SpringBootApplication
public class AnhThunhNasApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnhThunhNasApplication.class, args);
    }

}
