package com.donggei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.domain.entity.Role;
import com.donggei.mapper.RoleMapper;
import com.donggei.service.RoleService;
import com.donggei.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        //如果是超级管理员 集合中只需要一个 admin 即可
        if (SecurityUtils.isAdmin()){
            ArrayList<String> l = new ArrayList<>();
            l.add("admin");
            return l;
        }
        //如果是其他  查询用户具有的角色信息
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}

