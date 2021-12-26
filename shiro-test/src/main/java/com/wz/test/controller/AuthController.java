package com.wz.test.controller;

import com.wz.common.model.Result;
import com.wz.common.util.Results;
import com.wz.test.bean.DefaultShiroToken;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.controller
 * @className: AuthController
 * @description:
 * @author: zhi
 * @date: 2020/12/27 下午10:12
 * @version: 1.0
 */
@Api(tags = "认证相关")
@RestController
public class AuthController {

    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping("/login")
    public Result<Void> login(@RequestParam(value = "login_type", required = false) String loginType,
                              @RequestHeader(value = "login_type", required = false) String headerLoginType,
                              @Valid @RequestBody DefaultShiroToken token) {
        // 此处不实现逻辑，具体实现在 LoginFilter
        return Results.ok();
    }

    @ApiOperation(value = "退出", notes = "退出")
    @RequestMapping(value = "/logout", method = {RequestMethod.GET, RequestMethod.POST})
    public Result<Void> logout() {
        // 此处不实现逻辑，具体实现在 LogoutFilter
        return Results.ok();
    }

    //    @GetMapping("/login")
//    public Result<String> login() {
//        return Results.ok("登录页面");
//    }
//
//    @PostMapping("/login")
//    public Result<Boolean> login(@RequestBody Login login) {
//        final Subject s = SecurityUtils.getSubject();
//        final DefaultShiroToken t = new DefaultShiroToken();
//        t.setUsername(login.getUsername());
//        t.setPassword(login.getPassword().toCharArray());
//        t.setLoginType(DefaultLoginType.getInstance(login.getLoginType()));
//        try {
//            s.login(t);
//        } catch (AuthenticationException e) {
//            System.out.println("登录失败. exception class: " + e.getClass() + ", error msg: " + e.getMessage() + ", e: " + e);
//            return Results.ok(false);
//        }
//        return Results.ok(true);
//    }

//    @GetMapping("/logout")
//    public Result<String> logout() {
//        final Subject subject = SecurityUtils.getSubject();
//        subject.logout();
//        return Results.ok("退出成功!");
//    }

}
