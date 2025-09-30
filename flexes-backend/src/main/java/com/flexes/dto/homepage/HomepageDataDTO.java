package com.flexes.dto.homepage;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 首页数据传输对象
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomepageDataDTO {

    /**
     * 搜索统计数据
     */
    private SearchStatsDTO searchStats;

    /**
     * 热门搜索标签
     */
    private List<String> hotSearchTags;

    /**
     * 最新职位列表
     */
    private List<LatestJobDTO> latestJobs;

    /**
     * 顶级公司列表
     */
    private List<TopCompanyDTO> topCompanies;

    /**
     * 用户评价列表
     */
    private List<UserReviewDTO> userReviews;

    /**
     * 博客文章列表
     */
    private List<BlogArticleDTO> blogArticles;

    /**
     * 搜索统计数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchStatsDTO {
        private Integer totalJobs;
        private Integer totalCompanies;
        private Integer totalCandidates;
        private Integer newJobsToday;
    }

    /**
     * 最新职位数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LatestJobDTO {
        private Long jobId;
        private String title;
        private String companyName;
        private String companyLogo;
        private Integer salaryMin;
        private Integer salaryMax;
        private String salaryCurrency;
        private String location;
        private String remoteType;
        private Boolean featured;
        private String createdAt;
    }

    /**
     * 顶级公司数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopCompanyDTO {
        private Long employerId;
        private String companyName;
        private String companyLogo;
        private String location;
        private String industry;
        private Integer activeJobCount;
        private Boolean verified;
    }

    /**
     * 用户评价数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserReviewDTO {
        private Long reviewId;
        private String userName;
        private String userAvatar;
        private String userRole;
        private Integer rating;
        private String content;
        private String createdAt;
    }

    /**
     * 博客文章数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BlogArticleDTO {
        private Long articleId;
        private String title;
        private String summary;
        private String coverImage;
        private String author;
        private String publishedAt;
        private Integer readCount;
    }
}
