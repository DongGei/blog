package com.donggei.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "用户注册表单")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterVo {
    //用户名
    @ApiModelProperty(name = "userName",value="用户名",required = true)
    private String userName;
    //昵称
    //用户名
    @ApiModelProperty(name = "nickName",value="昵称",required = true)
    private String nickName;
    //密码
    @ApiModelProperty(name = "password",value="密码",required = true)
    private String password;

    //邮箱
    @ApiModelProperty(name = "email",value="邮箱",required = true)
    private String email;


}
