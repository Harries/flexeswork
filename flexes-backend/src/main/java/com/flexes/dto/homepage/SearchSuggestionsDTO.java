package com.flexes.dto.homepage;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * 搜索建议数据传输对象
 * 
 * @author Flexes Team
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchSuggestionsDTO {

    /**
     * 职位标题建议
     */
    private List<String> jobTitles;

    /**
     * 技能建议
     */
    private List<String> skills;

    /**
     * 公司建议
     */
    private List<String> companies;

    /**
     * 地点建议
     */
    private List<String> locations;
}
