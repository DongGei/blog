package com.donggei.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: LinkVo
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LinkVo {
    @TableId
    private Long id;

    private String name;

    private String logo;

    private String description;
    //网站地址
    private String address;
}
