package com.donggei.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: TagListDto
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/8/30
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagListDto {
    private Integer id;
private  String name;
private  String remark;
}
