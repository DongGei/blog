package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.constants.SystemConstants;
import com.donggei.domain.entity.Menu;
import com.donggei.mapper.MenuMapper;
import com.donggei.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2022-08-13 16:54:29
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    @Override
    public List<String> selectPermsByUserId(Long id) {
        //如果用户id为1代表管理员，roles 中只需要有admin，permissions中需要有所有菜单类型为C或者F的，状态为正常的，未被删除的权限
        //如果是管理员 返回所有权限
        if (id == 1){ //TODO 封装成一个方法"id==1"
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_MENU,SystemConstants.MENU_TYPE_BUTTON);
            wrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> menusList = list(wrapper);
            List<String> perms = menusList.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
//getBaseMapper 拿到的就是MenuMapper  也可以用Autowired
        return getBaseMapper().selectPermsByUserId(id);
    }
}

