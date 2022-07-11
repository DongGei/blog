package com.donggei.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: HotArticleVO
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/10
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticleVo {
    private Long id;
    private String title;
    private Long viewCount;
}
