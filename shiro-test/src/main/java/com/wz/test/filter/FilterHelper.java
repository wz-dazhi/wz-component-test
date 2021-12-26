package com.wz.test.filter;

import com.wz.test.bean.User;
import lombok.extern.slf4j.Slf4j;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.filter
 * @className: FilterHelper
 * @description:
 * @author: zhi
 * @date: 2020/12/16 下午2:03
 * @version: 1.0
 */
@Slf4j
public final class FilterHelper {
    /**
     * 秘钥
     */
    public static final String SECRET = "al9aiza50yhwuqviws9z4nxzhtaio3bd";

    public static String getTokenKey(String tokenPrefixKey, User u) {
        return tokenPrefixKey + ":" + u.getLoginType().getName().toLowerCase() + ":" + u.getUserNo();
    }

}
