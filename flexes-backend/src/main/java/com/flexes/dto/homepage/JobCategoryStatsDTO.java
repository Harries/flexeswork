package com.flexes.dto.homepage;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 职位分类统计数据传输对象
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobCategoryStatsDTO {

    /**
     * 分类ID
     */
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类图标
     */
    private String icon;

    /**
     * 背景样式类
     */
    private String bgClass;

    /**
     * 开放职位数量
     */
    private Integer openPositions;

    /**
     * 分类描述
     */
    private String description;
}
