package com.wz.test.enums;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wz.shiro.enums.LoginType;

import java.util.HashMap;
import java.util.Map;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.enums
 * @className: DefaultLoginType
 * @description:
 * @author: zhi
 * @date: 2020/12/10 下午4:04
 * @version: 1.0
 */
@JsonTypeName("defaultLoginType")
public enum DefaultLoginType implements LoginType {

    /**
     * 账号密码登录
     */
    SIMPLE(1, "账号"),

    /**
     * 邮箱密码登录
     */
    EMAIL(2, "邮箱"),

    /**
     * 微信
     */
    WE_CHAT(3, "微信"),

    ;

    private int type;

    private String desc;

    DefaultLoginType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    /**
     * 登录类型
     */
    @Override
    public int getType() {
        return type;
    }

    /**
     * 类型名称
     */
    @Override
    public String getName() {
        return this.name();
    }

    public String getDesc() {
        return desc;
    }

    private static final Map<Integer, DefaultLoginType> MAP = new HashMap<>(values().length);

    static {
        for (DefaultLoginType loginType : values()) {
            MAP.put(loginType.type, loginType);
        }
    }

    public static DefaultLoginType getInstance(int type) {
        return MAP.get(type);
    }

}
