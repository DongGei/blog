package com.donggei.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @className: RoutersVo
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/8/14
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutersVo {
    private List<MenuVo> menus;
}
