package com.donggei.service.impl;

import com.donggei.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @className: PermissionService
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/9/19
 **/
@Service("ps")
public class PermissionService {
    /**
     * 判断当前用户是否具有permission
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission){
        //如果是超级管理员 id为1的  直接返回ture
        if (SecurityUtils.isAdmin()){
            return true;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
