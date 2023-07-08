package com.donggei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.donggei.domain.entity.Role;

import java.util.List;



public interface RoleMapper extends BaseMapper<Role> {
    //根据用户id查询角色信息
    List<String> selectRoleKeyByUserId(Long userId);

    List<Long> selectRoleIdByUserId(Long userId);
}


