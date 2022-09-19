package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.donggei.domain.entity.RoleMenu;

public interface RoleMenuService extends IService<RoleMenu> {

    void deleteRoleMenuByRoleId(Long id);
}