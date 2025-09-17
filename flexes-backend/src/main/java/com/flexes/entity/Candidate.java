package com.flexes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import java.util.List;

/**
 * 求职者实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "candidates", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_location", columnList = "location"),
    @Index(name = "idx_experience", columnList = "experience_years"),
    @Index(name = "idx_job_status", columnList = "job_status")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Candidate extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "candidate_id")
    private Long candidateId;

    @NotNull(message = "用户ID不能为空")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank(message = "姓名不能为空")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "current_position", length = 200)
    private String currentPosition;

    @Min(value = 0, message = "工作经验年数不能为负数")
    @Max(value = 50, message = "工作经验年数不能超过50年")
    @Column(name = "experience_years")
    @Builder.Default
    private Integer experienceYears = 0;

    @Column(name = "education_level")
    @Enumerated(EnumType.ORDINAL)
    private EducationLevel educationLevel;

    @Min(value = 0, message = "期望薪资不能为负数")
    @Column(name = "expected_salary_min")
    private Integer expectedSalaryMin;

    @Min(value = 0, message = "期望薪资不能为负数")
    @Column(name = "expected_salary_max")
    private Integer expectedSalaryMax;

    @Column(name = "job_status")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private JobStatus jobStatus = JobStatus.OPEN_TO_OPPORTUNITIES;

    @Column(name = "resume_url", length = 500)
    private String resumeUrl;

    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "skills", columnDefinition = "JSON")
    private String skills;

    // 关联关系
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<JobApplication> applications;

    /**
     * 教育水平枚举
     */
    public enum EducationLevel {
        HIGH_SCHOOL(1, "高中"),
        ASSOCIATE(2, "专科"),
        BACHELOR(3, "本科"),
        MASTER(4, "硕士"),
        DOCTORATE(5, "博士");

        private final int code;
        private final String description;

        EducationLevel(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static EducationLevel fromCode(int code) {
            for (EducationLevel level : values()) {
                if (level.code == code) {
                    return level;
                }
            }
            throw new IllegalArgumentException("未知的教育水平代码: " + code);
        }
    }

    /**
     * 求职状态枚举
     */
    public enum JobStatus {
        NOT_LOOKING(0, "不找工作"),
        OPEN_TO_OPPORTUNITIES(1, "看机会"),
        ACTIVELY_LOOKING(2, "急找工作");

        private final int code;
        private final String description;

        JobStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static JobStatus fromCode(int code) {
            for (JobStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的求职状态代码: " + code);
        }
    }

    /**
     * 检查是否正在找工作
     */
    public boolean isLookingForJob() {
        return JobStatus.ACTIVELY_LOOKING.equals(this.jobStatus) || 
               JobStatus.OPEN_TO_OPPORTUNITIES.equals(this.jobStatus);
    }

    /**
     * 获取期望薪资范围字符串
     */
    public String getExpectedSalaryRange() {
        if (expectedSalaryMin != null && expectedSalaryMax != null) {
            return expectedSalaryMin + " - " + expectedSalaryMax;
        } else if (expectedSalaryMin != null) {
            return expectedSalaryMin + "+";
        } else if (expectedSalaryMax != null) {
            return "最高 " + expectedSalaryMax;
        }
        return "面议";
    }
}
