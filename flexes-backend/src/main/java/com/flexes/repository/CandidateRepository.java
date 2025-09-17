package com.flexes.repository;

import com.flexes.entity.Candidate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 求职者数据访问接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    /**
     * 根据用户ID查找求职者
     */
    Optional<Candidate> findByUserId(Long userId);

    /**
     * 根据姓名查找求职者
     */
    List<Candidate> findByNameContainingIgnoreCase(String name);

    /**
     * 根据地点查找求职者
     */
    Page<Candidate> findByLocationContainingIgnoreCase(String location, Pageable pageable);

    /**
     * 根据工作经验年数查找求职者
     */
    Page<Candidate> findByExperienceYearsBetween(Integer minYears, Integer maxYears, Pageable pageable);

    /**
     * 根据求职状态查找求职者
     */
    Page<Candidate> findByJobStatus(Candidate.JobStatus jobStatus, Pageable pageable);

    /**
     * 根据教育水平查找求职者
     */
    Page<Candidate> findByEducationLevel(Candidate.EducationLevel educationLevel, Pageable pageable);

    /**
     * 根据期望薪资范围查找求职者
     */
    @Query("SELECT c FROM Candidate c WHERE " +
           "(:minSalary IS NULL OR c.expectedSalaryMin >= :minSalary) AND " +
           "(:maxSalary IS NULL OR c.expectedSalaryMax <= :maxSalary)")
    Page<Candidate> findBySalaryRange(@Param("minSalary") Integer minSalary,
                                     @Param("maxSalary") Integer maxSalary,
                                     Pageable pageable);

    /**
     * 复合条件搜索求职者
     */
    @Query("SELECT c FROM Candidate c WHERE " +
           "(:keyword IS NULL OR " +
           " LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(c.currentPosition) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           " LOWER(c.bio) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:location IS NULL OR LOWER(c.location) LIKE LOWER(CONCAT('%', :location, '%'))) AND " +
           "(:minExperience IS NULL OR c.experienceYears >= :minExperience) AND " +
           "(:maxExperience IS NULL OR c.experienceYears <= :maxExperience) AND " +
           "(:educationLevel IS NULL OR c.educationLevel = :educationLevel) AND " +
           "(:jobStatus IS NULL OR c.jobStatus = :jobStatus)")
    Page<Candidate> searchCandidates(@Param("keyword") String keyword,
                                    @Param("location") String location,
                                    @Param("minExperience") Integer minExperience,
                                    @Param("maxExperience") Integer maxExperience,
                                    @Param("educationLevel") Candidate.EducationLevel educationLevel,
                                    @Param("jobStatus") Candidate.JobStatus jobStatus,
                                    Pageable pageable);

    /**
     * 查找正在找工作的求职者
     */
    @Query("SELECT c FROM Candidate c WHERE c.jobStatus IN (:activeStatuses)")
    Page<Candidate> findActiveCandidates(@Param("activeStatuses") List<Candidate.JobStatus> activeStatuses, 
                                        Pageable pageable);

    /**
     * 统计各求职状态的求职者数量
     */
    @Query("SELECT c.jobStatus, COUNT(c) FROM Candidate c GROUP BY c.jobStatus")
    List<Object[]> countCandidatesByJobStatus();

    /**
     * 统计各教育水平的求职者数量
     */
    @Query("SELECT c.educationLevel, COUNT(c) FROM Candidate c GROUP BY c.educationLevel")
    List<Object[]> countCandidatesByEducationLevel();

    /**
     * 统计各经验水平的求职者数量
     */
    @Query("SELECT " +
           "CASE " +
           "  WHEN c.experienceYears = 0 THEN '应届生' " +
           "  WHEN c.experienceYears BETWEEN 1 AND 2 THEN '初级' " +
           "  WHEN c.experienceYears BETWEEN 3 AND 5 THEN '中级' " +
           "  WHEN c.experienceYears BETWEEN 6 AND 10 THEN '高级' " +
           "  ELSE '专家' " +
           "END as experienceLevel, COUNT(c) " +
           "FROM Candidate c GROUP BY " +
           "CASE " +
           "  WHEN c.experienceYears = 0 THEN '应届生' " +
           "  WHEN c.experienceYears BETWEEN 1 AND 2 THEN '初级' " +
           "  WHEN c.experienceYears BETWEEN 3 AND 5 THEN '中级' " +
           "  WHEN c.experienceYears BETWEEN 6 AND 10 THEN '高级' " +
           "  ELSE '专家' " +
           "END")
    List<Object[]> countCandidatesByExperienceLevel();

    /**
     * 查找有简历的求职者
     */
    @Query("SELECT c FROM Candidate c WHERE c.resumeUrl IS NOT NULL")
    Page<Candidate> findCandidatesWithResume(Pageable pageable);

    /**
     * 查找没有简历的求职者
     */
    @Query("SELECT c FROM Candidate c WHERE c.resumeUrl IS NULL")
    Page<Candidate> findCandidatesWithoutResume(Pageable pageable);
}
