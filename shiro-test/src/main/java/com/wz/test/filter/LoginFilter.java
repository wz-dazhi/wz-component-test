package com.wz.test.filter;

import com.wz.common.constant.DateConsts;
import com.wz.common.exception.ParameterException;
import com.wz.common.model.JwtData;
import com.wz.common.util.DateUtil;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.JwtUtil;
import com.wz.common.util.MapUtil;
import com.wz.common.util.StringUtil;
import com.wz.redis.util.RedisUtil;
import com.wz.shiro.bean.ShiroProperties;
import com.wz.shiro.enums.LoginType;
import com.wz.shiro.filter.AbstractLoginFilter;
import com.wz.test.bean.DefaultShiroToken;
import com.wz.test.bean.User;
import com.wz.test.constant.ShiroConst;
import com.wz.test.enums.DefaultLoginType;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.filter
 * @className: LoginFilter
 * @description:
 * @author: zhi
 * @date: 2020/12/12 下午10:25
 * @version: 1.0
 */
@Slf4j
public class LoginFilter extends AbstractLoginFilter {
    private final String tokenPrefixKey;

    public LoginFilter(ShiroProperties shiroProperties) {
        tokenPrefixKey = shiroProperties.getCacheKeyPrefix() + shiroProperties.getToken();
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        // 解析body数据
        final DefaultShiroToken t;
        try {
            @Cleanup final BufferedReader reader = req.getReader();
            final String body = reader.lines()
                    .reduce(String::concat)
                    .orElseThrow(ParameterException::new);
            t = JsonUtil.toBean(body, DefaultShiroToken.class);
        } catch (Exception e) {
            log.error("Create token error. e: ", e);
            return null;
        }
        // 获取登录类型
        final LoginType loginType = this.getLoginType(req);
        if (Objects.isNull(loginType)) {
            return null;
        }
        t.setLoginType(loginType);
        return t;
    }

    /**
     * 登录成功，生成token令牌
     */
    @Override
    protected String onGenerateToken(AuthenticationToken t, Subject subject, HttpServletRequest req, HttpServletResponse resp) {
        final User principal = (User) subject.getPrincipal();

        // 过期时间从配置取
        long expire = 2;
        // jwt 一分钟过期
        final LocalDateTime expireTime = DateUtil.nowTime().plusMinutes(expire);
        final Map<String, Object> payload = MapUtil.beanToMap(principal);
        payload.put(ShiroConst.EXP_KEY, expireTime.format(DateConsts.DATE_TIME_HH_MM_SS_SSS_FORMATTER));
        final String token = JwtUtil.create(payload, FilterHelper.SECRET);
        // 根据登录类型设置token, 便于多realm登录 不被踢出
        final String key = FilterHelper.getTokenKey(tokenPrefixKey, principal);
        // redis 一分钟过期
        RedisUtil.set(key, token, expire, TimeUnit.MINUTES);
        return token;
    }

    /**
     * 校验token
     */
    @Override
    public boolean verifyToken(String token, HttpServletRequest req, HttpServletResponse resp) {
        log.debug("--> 校验token. {}", token);
        final JwtData<User> j = JwtUtil.verify(token, FilterHelper.SECRET, User.class, ShiroConst.EXP_KEY);
        if (j.isSuccess() && Objects.nonNull(j.getData())) {
            final User u = j.getData();
            // 根据登录类型设置token, 便于多realm登录 不被踢出
            final String key = FilterHelper.getTokenKey(tokenPrefixKey, u);
            final String redisToken = String.valueOf(RedisUtil.get(key));
            return token.equals(redisToken);
        }
        return false;
    }

    private LoginType getLoginType(HttpServletRequest req) {
        String loginTypeStr = req.getHeader(ShiroConst.LOGIN_TYPE);
        if (StringUtil.isBlank(loginTypeStr)) {
            loginTypeStr = req.getParameter(ShiroConst.LOGIN_TYPE);
            if (StringUtil.isBlank(loginTypeStr)) {
                log.error("Login request login_type is null. request uri: {}", req.getRequestURI());
                return null;
            }
        }
        try {
            final int loginType = Integer.parseInt(loginTypeStr);
            return DefaultLoginType.getInstance(loginType);
        } catch (NumberFormatException e) {
            log.error("login type error. type is [{}], msg: {}, e: ", loginTypeStr, e.getMessage(), e);
            return null;
        }
    }

}
