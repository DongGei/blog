package com.donggei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.donggei.domain.ResponseResult;
import com.donggei.domain.entity.User;
import com.donggei.domain.vo.UserInfoVo;
import com.donggei.domain.vo.UserRegisterVo;
import com.donggei.enums.AppHttpCodeEnum;
import com.donggei.exception.SystemException;
import com.donggei.mapper.UserMapper;
import com.donggei.service.UserService;
import com.donggei.utils.BeanCopyUtils;
import com.donggei.utils.SecurityUtils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public ResponseResult userInfo() {
        //获取当前用户id
        Long userId = SecurityUtils.getUserId();
        //根据用户id查询用户信息
        User user = getById(userId);
        //封装成UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult updateUserInfo(UserInfoVo userInfoVo) {
        User user = new User();
        user = BeanCopyUtils.copyBean(userInfoVo,User.class);
        Long userId = SecurityUtils.getUserId();
        user.setId(userId);
        //存入数据库
        updateById(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult register(UserRegisterVo userRegisterVo) {
        //对数据进行非空判断
        if(!StringUtils.hasText(userRegisterVo.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(userRegisterVo.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(userRegisterVo.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(userRegisterVo.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(userRegisterVo.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(userRegisterVo.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //...
        //对密码进行加密
        String encodePassword = passwordEncoder.encode(userRegisterVo.getPassword());
        userRegisterVo.setPassword(encodePassword);

        User user = new User();
        user = BeanCopyUtils.copyBean(userRegisterVo,User.class);
        //存入数据库
        save(user);
        return ResponseResult.okResult();
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getUserName,userName);
        return count(queryWrapper) > 0;
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(User::getPassword,nickName);
        return count(queryWrapper) > 0;
    }

}
