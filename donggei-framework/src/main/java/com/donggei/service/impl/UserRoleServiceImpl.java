package com.donggei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.domain.entity.UserRole;
import com.donggei.mapper.UserRoleMapper;
import com.donggei.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}