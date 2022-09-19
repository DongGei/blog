package com.donggei.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2022-08-13 17:01:18
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    List<Role> selectRoleAll();
    void insertRole(Role role);
    ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize);

    void updateRole(Role role);

    List<Long> selectRoleIdByUserId(Long userId);
}

