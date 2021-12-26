package com.wz.test.authc;

import com.wz.shiro.authc.AbstractShiroRealm;
import com.wz.test.bean.DefaultShiroToken;
import com.wz.test.bean.User;
import com.wz.test.enums.AppEnum;
import com.wz.test.enums.DefaultLoginType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.authc
 * @className: SimpleRealm
 * @description:
 * @author: zhi
 * @date: 2020/12/10 下午5:26
 * @version: 1.0
 */
@Slf4j
public class SimpleRealm extends AbstractShiroRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof DefaultShiroToken && DefaultLoginType.SIMPLE == ((DefaultShiroToken) token).getLoginType();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("---> 开始账号密码登录. {}", token);
        final DefaultShiroToken t = (DefaultShiroToken) token;
        // 模拟数据库查询
        if (!"zhangsan".equals(t.getUsername())) {
            throw new UnknownAccountException(AppEnum.USER_UNKNOWN.getMsg());
        }
        if (!"123456".equals(String.valueOf(t.getPassword()))) {
            throw new IncorrectCredentialsException(AppEnum.PASSWORD_ERROR.getMsg());
        }

        // 数据库中的密码
        String dbPassword = "e10adc3949ba59abbe56e057f20f883e";
        return new SimpleAuthenticationInfo(new User("0000001", t.getUsername(), DefaultLoginType.SIMPLE), dbPassword, this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    public static void main(String[] args) {
        String password = "123456";
        Md5Hash md5 = new Md5Hash(password);
        // e10adc3949ba59abbe56e057f20f883e
        System.out.println(md5.toHex());
    }
}
