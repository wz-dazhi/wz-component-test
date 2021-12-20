package com.wz.test.a2;

import com.wz.common.model.Result;
import com.wz.common.util.Results;
import com.wz.datasource.mybatisplus.model.Page;
import com.wz.test.bean.User;
import com.wz.test.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @projectName: wz-component-test
 * @package: com.wz.test.a1
 * @className: AController
 * @description:
 * @author: zhi
 * @date: 2021/12/20
 * @version: 1.0
 */
@Api(tags = "a2接口")
@RestController
@RequestMapping("/a2")
@AllArgsConstructor
public class A2Controller {
    private final UserService userService;

    @ApiOperation(notes = "a", value = "a")
    @GetMapping
    public Result<String> a() {
        return Results.ok();
    }

    @ApiOperation(notes = "a2", value = "a2")
    @GetMapping("/a2")
    public Result<Page<User>> a2() {
        return Results.ok();
    }

    @ApiOperation(notes = "page", value = "page")
    @PostMapping("/page")
    public Result<Page<User>> page(@RequestBody Page<User> page) {
        return Results.ok(userService.page(page));
    }

}
