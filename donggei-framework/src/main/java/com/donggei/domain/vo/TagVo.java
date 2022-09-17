package com.donggei.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: TagVo
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/8/31
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVo {
    /**
     * 主键
     */
    private Long id;
    private String name;
    private String remark;
}
