package com.wz.test.authc;

import com.wz.shiro.authc.AbstractShiroRealm;
import com.wz.test.bean.DefaultShiroToken;
import com.wz.test.bean.User;
import com.wz.test.enums.DefaultLoginType;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
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
public class EmailRealm extends AbstractShiroRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof DefaultShiroToken && DefaultLoginType.EMAIL == ((DefaultShiroToken) token).getLoginType();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("---> 开始邮箱登录. {}", token);
        final DefaultShiroToken t = (DefaultShiroToken) token;
        // 模拟数据库查询
        if (!"123@qq.com".equals(t.getUsername())) {
            throw new UnknownAccountException("用户不存在");
        }

        // 数据库中的密码
        String dbPassword = "e10adc3949ba59abbe56e057f20f883e";
        return new SimpleAuthenticationInfo(new User("0000001", t.getUsername(), DefaultLoginType.EMAIL), dbPassword, this.getName());
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
