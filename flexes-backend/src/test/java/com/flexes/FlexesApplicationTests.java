package com.flexes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Flexes 应用测试类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@SpringBootTest
@ActiveProfiles("test")
class FlexesApplicationTests {

    @Test
    void contextLoads() {
        // 测试Spring上下文是否能正常加载
    }

}
