package com.flexes.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * 测试配置类 - 用于禁用某些配置以便测试API
 */
@Configuration
@Profile("dev")
public class TestConfiguration {
    // 这个配置类用于测试环境，暂时禁用复杂的依赖
}
