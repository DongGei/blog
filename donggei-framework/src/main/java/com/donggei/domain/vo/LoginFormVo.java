package com.donggei.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginFormVo {
    //用户名
    @ApiModelProperty(name = "nickName",value="用户名",required = true)
    private String userName;
    //密码
    @ApiModelProperty(name = "nickName",value="用户名",required = true)
    private String password;
}
