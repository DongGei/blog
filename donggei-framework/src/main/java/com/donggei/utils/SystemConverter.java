package com.donggei.utils;

import com.donggei.domain.entity.Menu;
import com.donggei.domain.vo.MenuTreeVo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SystemConverter {

    private SystemConverter() {
    }


    public static List<MenuTreeVo> buildMenuSelectTree(List<Menu> menus) {
        //menus 转 MenuTreeVo
        List<MenuTreeVo> MenuTreeVos = menus.stream()
                .map(m -> new MenuTreeVo(m.getId(), m.getMenuName(), m.getParentId(), null))
                .collect(Collectors.toList());
        // 从根菜单开始 设置它的子菜单
        List<MenuTreeVo> options = MenuTreeVos.stream()
                .filter(o -> o.getParentId().equals(0L))
                .map(o -> o.setChildren(getChildList(MenuTreeVos, o)))
                .collect(Collectors.toList());


        return options;
    }


    /**
     * 得到子节点列表
     */
    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        List<MenuTreeVo> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .map(o -> o.setChildren(getChildList(list, o)))
                .collect(Collectors.toList());
        return options;

    }
}