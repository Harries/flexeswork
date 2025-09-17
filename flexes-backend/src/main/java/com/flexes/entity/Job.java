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
import java.time.LocalDate;
import java.util.List;

/**
 * 职位实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "jobs", indexes = {
    @Index(name = "idx_employer_id", columnList = "employer_id"),
    @Index(name = "idx_category_id", columnList = "category_id"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at"),
    @Index(name = "idx_location", columnList = "location"),
    @Index(name = "idx_employment_type", columnList = "employment_type"),
    @Index(name = "idx_experience_level", columnList = "experience_level"),
    @Index(name = "idx_featured", columnList = "featured")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Job extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    private Long jobId;

    @NotNull(message = "雇主ID不能为空")
    @Column(name = "employer_id", nullable = false)
    private Long employerId;

    @Column(name = "category_id")
    private Integer categoryId;

    @NotBlank(message = "职位标题不能为空")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @NotBlank(message = "职位描述不能为空")
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "requirements", columnDefinition = "TEXT")
    private String requirements;

    @Column(name = "responsibilities", columnDefinition = "TEXT")
    private String responsibilities;

    @Column(name = "skills_required", columnDefinition = "JSON")
    private String skillsRequired;

    @Column(name = "skills_preferred", columnDefinition = "JSON")
    private String skillsPreferred;

    @NotNull(message = "工作类型不能为空")
    @Column(name = "employment_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private EmploymentType employmentType;

    @Column(name = "experience_level")
    @Enumerated(EnumType.ORDINAL)
    private ExperienceLevel experienceLevel;

    @Column(name = "education_level")
    @Enumerated(EnumType.ORDINAL)
    private EducationLevel educationLevel;

    @Min(value = 0, message = "薪资不能为负数")
    @Column(name = "salary_min")
    private Integer salaryMin;

    @Min(value = 0, message = "薪资不能为负数")
    @Column(name = "salary_max")
    private Integer salaryMax;

    @Column(name = "salary_currency", length = 10)
    @Builder.Default
    private String salaryCurrency = "USD";

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "remote_type")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private RemoteType remoteType = RemoteType.FULLY_REMOTE;

    @Column(name = "benefits", columnDefinition = "TEXT")
    private String benefits;

    @Column(name = "application_deadline")
    private LocalDate applicationDeadline;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private JobStatus status = JobStatus.PENDING_APPROVAL;

    @Column(name = "view_count")
    @Builder.Default
    private Integer viewCount = 0;

    @Column(name = "application_count")
    @Builder.Default
    private Integer applicationCount = 0;

    @Column(name = "featured")
    @Builder.Default
    private Boolean featured = false;

    // 关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", insertable = false, updatable = false)
    @JsonIgnore
    private Employer employer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    @JsonIgnore
    private JobCategory category;

    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<JobApplication> applications;

    /**
     * 工作类型枚举
     */
    public enum EmploymentType {
        FULL_TIME(1, "全职"),
        PART_TIME(2, "兼职"),
        CONTRACT(3, "合同工"),
        INTERNSHIP(4, "实习");

        private final int code;
        private final String description;

        EmploymentType(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static EmploymentType fromCode(int code) {
            for (EmploymentType type : values()) {
                if (type.code == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("未知的工作类型代码: " + code);
        }
    }

    /**
     * 经验要求枚举
     */
    public enum ExperienceLevel {
        ENTRY_LEVEL(1, "应届生"),
        JUNIOR(2, "初级"),
        MID_LEVEL(3, "中级"),
        SENIOR(4, "高级"),
        EXPERT(5, "专家");

        private final int code;
        private final String description;

        ExperienceLevel(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static ExperienceLevel fromCode(int code) {
            for (ExperienceLevel level : values()) {
                if (level.code == code) {
                    return level;
                }
            }
            throw new IllegalArgumentException("未知的经验要求代码: " + code);
        }
    }

    /**
     * 学历要求枚举
     */
    public enum EducationLevel {
        NO_REQUIREMENT(1, "不限"),
        HIGH_SCHOOL(2, "高中"),
        ASSOCIATE(3, "专科"),
        BACHELOR(4, "本科"),
        MASTER(5, "硕士"),
        DOCTORATE(6, "博士");

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
            throw new IllegalArgumentException("未知的学历要求代码: " + code);
        }
    }

    /**
     * 远程工作类型枚举
     */
    public enum RemoteType {
        FULLY_REMOTE(1, "完全远程"),
        HYBRID(2, "混合办公"),
        ON_SITE(3, "现场办公");

        private final int code;
        private final String description;

        RemoteType(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static RemoteType fromCode(int code) {
            for (RemoteType type : values()) {
                if (type.code == code) {
                    return type;
                }
            }
            throw new IllegalArgumentException("未知的远程工作类型代码: " + code);
        }
    }

    /**
     * 职位状态枚举
     */
    public enum JobStatus {
        PENDING_APPROVAL(0, "待审核"),
        ACTIVE(1, "招聘中"),
        PAUSED(2, "暂停"),
        CLOSED(3, "已关闭");

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
            throw new IllegalArgumentException("未知的职位状态代码: " + code);
        }
    }

    /**
     * 检查职位是否激活
     */
    public boolean isActive() {
        return JobStatus.ACTIVE.equals(this.status);
    }

    /**
     * 检查职位是否已过期
     */
    public boolean isExpired() {
        return applicationDeadline != null && applicationDeadline.isBefore(LocalDate.now());
    }

    /**
     * 检查是否为精选职位
     */
    public boolean isFeatured() {
        return Boolean.TRUE.equals(this.featured);
    }

    /**
     * 获取薪资范围字符串
     */
    public String getSalaryRange() {
        if (salaryMin != null && salaryMax != null) {
            return salaryMin + " - " + salaryMax + " " + salaryCurrency;
        } else if (salaryMin != null) {
            return salaryMin + "+ " + salaryCurrency;
        } else if (salaryMax != null) {
            return "最高 " + salaryMax + " " + salaryCurrency;
        }
        return "面议";
    }
}
