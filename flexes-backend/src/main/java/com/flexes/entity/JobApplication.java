package com.flexes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 职位申请实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "job_applications",
       uniqueConstraints = @UniqueConstraint(name = "uk_job_candidate", columnNames = {"job_id", "candidate_id"}),
       indexes = {
    @Index(name = "idx_job_id", columnList = "job_id"),
    @Index(name = "idx_candidate_id", columnList = "candidate_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_applied_at", columnList = "applied_at")
})
@AttributeOverride(name = "createdAt", column = @Column(name = "applied_at", nullable = false, updatable = false))
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "application_id")
    private Long applicationId;

    @NotNull(message = "职位ID不能为空")
    @Column(name = "job_id", nullable = false)
    private Long jobId;

    @NotNull(message = "求职者ID不能为空")
    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @Column(name = "cover_letter", columnDefinition = "TEXT")
    private String coverLetter;

    @Column(name = "resume_url", length = 500)
    private String resumeUrl;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.SUBMITTED;

    @Column(name = "employer_notes", columnDefinition = "TEXT")
    private String employerNotes;

    // 关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    @JsonIgnore
    private Job job;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    @JsonIgnore
    private Candidate candidate;

    /**
     * 申请状态枚举
     */
    public enum ApplicationStatus {
        SUBMITTED(1, "已提交"),
        VIEWED(2, "已查看"),
        INTERVIEWING(3, "面试中"),
        REJECTED(4, "已拒绝"),
        HIRED(5, "已录用");

        private final int code;
        private final String description;

        ApplicationStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static ApplicationStatus fromCode(int code) {
            for (ApplicationStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的申请状态代码: " + code);
        }
    }

    /**
     * 检查申请是否为待处理状态
     */
    public boolean isPending() {
        return ApplicationStatus.SUBMITTED.equals(this.status);
    }

    /**
     * 检查申请是否已被查看
     */
    public boolean isViewed() {
        return ApplicationStatus.VIEWED.equals(this.status) || 
               ApplicationStatus.INTERVIEWING.equals(this.status) ||
               ApplicationStatus.REJECTED.equals(this.status) ||
               ApplicationStatus.HIRED.equals(this.status);
    }

    /**
     * 检查申请是否成功
     */
    public boolean isSuccessful() {
        return ApplicationStatus.HIRED.equals(this.status);
    }

    /**
     * 检查申请是否被拒绝
     */
    public boolean isRejected() {
        return ApplicationStatus.REJECTED.equals(this.status);
    }
}
