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

/**
 * 用户评价实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "user_reviews", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_rating", columnList = "rating"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReview extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;

    @NotNull(message = "用户ID不能为空")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank(message = "用户角色不能为空")
    @Column(name = "user_role", nullable = false, length = 20)
    private String userRole;

    @NotBlank(message = "用户职位不能为空")
    @Column(name = "user_title", length = 100)
    private String userTitle;

    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分不能小于1")
    @Max(value = 5, message = "评分不能大于5")
    @Column(name = "rating", nullable = false)
    private Integer rating;

    @NotBlank(message = "评价内容不能为空")
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "helpful_count")
    @Builder.Default
    private Integer helpfulCount = 0;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    @Builder.Default
    private ReviewStatus status = ReviewStatus.PENDING;

    // 关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    @JsonIgnore
    private User user;

    /**
     * 评价状态枚举
     */
    public enum ReviewStatus {
        PENDING(0, "待审核"),
        APPROVED(1, "已通过"),
        REJECTED(2, "已拒绝");

        private final int code;
        private final String description;

        ReviewStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static ReviewStatus fromCode(int code) {
            for (ReviewStatus status : values()) {
                if (status.code == code) {
                    return status;
                }
            }
            throw new IllegalArgumentException("未知的评价状态代码: " + code);
        }
    }

    /**
     * 检查评价是否已通过
     */
    public boolean isApproved() {
        return ReviewStatus.APPROVED.equals(this.status);
    }

    /**
     * 检查是否为高评分
     */
    public boolean isHighRating() {
        return rating != null && rating >= 4;
    }
}
