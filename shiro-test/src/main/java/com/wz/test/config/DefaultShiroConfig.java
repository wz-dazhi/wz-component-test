package com.wz.test.config;

import com.wz.shiro.bean.ShiroProperties;
import com.wz.shiro.config.AbstractShiroConfig;
import com.wz.test.authc.EmailRealm;
import com.wz.test.authc.SimpleRealm;
import com.wz.test.filter.LoginFilter;
import com.wz.test.filter.LogoutFilter;
import org.apache.shiro.realm.Realm;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.config
 * @className: ShiroConfig
 * @description:
 * @author: zhi
 * @date: 2020/12/10 下午2:21
 * @version: 1.0
 */
@Configuration
@ConditionalOnProperty(name = {"shiro.enabled", "shiro.annotations.enabled"}, matchIfMissing = true)
@EnableConfigurationProperties({RedisProperties.class, ShiroProperties.class})
public class DefaultShiroConfig extends AbstractShiroConfig {

    @Bean
    public Realm simpleRealm() {
        final SimpleRealm simpleRealm = new SimpleRealm();
        simpleRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        simpleRealm.setAuthenticationCachingEnabled(true);
        simpleRealm.setAuthorizationCachingEnabled(true);
        return simpleRealm;
    }

    @Bean
    public Realm emailRealm() {
        final EmailRealm emailRealm = new EmailRealm();
        emailRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        emailRealm.setAuthenticationCachingEnabled(true);
        emailRealm.setAuthorizationCachingEnabled(true);
        return emailRealm;
    }

    @Bean
    public LoginFilter loginFilter() {
        return new LoginFilter(shiroProperties);
    }

    @Bean
    public LogoutFilter logoutFilter() {
        return new LogoutFilter();
    }

    /**
     * 登录filter设置不启用. 避免拦截多个请求
     */
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilterRegistrationBean(LoginFilter loginFilter) {
        final FilterRegistrationBean<LoginFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(loginFilter);
        filterFilterRegistrationBean.setEnabled(false);
        return filterFilterRegistrationBean;
    }

    /**
     * 退出filter设置不启用. 避免拦截多个请求
     */
    @Bean
    public FilterRegistrationBean<LogoutFilter> logoutFilterRegistrationBean(LogoutFilter logoutFilter) {
        final FilterRegistrationBean<LogoutFilter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(logoutFilter);
        filterFilterRegistrationBean.setEnabled(false);
        return filterFilterRegistrationBean;
    }

}
