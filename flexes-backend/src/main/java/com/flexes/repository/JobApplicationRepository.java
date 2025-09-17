package com.flexes.repository;

import com.flexes.entity.JobApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 职位申请数据访问接口
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    /**
     * 根据职位ID查找申请
     */
    Page<JobApplication> findByJobId(Long jobId, Pageable pageable);

    /**
     * 根据求职者ID查找申请
     */
    Page<JobApplication> findByCandidateId(Long candidateId, Pageable pageable);

    /**
     * 根据状态查找申请
     */
    Page<JobApplication> findByStatus(JobApplication.ApplicationStatus status, Pageable pageable);

    /**
     * 检查是否已申请过该职位
     */
    boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);

    /**
     * 根据职位和求职者查找申请
     */
    Optional<JobApplication> findByJobIdAndCandidateId(Long jobId, Long candidateId);

    /**
     * 根据职位ID和状态查找申请
     */
    Page<JobApplication> findByJobIdAndStatus(Long jobId, JobApplication.ApplicationStatus status, Pageable pageable);

    /**
     * 根据求职者ID和状态查找申请
     */
    Page<JobApplication> findByCandidateIdAndStatus(Long candidateId, JobApplication.ApplicationStatus status, Pageable pageable);

    /**
     * 统计职位的申请数量
     */
    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.jobId = :jobId")
    Long countByJobId(@Param("jobId") Long jobId);

    /**
     * 统计求职者的申请数量
     */
    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.candidateId = :candidateId")
    Long countByCandidateId(@Param("candidateId") Long candidateId);

    /**
     * 统计求职者指定状态的申请数量
     */
    @Query("SELECT COUNT(ja) FROM JobApplication ja WHERE ja.candidateId = :candidateId AND ja.status = :status")
    Long countByCandidateIdAndStatus(@Param("candidateId") Long candidateId, 
                                    @Param("status") JobApplication.ApplicationStatus status);

    /**
     * 查找求职者最近的申请
     */
    @Query("SELECT ja FROM JobApplication ja WHERE ja.candidateId = :candidateId ORDER BY ja.createdAt DESC")
    List<JobApplication> findRecentApplicationsByCandidate(@Param("candidateId") Long candidateId, Pageable pageable);

    /**
     * 查找职位最近的申请
     */
    @Query("SELECT ja FROM JobApplication ja WHERE ja.jobId = :jobId ORDER BY ja.createdAt DESC")
    List<JobApplication> findRecentApplicationsByJob(@Param("jobId") Long jobId, Pageable pageable);

    /**
     * 根据申请时间范围查找申请
     */
    @Query("SELECT ja FROM JobApplication ja WHERE ja.createdAt BETWEEN :startTime AND :endTime")
    List<JobApplication> findByAppliedAtBetween(@Param("startTime") LocalDateTime startTime,
                                               @Param("endTime") LocalDateTime endTime);

    /**
     * 统计各状态申请数量
     */
    @Query("SELECT ja.status, COUNT(ja) FROM JobApplication ja GROUP BY ja.status")
    List<Object[]> countApplicationsByStatus();

    /**
     * 查找雇主收到的所有申请
     */
    @Query("SELECT ja FROM JobApplication ja " +
           "JOIN Job j ON ja.jobId = j.jobId " +
           "WHERE j.employerId = :employerId")
    Page<JobApplication> findApplicationsByEmployer(@Param("employerId") Long employerId, Pageable pageable);

    /**
     * 查找雇主收到的指定状态申请
     */
    @Query("SELECT ja FROM JobApplication ja " +
           "JOIN Job j ON ja.jobId = j.jobId " +
           "WHERE j.employerId = :employerId AND ja.status = :status")
    Page<JobApplication> findApplicationsByEmployerAndStatus(@Param("employerId") Long employerId,
                                                           @Param("status") JobApplication.ApplicationStatus status,
                                                           Pageable pageable);

    /**
     * 统计雇主收到的申请数量
     */
    @Query("SELECT COUNT(ja) FROM JobApplication ja " +
           "JOIN Job j ON ja.jobId = j.jobId " +
           "WHERE j.employerId = :employerId")
    Long countApplicationsByEmployer(@Param("employerId") Long employerId);

    /**
     * 查找指定时间后的申请
     */
    @Query("SELECT ja FROM JobApplication ja WHERE ja.createdAt >= :since")
    List<JobApplication> findApplicationsSince(@Param("since") LocalDateTime since);

    /**
     * 查找待处理的申请（雇主视角）
     */
    @Query("SELECT ja FROM JobApplication ja " +
           "JOIN Job j ON ja.jobId = j.jobId " +
           "WHERE j.employerId = :employerId AND ja.status = :status " +
           "ORDER BY ja.createdAt ASC")
    List<JobApplication> findPendingApplicationsByEmployer(@Param("employerId") Long employerId,
                                                          @Param("status") JobApplication.ApplicationStatus status);

    /**
     * 查找成功的申请（求职者视角）
     */
    @Query("SELECT ja FROM JobApplication ja WHERE ja.candidateId = :candidateId AND ja.status = :status")
    List<JobApplication> findSuccessfulApplicationsByCandidate(@Param("candidateId") Long candidateId,
                                                              @Param("status") JobApplication.ApplicationStatus status);
}
