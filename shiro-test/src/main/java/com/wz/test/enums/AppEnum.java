package com.wz.test.enums;

import com.wz.common.config.Resources;
import com.wz.common.exception.IErrorCode;

/**
 * @projectName: wz-shiro
 * @package: com.wz.shiro.enums
 * @className: ShiroEnum
 * @description:
 * @author: Zhi Wang
 * @createDate: 2020/12/15 上午10:39
 **/
public enum AppEnum implements IErrorCode {

    /**
     * 用户不存在
     */
    USER_UNKNOWN("0001"),

    /**
     * 密码不正确
     */
    PASSWORD_ERROR("0002"),

    ;

    private String code;

    AppEnum(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return Resources.getMessage("i18n/app/app", this.code);
    }
}
