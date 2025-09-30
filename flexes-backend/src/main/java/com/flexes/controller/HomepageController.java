package com.flexes.controller;

import com.flexes.dto.homepage.*;
import com.flexes.service.HomepageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页控制器
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/homepage")
@RequiredArgsConstructor
@Tag(name = "首页API", description = "首页相关接口")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomepageController {

    private final HomepageService homepageService;

    /**
     * 获取首页所有数据
     */
    @GetMapping
    @Operation(summary = "获取首页数据", description = "获取首页所有模块的数据")
    public ResponseEntity<Map<String, Object>> getHomepageData() {
        log.info("获取首页数据");
        
        try {
            HomepageDataDTO data = homepageService.getHomepageData();
            return ResponseEntity.ok(createSuccessResponse("获取成功", data));
        } catch (Exception e) {
            log.error("获取首页数据失败", e);
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("获取首页数据失败: " + e.getMessage()));
        }
    }

    /**
     * 获取职位分类统计
     */
    @GetMapping("/job-categories")
    @Operation(summary = "获取职位分类统计", description = "获取所有职位分类及每个分类下的职位数量统计")
    public ResponseEntity<Map<String, Object>> getJobCategoryStats() {
        log.info("获取职位分类统计");
        
        try {
            List<JobCategoryStatsDTO> data = homepageService.getJobCategoryStats();
            return ResponseEntity.ok(createSuccessResponse("获取成功", data));
        } catch (Exception e) {
            log.error("获取职位分类统计失败", e);
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("获取职位分类统计失败: " + e.getMessage()));
        }
    }

    /**
     * 获取最新职位
     */
    @GetMapping("/latest-jobs")
    @Operation(summary = "获取最新职位", description = "获取首页展示的最新职位列表")
    public ResponseEntity<Map<String, Object>> getLatestJobs(
            @Parameter(description = "职位数量限制") @RequestParam(defaultValue = "5") Integer limit,
            @Parameter(description = "是否只显示精选职位") @RequestParam(required = false) Boolean featured) {
        log.info("获取最新职位，限制数量: {}, 精选职位: {}", limit, featured);
        
        try {
            List<HomepageDataDTO.LatestJobDTO> data = homepageService.getLatestJobs(limit, featured);
            return ResponseEntity.ok(createSuccessResponse("获取成功", data));
        } catch (Exception e) {
            log.error("获取最新职位失败", e);
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("获取最新职位失败: " + e.getMessage()));
        }
    }

    /**
     * 获取顶级公司
     */
    @GetMapping("/top-companies")
    @Operation(summary = "获取顶级公司", description = "获取首页展示的顶级合作公司")
    public ResponseEntity<Map<String, Object>> getTopCompanies(
            @Parameter(description = "公司数量限制") @RequestParam(defaultValue = "8") Integer limit) {
        log.info("获取顶级公司，限制数量: {}", limit);
        
        try {
            List<HomepageDataDTO.TopCompanyDTO> data = homepageService.getTopCompanies(limit);
            return ResponseEntity.ok(createSuccessResponse("获取成功", data));
        } catch (Exception e) {
            log.error("获取顶级公司失败", e);
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("获取顶级公司失败: " + e.getMessage()));
        }
    }

    /**
     * 获取用户评价
     */
    @GetMapping("/reviews")
    @Operation(summary = "获取用户评价", description = "获取首页展示的用户评价")
    public ResponseEntity<Map<String, Object>> getUserReviews(
            @Parameter(description = "评价数量限制") @RequestParam(defaultValue = "3") Integer limit) {
        log.info("获取用户评价，限制数量: {}", limit);
        
        try {
            List<HomepageDataDTO.UserReviewDTO> data = homepageService.getUserReviews(limit);
            return ResponseEntity.ok(createSuccessResponse("获取成功", data));
        } catch (Exception e) {
            log.error("获取用户评价失败", e);
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("获取用户评价失败: " + e.getMessage()));
        }
    }

    /**
     * 获取博客文章
     */
    @GetMapping("/blog-articles")
    @Operation(summary = "获取博客文章", description = "获取首页展示的博客文章")
    public ResponseEntity<Map<String, Object>> getBlogArticles(
            @Parameter(description = "文章数量限制") @RequestParam(defaultValue = "3") Integer limit) {
        log.info("获取博客文章，限制数量: {}", limit);
        
        try {
            List<HomepageDataDTO.BlogArticleDTO> data = homepageService.getBlogArticles(limit);
            return ResponseEntity.ok(createSuccessResponse("获取成功", data));
        } catch (Exception e) {
            log.error("获取博客文章失败", e);
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("获取博客文章失败: " + e.getMessage()));
        }
    }

    /**
     * 获取搜索建议
     */
    @GetMapping("/search-suggestions")
    @Operation(summary = "获取搜索建议", description = "获取搜索框的智能建议")
    public ResponseEntity<Map<String, Object>> getSearchSuggestions(
            @Parameter(description = "搜索关键词") @RequestParam String q,
            @Parameter(description = "建议数量限制") @RequestParam(defaultValue = "10") Integer limit) {
        log.info("获取搜索建议，关键词: {}, 限制数量: {}", q, limit);
        
        try {
            SearchSuggestionsDTO data = homepageService.getSearchSuggestions(q, limit);
            return ResponseEntity.ok(createSuccessResponse("获取成功", data));
        } catch (Exception e) {
            log.error("获取搜索建议失败", e);
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("获取搜索建议失败: " + e.getMessage()));
        }
    }

    /**
     * 获取平台统计数据
     */
    @GetMapping("/platform-stats")
    @Operation(summary = "获取平台统计数据", description = "获取平台整体统计数据用于首页展示")
    public ResponseEntity<Map<String, Object>> getPlatformStats() {
        log.info("获取平台统计数据");
        
        try {
            PlatformStatsDTO data = homepageService.getPlatformStats();
            return ResponseEntity.ok(createSuccessResponse("获取成功", data));
        } catch (Exception e) {
            log.error("获取平台统计数据失败", e);
            return ResponseEntity.internalServerError()
                    .body(createErrorResponse("获取平台统计数据失败: " + e.getMessage()));
        }
    }

    /**
     * 创建成功响应
     */
    private Map<String, Object> createSuccessResponse(String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }

    /**
     * 创建错误响应
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);
        response.put("data", null);
        response.put("timestamp", LocalDateTime.now());
        return response;
    }
}
