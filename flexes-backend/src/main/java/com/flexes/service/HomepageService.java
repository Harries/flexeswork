package com.flexes.service;

import com.flexes.dto.homepage.*;

import java.util.List;

/**
 * 首页服务接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
public interface HomepageService {

    /**
     * 获取首页所有数据
     * 
     * @return 首页数据
     */
    HomepageDataDTO getHomepageData();

    /**
     * 获取职位分类统计
     * 
     * @return 职位分类统计列表
     */
    List<JobCategoryStatsDTO> getJobCategoryStats();

    /**
     * 获取最新职位
     * 
     * @param limit 限制数量
     * @param featured 是否只显示精选职位
     * @return 最新职位列表
     */
    List<HomepageDataDTO.LatestJobDTO> getLatestJobs(Integer limit, Boolean featured);

    /**
     * 获取顶级公司
     * 
     * @param limit 限制数量
     * @return 顶级公司列表
     */
    List<HomepageDataDTO.TopCompanyDTO> getTopCompanies(Integer limit);

    /**
     * 获取用户评价
     * 
     * @param limit 限制数量
     * @return 用户评价列表
     */
    List<HomepageDataDTO.UserReviewDTO> getUserReviews(Integer limit);

    /**
     * 获取博客文章
     * 
     * @param limit 限制数量
     * @return 博客文章列表
     */
    List<HomepageDataDTO.BlogArticleDTO> getBlogArticles(Integer limit);

    /**
     * 获取搜索建议
     * 
     * @param query 搜索关键词
     * @param limit 限制数量
     * @return 搜索建议
     */
    SearchSuggestionsDTO getSearchSuggestions(String query, Integer limit);

    /**
     * 获取平台统计数据
     * 
     * @return 平台统计数据
     */
    PlatformStatsDTO getPlatformStats();
}
