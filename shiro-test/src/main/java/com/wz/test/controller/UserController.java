package com.wz.test.controller;

import com.wz.common.model.Result;
import com.wz.common.util.Results;
import com.wz.test.bean.DefaultShiroToken;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.controller
 * @className: UserController
 * @description:
 * @author: zhi
 * @date: 2020/12/10 下午3:16
 * @version: 1.0
 */
@Api(tags = "用户相关")
@RestController
public class UserController {

    @PostMapping("/user")
    public Result<String> user(@Valid @RequestBody DefaultShiroToken token) { // 测试 @Valid
        final Subject subject = SecurityUtils.getSubject();
        final Session session = subject.getSession();
        final Object value = session.getAttribute("test-key");
        if (Objects.isNull(value)) {
            session.setAttribute("test-key", Results.ok());
        }
        return Results.ok("登录之后就可以访问." + subject.getPrincipal() + ", session value: " + value);
    }

}
