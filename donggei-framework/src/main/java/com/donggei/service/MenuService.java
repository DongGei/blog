package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.donggei.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2022-08-13 16:54:29
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);
}

