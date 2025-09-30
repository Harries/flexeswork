package com.flexes.service.impl;

import com.flexes.dto.homepage.*;
import com.flexes.entity.Job;
import com.flexes.entity.Employer;
import com.flexes.entity.UserReview;
import com.flexes.entity.BlogArticle;
import com.flexes.repository.JobRepository;
import com.flexes.repository.EmployerRepository;
import com.flexes.repository.UserReviewRepository;
import com.flexes.repository.BlogArticleRepository;
import com.flexes.service.HomepageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 首页服务实现类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class HomepageServiceImpl implements HomepageService {

    private final JobRepository jobRepository;
    private final EmployerRepository employerRepository;
    private final UserReviewRepository userReviewRepository;
    private final BlogArticleRepository blogArticleRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public HomepageDataDTO getHomepageData() {
        log.info("获取首页数据");

        // 获取搜索统计数据
        HomepageDataDTO.SearchStatsDTO searchStats = getSearchStats();

        // 获取热门搜索标签
        List<String> hotSearchTags = getHotSearchTags();

        // 获取最新职位
        List<HomepageDataDTO.LatestJobDTO> latestJobs = getLatestJobs(5, null);

        // 获取顶级公司
        List<HomepageDataDTO.TopCompanyDTO> topCompanies = getTopCompanies(8);

        // 获取用户评价
        List<HomepageDataDTO.UserReviewDTO> userReviews = getUserReviews(3);

        // 获取博客文章
        List<HomepageDataDTO.BlogArticleDTO> blogArticles = getBlogArticles(3);

        return HomepageDataDTO.builder()
                .searchStats(searchStats)
                .hotSearchTags(hotSearchTags)
                .latestJobs(latestJobs)
                .topCompanies(topCompanies)
                .userReviews(userReviews)
                .blogArticles(blogArticles)
                .build();
    }

    @Override
    public List<JobCategoryStatsDTO> getJobCategoryStats() {
        log.info("获取职位分类统计");

        try {
            // 从数据库查询职位分类统计
            List<Object[]> categoryStats = jobRepository.findJobCategoryStats();

            if (categoryStats.isEmpty()) {
                log.warn("数据库中没有职位分类统计数据，返回默认数据");
                return getDefaultJobCategoryStats();
            }

            return categoryStats.stream()
                    .map(stat -> JobCategoryStatsDTO.builder()
                            .categoryId(((Number) stat[0]).intValue())
                            .name((String) stat[1])
                            .icon(getIconForCategory((String) stat[1]))
                            .bgClass(getBgClassForCategory(((Number) stat[0]).intValue()))
                            .openPositions(((Number) stat[2]).intValue())
                            .description((String) stat[3])
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("查询职位分类统计失败，返回默认数据", e);
            return getDefaultJobCategoryStats();
        }
    }

    private List<JobCategoryStatsDTO> getDefaultJobCategoryStats() {
        return Arrays.asList(
                JobCategoryStatsDTO.builder()
                        .categoryId(1)
                        .name("Design & Creative")
                        .icon("ri-palette-line")
                        .bgClass("jobs-card-bg")
                        .openPositions(22)
                        .description("UI/UX Design, Graphic Design, Creative roles")
                        .build(),
                JobCategoryStatsDTO.builder()
                        .categoryId(2)
                        .name("Development & IT")
                        .icon("ri-code-line")
                        .bgClass("jobs-card-bg2")
                        .openPositions(43)
                        .description("Software Development, IT Support, DevOps")
                        .build(),
                JobCategoryStatsDTO.builder()
                        .categoryId(3)
                        .name("Sales & Marketing")
                        .icon("ri-megaphone-line")
                        .bgClass("jobs-card-bg3")
                        .openPositions(35)
                        .description("Sales, Digital Marketing, Business Development")
                        .build(),
                JobCategoryStatsDTO.builder()
                        .categoryId(4)
                        .name("Writing & Translation")
                        .icon("ri-edit-line")
                        .bgClass("jobs-card-bg4")
                        .openPositions(18)
                        .description("Content Writing, Translation, Copywriting")
                        .build(),
                JobCategoryStatsDTO.builder()
                        .categoryId(5)
                        .name("Admin & Customer Support")
                        .icon("ri-customer-service-line")
                        .bgClass("jobs-card-bg5")
                        .openPositions(27)
                        .description("Administrative, Customer Service, Virtual Assistant")
                        .build(),
                JobCategoryStatsDTO.builder()
                        .categoryId(6)
                        .name("Finance & Accounting")
                        .icon("ri-calculator-line")
                        .bgClass("jobs-card-bg6")
                        .openPositions(31)
                        .description("Accounting, Financial Analysis, Bookkeeping")
                        .build()
        );
    }

    private String getIconForCategory(String categoryName) {
        switch (categoryName.toLowerCase()) {
            case "design & creative":
                return "ri-palette-line";
            case "development & it":
                return "ri-code-line";
            case "sales & marketing":
                return "ri-megaphone-line";
            case "writing & translation":
                return "ri-edit-line";
            case "admin & customer support":
                return "ri-customer-service-line";
            case "finance & accounting":
                return "ri-calculator-line";
            case "engineering & architecture":
                return "ri-building-line";
            case "legal":
                return "ri-scales-line";
            default:
                return "ri-briefcase-line";
        }
    }

    private String getBgClassForCategory(int categoryId) {
        String[] bgClasses = {"jobs-card-bg", "jobs-card-bg2", "jobs-card-bg3",
                             "jobs-card-bg4", "jobs-card-bg5", "jobs-card-bg6"};
        return bgClasses[(categoryId - 1) % bgClasses.length];
    }

    @Override
    public List<HomepageDataDTO.LatestJobDTO> getLatestJobs(Integer limit, Boolean featured) {
        log.info("获取最新职位，限制数量: {}, 精选职位: {}", limit, featured);

        Pageable pageable = PageRequest.of(0, limit != null ? limit : 5, 
                Sort.by(Sort.Direction.DESC, "createdAt"));

        List<Job> jobs;
        if (Boolean.TRUE.equals(featured)) {
            jobs = jobRepository.findByStatusAndFeaturedOrderByCreatedAtDesc(
                    Job.JobStatus.ACTIVE, true, pageable);
        } else {
            jobs = jobRepository.findByStatusOrderByCreatedAtDesc(
                    Job.JobStatus.ACTIVE, pageable);
        }

        return jobs.stream()
                .map(this::convertToLatestJobDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HomepageDataDTO.TopCompanyDTO> getTopCompanies(Integer limit) {
        log.info("获取顶级公司，限制数量: {}", limit);

        Pageable pageable = PageRequest.of(0, limit != null ? limit : 8);
        List<Employer> employers = employerRepository.findTopCompanies(pageable);

        return employers.stream()
                .map(this::convertToTopCompanyDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HomepageDataDTO.UserReviewDTO> getUserReviews(Integer limit) {
        log.info("获取用户评价，限制数量: {}", limit);

        Pageable pageable = PageRequest.of(0, limit != null ? limit : 3);

        // 尝试从数据库获取高评分的已通过评价
        List<UserReview> reviews = userReviewRepository.findHighRatingApprovedReviews(
                UserReview.ReviewStatus.APPROVED, 4, pageable);

        // 如果数据库中没有数据，返回模拟数据
        if (reviews.isEmpty()) {
            return Arrays.asList(
                    HomepageDataDTO.UserReviewDTO.builder()
                            .reviewId(1L)
                            .userName("Thomas Albedin")
                            .userAvatar("assets/images/testimonials/testimonials-img1.jpg")
                            .userRole("IT Manager")
                            .rating(5)
                            .content("We collect reviews from our users so you can get an honest opinion of what an experience with our website are really like.")
                            .createdAt(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HomepageDataDTO.UserReviewDTO.builder()
                            .reviewId(2L)
                            .userName("Camelia Rose")
                            .userAvatar("assets/images/testimonials/testimonials-img2.jpg")
                            .userRole("Digital Marketer")
                            .rating(5)
                            .content("We collect reviews from our users so you can get an honest opinion of what an experience with our website are really like.")
                            .createdAt(LocalDateTime.now().format(DATE_FORMATTER))
                            .build(),
                    HomepageDataDTO.UserReviewDTO.builder()
                            .reviewId(3L)
                            .userName("John Carter")
                            .userAvatar("assets/images/testimonials/testimonials-img3.jpg")
                            .userRole("UI/UX Designer")
                            .rating(5)
                            .content("We collect reviews from our users so you can get an honest opinion of what an experience with our website are really like.")
                            .createdAt(LocalDateTime.now().format(DATE_FORMATTER))
                            .build()
            );
        }

        return reviews.stream()
                .map(this::convertToUserReviewDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<HomepageDataDTO.BlogArticleDTO> getBlogArticles(Integer limit) {
        log.info("获取博客文章，限制数量: {}", limit);

        Pageable pageable = PageRequest.of(0, limit != null ? limit : 3);

        // 尝试从数据库获取最新发布的文章
        List<BlogArticle> articles = blogArticleRepository.findLatestPublishedArticles(
                BlogArticle.ArticleStatus.PUBLISHED, pageable);

        // 如果数据库中没有数据，返回模拟数据
        if (articles.isEmpty()) {
            return Arrays.asList(
                    HomepageDataDTO.BlogArticleDTO.builder()
                            .articleId(1L)
                            .title("How to Make a Perfect Cv That Attracts the Attention")
                            .summary("Learn the best practices for creating an outstanding CV...")
                            .coverImage("assets/images/blog/blog-img1.jpg")
                            .author("Flexes Team")
                            .publishedAt("2024-01-01T12:00:00")
                            .readCount(1256)
                            .build(),
                    HomepageDataDTO.BlogArticleDTO.builder()
                            .articleId(2L)
                            .title("Out Bound Marketing to Get the Job You Want Within 72 Days")
                            .summary("Effective marketing strategies for job seekers...")
                            .coverImage("assets/images/blog/blog-img2.jpg")
                            .author("Marketing Team")
                            .publishedAt("2024-01-02T12:00:00")
                            .readCount(890)
                            .build(),
                    HomepageDataDTO.BlogArticleDTO.builder()
                            .articleId(3L)
                            .title("Your social media accounts will be your new CV")
                            .summary("How social media profiles impact your job search...")
                            .coverImage("assets/images/blog/blog-img3.jpg")
                            .author("HR Team")
                            .publishedAt("2024-01-03T12:00:00")
                            .readCount(567)
                            .build()
            );
        }

        return articles.stream()
                .map(this::convertToBlogArticleDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SearchSuggestionsDTO getSearchSuggestions(String query, Integer limit) {
        log.info("获取搜索建议，关键词: {}, 限制数量: {}", query, limit);

        // 模拟数据 - 实际应该从数据库查询
        return SearchSuggestionsDTO.builder()
                .jobTitles(Arrays.asList("Frontend Developer", "Backend Developer", "Full Stack Developer"))
                .skills(Arrays.asList("React", "Node.js", "Python", "Java"))
                .companies(Arrays.asList("Google", "Microsoft", "Apple"))
                .locations(Arrays.asList("New York", "San Francisco", "Seattle"))
                .build();
    }

    @Override
    public PlatformStatsDTO getPlatformStats() {
        log.info("获取平台统计数据");

        // 实际统计数据
        Long totalJobs = jobRepository.countByStatus(Job.JobStatus.ACTIVE);
        Long totalCompanies = employerRepository.count();

        return PlatformStatsDTO.builder()
                .totalJobs(totalJobs.intValue())
                .totalCompanies(totalCompanies.intValue())
                .totalCandidates(1250) // 模拟数据
                .successfulMatches(890) // 模拟数据
                .newJobsThisWeek(23) // 模拟数据
                .averageSalary(75000) // 模拟数据
                .topSkills(Arrays.asList(
                        PlatformStatsDTO.SkillStatsDTO.builder().skill("React").count(234).build(),
                        PlatformStatsDTO.SkillStatsDTO.builder().skill("Node.js").count(189).build(),
                        PlatformStatsDTO.SkillStatsDTO.builder().skill("Python").count(156).build()
                ))
                .topLocations(Arrays.asList(
                        PlatformStatsDTO.LocationStatsDTO.builder().location("New York").count(345).build(),
                        PlatformStatsDTO.LocationStatsDTO.builder().location("San Francisco").count(289).build(),
                        PlatformStatsDTO.LocationStatsDTO.builder().location("Seattle").count(234).build()
                ))
                .build();
    }

    /**
     * 获取搜索统计数据
     */
    private HomepageDataDTO.SearchStatsDTO getSearchStats() {
        Long totalJobs = jobRepository.countByStatus(Job.JobStatus.ACTIVE);
        Long totalCompanies = employerRepository.count();

        return HomepageDataDTO.SearchStatsDTO.builder()
                .totalJobs(totalJobs.intValue())
                .totalCompanies(totalCompanies.intValue())
                .totalCandidates(1250) // 模拟数据
                .newJobsToday(20) // 模拟数据
                .build();
    }

    /**
     * 获取热门搜索标签
     */
    private List<String> getHotSearchTags() {
        return Arrays.asList("React", "Node.js", "Python", "Java", "Frontend", "Backend", "Full Stack");
    }

    /**
     * 转换为最新职位DTO
     */
    private HomepageDataDTO.LatestJobDTO convertToLatestJobDTO(Job job) {
        return HomepageDataDTO.LatestJobDTO.builder()
                .jobId(job.getJobId())
                .title(job.getTitle())
                .companyName(job.getEmployer() != null ? job.getEmployer().getCompanyName() : "Unknown Company")
                .companyLogo(job.getEmployer() != null ? job.getEmployer().getCompanyLogo() : null)
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .salaryCurrency(job.getSalaryCurrency())
                .location(job.getLocation())
                .remoteType(job.getRemoteType() != null ? job.getRemoteType().getDescription() : null)
                .featured(job.getFeatured())
                .createdAt(job.getCreatedAt() != null ? job.getCreatedAt().format(DATE_FORMATTER) : null)
                .build();
    }

    /**
     * 转换为顶级公司DTO
     */
    private HomepageDataDTO.TopCompanyDTO convertToTopCompanyDTO(Employer employer) {
        return HomepageDataDTO.TopCompanyDTO.builder()
                .employerId(employer.getEmployerId())
                .companyName(employer.getCompanyName())
                .companyLogo(employer.getCompanyLogo())
                .location(employer.getLocation())
                .industry(employer.getIndustry())
                .activeJobCount(0) // 需要查询活跃职位数量
                .verified(employer.getVerified())
                .build();
    }

    /**
     * 转换为用户评价DTO
     */
    private HomepageDataDTO.UserReviewDTO convertToUserReviewDTO(UserReview review) {
        return HomepageDataDTO.UserReviewDTO.builder()
                .reviewId(review.getReviewId())
                .userName(review.getUser() != null ? review.getUser().getEmail() : "Anonymous")
                .userAvatar(null) // User实体中没有avatar字段
                .userRole(review.getUserTitle())
                .rating(review.getRating())
                .content(review.getContent())
                .createdAt(review.getCreatedAt() != null ? review.getCreatedAt().format(DATE_FORMATTER) : null)
                .build();
    }

    /**
     * 转换为博客文章DTO
     */
    private HomepageDataDTO.BlogArticleDTO convertToBlogArticleDTO(BlogArticle article) {
        return HomepageDataDTO.BlogArticleDTO.builder()
                .articleId(article.getArticleId())
                .title(article.getTitle())
                .summary(article.getSummary())
                .coverImage(article.getCoverImage())
                .author(article.getAuthorName())
                .publishedAt(article.getPublishedAt() != null ? article.getPublishedAt().format(DATE_FORMATTER) : null)
                .readCount(article.getReadCount())
                .build();
    }
}
