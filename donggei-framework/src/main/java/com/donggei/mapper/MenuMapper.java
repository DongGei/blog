package com.donggei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.donggei.domain.entity.Menu;
import com.donggei.domain.vo.MenuVo;

import java.util.List;



public interface MenuMapper extends BaseMapper<Menu> {
    //根据用户id查询权限信息
    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);
}


