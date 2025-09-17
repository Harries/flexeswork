package com.flexes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Flexes 远程工程招聘平台主应用类
 * 
 * @author Flexes Team
 * @version 1.0.0
 * @since 2025-09-17
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableTransactionManagement
public class FlexesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlexesApplication.class, args);
    }

}
