package com.flexes.repository;

import com.flexes.entity.Employer;
import com.flexes.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 雇主数据访问接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Repository
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    /**
     * 根据用户ID查找雇主
     */
    Optional<Employer> findByUserId(Long userId);

    /**
     * 根据公司名称查找雇主
     */
    List<Employer> findByCompanyNameContainingIgnoreCase(String companyName);

    /**
     * 根据行业查找雇主
     */
    Page<Employer> findByIndustryContainingIgnoreCase(String industry, Pageable pageable);

    /**
     * 根据公司规模查找雇主
     */
    Page<Employer> findByCompanySize(Employer.CompanySize companySize, Pageable pageable);

    /**
     * 根据地点查找雇主
     */
    Page<Employer> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    /**
     * 查找已认证的雇主
     */
    Page<Employer> findByVerifiedTrue(Pageable pageable);

    /**
     * 查找未认证的雇主
     */
    Page<Employer> findByVerifiedFalse(Pageable pageable);

    /**
     * 根据认证状态查找雇主
     */
    Page<Employer> findByVerified(Boolean verified, Pageable pageable);

    /**
     * 复合条件搜索雇主
     */
    @Query("SELECT e FROM Employer e WHERE " +
           "(:keyword IS NULL OR " +
           " LOWER(e.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:industry IS NULL OR LOWER(e.industry) LIKE LOWER(CONCAT('%', :industry, '%'))) AND " +
           "(:location IS NULL OR LOWER(e.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:companySize IS NULL OR e.companySize = :companySize) AND " +
           "(:verified IS NULL OR e.verified = :verified)")
    Page<Employer> searchEmployers(@Param("keyword") String keyword,
                                  @Param("industry") String industry,
                                  @Param("location") String location,
                                  @Param("companySize") Employer.CompanySize companySize,
                                  @Param("verified") Boolean verified,
                                  Pageable pageable);

    /**
     * 统计各行业的雇主数量
     */
    @Query("SELECT e.industry, COUNT(e) FROM Employer e WHERE e.industry IS NOT NULL GROUP BY e.industry")
    List<Object[]> countEmployersByIndustry();

    /**
     * 统计各公司规模的雇主数量
     */
    @Query("SELECT e.companySize, COUNT(e) FROM Employer e GROUP BY e.companySize")
    List<Object[]> countEmployersByCompanySize();

    /**
     * 统计认证状态的雇主数量
     */
    @Query("SELECT e.verified, COUNT(e) FROM Employer e GROUP BY e.verified")
    List<Object[]> countEmployersByVerificationStatus();

    /**
     * 查找有活跃职位的雇主
     */
    @Query("SELECT DISTINCT e FROM Employer e " +
           "JOIN Job j ON e.employerId = j.employerId " +
           "WHERE j.status = :status")
    Page<Employer> findEmployersWithActiveJobs(@Param("status") Job.JobStatus status, Pageable pageable);

    /**
     * 统计雇主发布的职位数量
     */
    @Query("SELECT e.employerId, e.companyName, COUNT(j) as jobCount " +
           "FROM Employer e LEFT JOIN Job j ON e.employerId = j.employerId " +
           "GROUP BY e.employerId, e.companyName " +
           "ORDER BY jobCount DESC")
    List<Object[]> getEmployersWithJobCount();

    /**
     * 查找最活跃的雇主（按职位数量排序）
     */
    @Query("SELECT e FROM Employer e " +
           "LEFT JOIN Job j ON e.employerId = j.employerId " +
           "GROUP BY e.employerId " +
           "ORDER BY COUNT(j) DESC")
    List<Employer> findMostActiveEmployers(Pageable pageable);

    /**
     * 根据成立年份范围查找雇主
     */
    @Query("SELECT e FROM Employer e WHERE " +
           "(:startYear IS NULL OR e.foundedYear >= :startYear) AND " +
           "(:endYear IS NULL OR e.foundedYear <= :endYear)")
    Page<Employer> findByFoundedYearBetween(@Param("startYear") Integer startYear,
                                           @Param("endYear") Integer endYear,
                                           Pageable pageable);

    /**
     * 查找有网站的雇主
     */
    @Query("SELECT e FROM Employer e WHERE e.website IS NOT NULL AND e.website != ''")
    Page<Employer> findEmployersWithWebsite(Pageable pageable);

    /**
     * 查找有Logo的雇主
     */
    @Query("SELECT e FROM Employer e WHERE e.companyLogo IS NOT NULL AND e.companyLogo != ''")
    Page<Employer> findEmployersWithLogo(Pageable pageable);
}
