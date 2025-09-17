package com.flexes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import java.time.LocalDate;

/**
 * 每日申请限制记录实体类
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Entity
@Table(name = "daily_application_limits",
       uniqueConstraints = @UniqueConstraint(name = "uk_candidate_date", columnNames = {"candidate_id", "application_date"}),
       indexes = {
    @Index(name = "idx_application_date", columnList = "applicationDate")
})
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyApplicationLimit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "limit_id")
    private Long limitId;

    @NotNull(message = "求职者ID不能为空")
    @Column(name = "candidate_id", nullable = false)
    private Long candidateId;

    @NotNull(message = "申请日期不能为空")
    @Column(name = "application_date", nullable = false)
    private LocalDate applicationDate;

    @Min(value = 0, message = "申请次数不能为负数")
    @Column(name = "application_count")
    @Builder.Default
    private Integer applicationCount = 0;

    // 关联关系
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    @JsonIgnore
    private Candidate candidate;

    /**
     * 增加申请次数
     */
    public void incrementCount() {
        this.applicationCount = (this.applicationCount == null ? 0 : this.applicationCount) + 1;
    }

    /**
     * 检查是否达到限制
     */
    public boolean hasReachedLimit(int dailyLimit) {
        return this.applicationCount != null && this.applicationCount >= dailyLimit;
    }

    /**
     * 获取剩余申请次数
     */
    public int getRemainingCount(int dailyLimit) {
        int current = this.applicationCount == null ? 0 : this.applicationCount;
        return Math.max(0, dailyLimit - current);
    }
}
