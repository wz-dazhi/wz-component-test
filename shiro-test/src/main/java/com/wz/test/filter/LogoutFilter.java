package com.wz.test.filter;

import com.wz.common.util.Results;
import com.wz.redis.util.RedisUtil;
import com.wz.shiro.bean.ShiroProperties;
import com.wz.test.authc.EmailRealm;
import com.wz.test.authc.SimpleRealm;
import com.wz.test.bean.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Locale;
import java.util.Objects;

import static com.wz.shiro.enums.ShiroEnum.METHOD_NOT_ALLOWED;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.filter
 * @className: LogoutFilter
 * @description:
 * @author: zhi
 * @date: 2020/12/16 下午1:42
 * @version: 1.0
 */
@Slf4j
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    @Resource
    private ShiroProperties shiroProperties;
    @Resource
    private SimpleRealm simpleRealm;
    @Resource
    private EmailRealm emailRealm;
    @Resource
    private CacheManager cacheManager;

    private String tokenPrefixKey;

    @PostConstruct
    public void init() {
        tokenPrefixKey = shiroProperties.getCacheKeyPrefix() + shiroProperties.getToken();
    }

    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        // 检查是否开启使用post请求
        if (isPostOnlyLogout()) {
            // 如果当前请求方式不等于post or get, 直接返回false
            final String requestMethod = WebUtils.toHttp(request).getMethod().toUpperCase(Locale.ENGLISH);
            if (!AccessControlFilter.POST_METHOD.equals(requestMethod) && !AccessControlFilter.GET_METHOD.equals(requestMethod)) {
                com.wz.webmvc.filter.FilterHelper.writeResponse(response, Results.fail(METHOD_NOT_ALLOWED));
                return false;
            }
        }

        // 清空缓存
        final User principal = (User) subject.getPrincipal();
        if (Objects.nonNull(principal)) {
            final String key = FilterHelper.getTokenKey(tokenPrefixKey, principal);
            RedisUtil.del(key);
            this.delCache(subject);
        } else {
            // 未认证, 不需要退出
            com.wz.webmvc.filter.FilterHelper.writeResponse(response, Results.ok());
            return false;
        }

        // 获取重定向地址;
        // 退出成功后, 跳转页面应由前端来定
        //String redirectUrl = getRedirectUrl(request, response, subject);
        //try/catch added for SHIRO-298:
        try {
            subject.logout();
        } catch (SessionException ise) {
            log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        //issueRedirect(request, response, redirectUrl);
        com.wz.webmvc.filter.FilterHelper.writeResponse(response, Results.ok());
        return false;
    }

    private void delCache(Subject s) {
        final Object principal = s.getPrincipal();
        final User u = (User) principal;
        final String username = u.getUsername();
        String authenticationCacheName;
        String authorizationCacheName;
        switch (u.getLoginType()) {
            case SIMPLE:
                authenticationCacheName = simpleRealm.getAuthenticationCacheName();
                authorizationCacheName = simpleRealm.getAuthorizationCacheName();
                break;
            case EMAIL:
                authenticationCacheName = emailRealm.getAuthenticationCacheName();
                authorizationCacheName = emailRealm.getAuthorizationCacheName();
                break;
            case WE_CHAT:
            default:
                return;
        }
        cacheManager.getCache(authenticationCacheName).remove(username);
        cacheManager.getCache(authorizationCacheName).remove(username);
    }

}
