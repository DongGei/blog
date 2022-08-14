package com.donggei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.domain.entity.Role;
import com.donggei.mapper.RoleMapper;
import com.donggei.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2022-08-13 17:01:22
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        return null;
    }
}

