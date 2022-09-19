package com.donggei.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @className: LoginUser
 * @description: TODO 类描述
 * @author: Dong
 * @date: 2022/7/11
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    private User user;
    private List<String> permissions;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // SpringSecurity 默认的获取权限的方法 可以用List<String> permissions 转化成Collection<? extends GrantedAuthority> 返回
        // 但是!! 现在我们用自己的方法 直接写null
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }
    //下面的实现后先 全部改成 return true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
