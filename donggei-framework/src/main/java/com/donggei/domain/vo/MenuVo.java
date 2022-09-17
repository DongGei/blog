package com.donggei.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @className: MenuVO
 * @description: 在RoutresVO中需要使用
 * @author: Dong
 * @date: 2022/8/14
 **/
@Data
@Accessors(chain = true)
public class MenuVo {
    //菜单ID
    private Long id;

    //菜单名称
    private String menuName;
    //父菜单ID
    private Long parentId;
    //显示顺序
    private Integer orderNum;
    //路由地址
    private String path;
    //权限标识
    private String perms;
    //组件路径
    private String component;
    //是否为外链（0是 1否）
    private Integer isFrame;
    //菜单类型（M目录 C菜单 F按钮）
    private String menuType;
    //菜单状态（0显示 1隐藏）
    private String visible;
    //菜单状态（0正常 1停用）
    private String status;

    //菜单图标
    private String icon;

    //创建时间
    private Date createTime;
   //子菜单
    private List<MenuVo> children;

}
