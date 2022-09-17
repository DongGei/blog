package com.donggei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.donggei.domain.entity.Menu;
import com.donggei.domain.vo.MenuVo;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-13 16:54:27
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);
}


