package com.wz.test.a1;

import com.wz.common.model.Result;
import com.wz.common.util.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
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
@Api(tags = "a1接口")
@RestController
@RequestMapping("/a1")
public class AController {

    @ApiOperation(notes = "a", value = "a")
    @PostMapping
    public Result<String> a() {
        return Results.ok();
    }

    @ApiOperation(notes = "a2", value = "a2")
    @PostMapping("/a2")
    public Result<User> a2() {
        return Results.ok();
    }

    @ApiOperation(notes = "a3", value = "a3")
    @PostMapping("/a3")
    public Result<User> a3(@RequestBody User user) {
        return Results.ok(user);
    }

    @ApiModel("用户")
    @Data
    public static class User {
        @ApiModelProperty("ID")
        private String id;
    }
}
