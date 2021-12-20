package com.wz.test.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wz.datasource.mybatisplus.model.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel("用户")
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_user")
public class User extends BaseBean {
    @ApiModelProperty("用户编号")
    @TableField(value = "user_no")
    private String userNo;

    @ApiModelProperty("用户编号")
    @TableField(value = "username")
    private String username;

    @TableField(value = "`password`")
    private String password;
}