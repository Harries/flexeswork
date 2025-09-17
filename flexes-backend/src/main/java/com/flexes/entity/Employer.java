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
import java.time.Year;
import java.util.List;

/**
 * 雇主实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "employers", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_company_name", columnList = "company_name"),
    @Index(name = "idx_industry", columnList = "industry"),
    @Index(name = "idx_verified", columnList = "verified")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private Long employerId;

    @NotNull(message = "用户ID不能为空")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank(message = "公司名称不能为空")
    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(name = "company_logo", length = 500)
    private String companyLogo;

    @Column(name = "industry", length = 100)
    private String industry;

    @Column(name = "company_size")
    @Enumerated(EnumType.ORDINAL)
    private CompanySize companySize;

    @Column(name = "founded_year")
    private Year foundedYear;

    @Column(name = "website", length = 300)
    private String website;

    @Column(name = "location", length = 200)
    private String location;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "contact_person", length = 100)
    private String contactPerson;

    @Column(name = "contact_phone", length = 20)
    private String contactPhone;

    @Column(name = "verified")
    @Builder.Default
    private Boolean verified = false;

    // 关联关系
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "employer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Job> jobs;

    /**
     * 公司规模枚举
     */
    public enum CompanySize {
        STARTUP(1, "1-10人"),
        SMALL(2, "11-50人"),
        MEDIUM(3, "51-200人"),
        LARGE(4, "201-1000人"),
        ENTERPRISE(5, "1000+人");

        private final int code;
        private final String description;

        CompanySize(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static CompanySize fromCode(int code) {
            for (CompanySize size : values()) {
                if (size.code == code) {
                    return size;
                }
            }
            throw new IllegalArgumentException("未知的公司规模代码: " + code);
        }
    }

    /**
     * 检查公司是否已认证
     */
    public boolean isVerified() {
        return Boolean.TRUE.equals(this.verified);
    }

    /**
     * 获取公司成立年限
     */
    public Integer getCompanyAge() {
        if (foundedYear != null) {
            return Year.now().getValue() - foundedYear.getValue();
        }
        return null;
    }

    /**
     * 获取公司规模描述
     */
    public String getCompanySizeDescription() {
        return companySize != null ? companySize.getDescription() : "未知";
    }
}
