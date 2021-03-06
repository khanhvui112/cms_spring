package com.fa.cms_spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CmsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmsSpringApplication.class, args);
    }

}
