package com.flexes.dto.homepage;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 平台统计数据传输对象
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlatformStatsDTO {

    /**
     * 总职位数
     */
    private Integer totalJobs;

    /**
     * 总公司数
     */
    private Integer totalCompanies;

    /**
     * 总候选人数
     */
    private Integer totalCandidates;

    /**
     * 成功匹配数
     */
    private Integer successfulMatches;

    /**
     * 本周新增职位数
     */
    private Integer newJobsThisWeek;

    /**
     * 平均薪资
     */
    private Integer averageSalary;

    /**
     * 热门技能统计
     */
    private List<SkillStatsDTO> topSkills;

    /**
     * 热门地点统计
     */
    private List<LocationStatsDTO> topLocations;

    /**
     * 技能统计数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SkillStatsDTO {
        private String skill;
        private Integer count;
    }

    /**
     * 地点统计数据
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LocationStatsDTO {
        private String location;
        private Integer count;
    }
}
