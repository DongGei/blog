package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.constants.SystemConstants;
import com.donggei.domain.entity.Menu;
import com.donggei.domain.vo.MenuVo;
import com.donggei.mapper.MenuMapper;
import com.donggei.service.MenuService;
import com.donggei.utils.BeanCopyUtils;
import com.donggei.utils.SecurityUtils;
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
        // if (id == 1L){ 效果一致
        if (SecurityUtils.isAdmin()) {

            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_MENU, SystemConstants.MENU_TYPE_BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menusList = list(wrapper);
            List<String> perms = menusList.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            return perms;
        }
//getBaseMapper 拿到的就是MenuMapper  也可以用Autowired
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<MenuVo> selectRouterMenuTreeByUserId(Long id) {
        MenuMapper mapper = getBaseMapper();
        List<MenuVo> menus = null;
        //如果是管理员 	如果用户id为1代表管理员，menus中需要有所有菜单类型为C或者M的，状态为正常的，未被删除的权限
        if (SecurityUtils.isAdmin()) {
            List<Menu> menuVos = mapper.selectAllRouterMenu();
            menus = BeanCopyUtils.copyBeanList(menuVos, MenuVo.class);
        } else {
            //否则 用户具有的menu
            List<Menu> menuVos = mapper.selectRouterMenuTreeByUserId(id);
            menus = BeanCopyUtils.copyBeanList(menuVos, MenuVo.class);

        }

        //构建tree的层级关系
        //先找出第一层的菜单 再找他们的子菜单 设置到他们的children中
        List<MenuVo> menuVoTree = builderMenuTree(menus, 0L);
        return menuVoTree;
    }

    private List<MenuVo> builderMenuTree(List<MenuVo> menus, long parenId) {
        List<MenuVo> menuVoList = menus.stream()
                .filter(menu -> menu.getParentId().equals(parenId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuVoList;
    }

    /**
     * 获取传入参数的 子menu   实际项目很少有三层 但是现在为了万一有 也写了
     *
     * @param menu 要获取子菜单的菜单
     * @param menus 所有菜单
     * @return
     */
    private List<MenuVo> getChildren(MenuVo menu, List<MenuVo> menus) {
        return menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                //上面已经找到了子菜单 下面找一下子菜单的子菜单
                .map(m -> m.setChildren(getChildren(m, menus)))
                .collect(Collectors.toList());
    }
}

