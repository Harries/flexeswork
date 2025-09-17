package com.flexes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 职位分类实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "job_categories", indexes = {
    @Index(name = "idx_slug", columnList = "slug"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_sort_order", columnList = "sort_order")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @NotBlank(message = "分类名称不能为空")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @NotBlank(message = "分类标识不能为空")
    @Column(name = "slug", nullable = false, unique = true, length = 100)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "sort_order")
    @Builder.Default
    private Integer sortOrder = 0;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private CategoryStatus status = CategoryStatus.ACTIVE;

    // 关联关系
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Job> jobs;

    /**
     * 分类状态枚举
     */
    public enum CategoryStatus {
        DISABLED(0, "禁用"),
        ACTIVE(1, "启用");

        private final int code;
        private final String description;

        CategoryStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static CategoryStatus fromCode(int code) {
            for (CategoryStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的分类状态代码: " + code);
        }
    }

    /**
     * 检查分类是否启用
     */
    public boolean isActive() {
        return CategoryStatus.ACTIVE.equals(this.status);
    }
}
