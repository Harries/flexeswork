package com.flexes.repository;

import com.flexes.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 职位数据访问接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    /**
     * 根据雇主ID查找职位
     */
    Page<Job> findByEmployerId(Long employerId, Pageable pageable);

    /**
     * 根据状态查找职位
     */
    Page<Job> findByStatus(Job.JobStatus status, Pageable pageable);

    /**
     * 根据分类查找职位
     */
    Page<Job> findByCategoryId(Integer categoryId, Pageable pageable);

    /**
     * 根据工作类型查找职位
     */
    Page<Job> findByEmploymentType(Job.EmploymentType employmentType, Pageable pageable);

    /**
     * 根据经验要求查找职位
     */
    Page<Job> findByExperienceLevel(Job.ExperienceLevel experienceLevel, Pageable pageable);

    /**
     * 根据远程工作类型查找职位
     */
    Page<Job> findByRemoteType(Job.RemoteType remoteType, Pageable pageable);

    /**
     * 查找精选职位
     */
    Page<Job> findByFeaturedTrueAndStatus(Job.JobStatus status, Pageable pageable);

    /**
     * 根据地点查找职位
     */
    Page<Job> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    /**
     * 根据薪资范围查找职位
     */
    @Query("SELECT j FROM Job j WHERE j.salaryMin >= :minSalary AND j.salaryMax <= :maxSalary")
    Page<Job> findBySalaryRange(@Param("minSalary") Integer minSalary, 
                               @Param("maxSalary") Integer maxSalary, 
                               Pageable pageable);

    /**
     * 全文搜索职位
     */
    @Query("SELECT j FROM Job j WHERE " +
           "LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(j.requirements) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<Job> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    /**
     * 复合条件搜索职位
     */
    @Query("SELECT j FROM Job j WHERE " +
           "(:keyword IS NULL OR " +
           " LOWER(j.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(j.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:categoryId IS NULL OR j.categoryId = :categoryId) AND " +
           "(:employmentType IS NULL OR j.employmentType = :employmentType) AND " +
           "(:experienceLevel IS NULL OR j.experienceLevel = :experienceLevel) AND " +
           "(:remoteType IS NULL OR j.remoteType = :remoteType) AND " +
           "(:minSalary IS NULL OR j.salaryMin >= :minSalary) AND " +
           "(:maxSalary IS NULL OR j.salaryMax <= :maxSalary) AND " +
           "j.status = :status")
    Page<Job> searchJobs(@Param("keyword") String keyword,
                        @Param("location") String location,
                        @Param("categoryId") Integer categoryId,
                        @Param("employmentType") Job.EmploymentType employmentType,
                        @Param("experienceLevel") Job.ExperienceLevel experienceLevel,
                        @Param("remoteType") Job.RemoteType remoteType,
                        @Param("minSalary") Integer minSalary,
                        @Param("maxSalary") Integer maxSalary,
                        @Param("status") Job.JobStatus status,
                        Pageable pageable);

    /**
     * 查找最新发布的职位
     */
    @Query("SELECT j FROM Job j WHERE j.status = :status ORDER BY j.createdAt DESC")
    List<Job> findLatestJobs(@Param("status") Job.JobStatus status, Pageable pageable);

    /**
     * 查找热门职位（按申请数量排序）
     */
    @Query("SELECT j FROM Job j WHERE j.status = :status ORDER BY j.applicationCount DESC")
    List<Job> findPopularJobs(@Param("status") Job.JobStatus status, Pageable pageable);

    /**
     * 查找即将过期的职位
     */
    @Query("SELECT j FROM Job j WHERE j.applicationDeadline BETWEEN :startDate AND :endDate AND j.status = :status")
    List<Job> findJobsExpiringBetween(@Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate,
                                     @Param("status") Job.JobStatus status);

    /**
     * 统计雇主的职位数量
     */
    @Query("SELECT COUNT(j) FROM Job j WHERE j.employerId = :employerId AND j.status = :status")
    Long countByEmployerIdAndStatus(@Param("employerId") Long employerId, 
                                   @Param("status") Job.JobStatus status);

    /**
     * 统计各状态职位数量
     */
    @Query("SELECT j.status, COUNT(j) FROM Job j GROUP BY j.status")
    List<Object[]> countJobsByStatus();

    /**
     * 统计各分类职位数量
     */
    @Query("SELECT j.categoryId, COUNT(j) FROM Job j WHERE j.status = :status GROUP BY j.categoryId")
    List<Object[]> countJobsByCategory(@Param("status") Job.JobStatus status);

    /**
     * 查找指定时间后发布的职位
     */
    @Query("SELECT j FROM Job j WHERE j.createdAt >= :since AND j.status = :status")
    List<Job> findJobsCreatedSince(@Param("since") LocalDateTime since, 
                                  @Param("status") Job.JobStatus status);

    /**
     * 根据雇主和状态查找职位
     */
    @Query("SELECT j FROM Job j WHERE j.employerId = :employerId AND j.status = :status")
    Page<Job> findByEmployerIdAndStatus(@Param("employerId") Long employerId,
                                       @Param("status") Job.JobStatus status,
                                       Pageable pageable);

    /**
     * 查找相似职位（基于分类和技能）
     */
    @Query("SELECT j FROM Job j WHERE j.categoryId = :categoryId AND j.jobId != :excludeJobId AND j.status = :status")
    List<Job> findSimilarJobs(@Param("categoryId") Integer categoryId,
                             @Param("excludeJobId") Long excludeJobId,
                             @Param("status") Job.JobStatus status,
                             Pageable pageable);
}
