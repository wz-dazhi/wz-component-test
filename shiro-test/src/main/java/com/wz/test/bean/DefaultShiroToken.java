package com.wz.test.bean;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wz.shiro.enums.LoginType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.shiro.authc.UsernamePasswordToken;

import javax.validation.constraints.NotBlank;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.bean
 * @className: ShiroToken
 * @description:
 * @author: zhi
 * @date: 2020/12/10 下午4:11
 * @version: 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("shiro登录")
public class DefaultShiroToken extends UsernamePasswordToken {

    @ApiModelProperty("登录类型：")
    @JsonIgnore
    private LoginType loginType;

    /**
     * 用户名, 邮箱, 微信code
     */
    //@NotBlank(message = "{shiro.username.NotBlank}") // shiro Realm 做了校验
    @ApiModelProperty("用户名, 邮箱, 微信code")
    @JsonAlias({"username", "email", "code"})
    private String username;

    //@NotNull(message = "{shiro.password.NotNull}") // shiro Realm 做了校验
    @ApiModelProperty("密码")
    private char[] password;

    @NotBlank(message = "{shiro.password.NotNull}")
    private String test;
}
