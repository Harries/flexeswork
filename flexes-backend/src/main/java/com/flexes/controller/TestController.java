package com.flexes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 测试控制器 - 提供模拟数据用于测试首页API
 */
@RestController
@RequestMapping("/api/v1/test")
@CrossOrigin(origins = "*")
public class TestController {

    @GetMapping("/homepage")
    public ResponseEntity<String> getHomepageData() {
        return ResponseEntity.ok("{\n" +
            "  \"message\": \"Homepage API is working!\",\n" +
            "  \"timestamp\": \"" + java.time.LocalDateTime.now() + "\",\n" +
            "  \"status\": \"success\"\n" +
            "}");
    }
}
