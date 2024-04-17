package com.kid.anh_thunh_nas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class AnhThunhNasApplication {

    public static void main(String[] args) {
//        SpringApplication.run(AnhThunhNasApplication.class, args);
        SpringApplication.run(AnhThunhNasApplication.class, args);
    }

}
