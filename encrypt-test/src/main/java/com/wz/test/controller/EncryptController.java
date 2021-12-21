package com.wz.test.controller;

import com.google.common.collect.Maps;
import com.wz.common.model.Result;
import com.wz.common.util.JsonUtil;
import com.wz.common.util.Results;
import com.wz.encrypt.algorithm.DefaultEncryptAlgorithm;
import com.wz.encrypt.algorithm.EncryptAlgorithm;
import com.wz.encrypt.annotation.Decrypt;
import com.wz.encrypt.annotation.Encrypt;
import com.wz.encrypt.annotation.Sign;
import com.wz.test.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;

/**
 * @projectName: test
 * @package: com.wz.demo.controller
 * @className: EncryptController
 * @description:
 * @author: Zhi
 * @date: 2019-11-27 14:40
 * @version: 1.0
 */
@Api(tags = "加解密")
@Validated
@RestController
@RequestMapping("/api/encrypt")
public class EncryptController {

    @Sign
    @ApiOperation(value = "签名", notes = "get 签名")
    @GetMapping("/sign")
    public Result<String> sign(@NotBlank(message = "{user.username.NotBlank}") @RequestParam String username,
                               @Length(min = 3, max = 6, message = "{user.password.Length}") @RequestParam String password) {
        return Results.ok(username + "--" + password);
    }

    @Sign
    @PostMapping("/sign")
    public Result<User> sign(@Valid @RequestBody User u) {
        return Results.ok(u);
    }

    @Encrypt
    @PostMapping("/encrypt")
    public Result<User> encrypt(@Valid @RequestBody User u) {
        return Results.ok(u);
    }

    @Encrypt
    @Decrypt
    @PostMapping("/decrypt")
    public Result<User> decrypt(@Valid @RequestBody User u) {
        return Results.ok(u);
    }

    @Sign
    @Encrypt
    @Decrypt
    @PostMapping("/sign-en-de")
    public Result<User> signEnDe(@Valid @RequestBody User u) {
        return Results.ok(u);
    }

    public static void main(String[] args) throws Exception {
        //String key = "1234567890qazxsw";
//        final String secret = RandomUtil.randomString(32);
//        System.out.println(secret);
        String key = "7vtrtamtbotnnlxqupde177gjvn2hs6i";
        EncryptAlgorithm algorithm = new DefaultEncryptAlgorithm();
        Map<String, Object> map = Maps.newHashMap();
//        map.put("userNo", "123456");
        map.put("username", "张三");
//        map.put("password", "123456");
        map.put("timestamp", System.currentTimeMillis());

        String json = JsonUtil.toJson(map);
        System.out.println(json);
        // mZdcWz9eCCOG1+rFEBEWZpS3CZeFTj9O2pTpfXsCgSmGFnNsqD46SHf/GDsFCqxuMTWHiZq5clEJs3qayJldo/RgBpJt+PrbBhFXW/K+D89Eb+l/AnrHh9YMobg19BDmHHDlnioIqiSCd1TTib/Jw8tWs4U+ZtJwxZkRzAYG/34=
        final String encryptStr = algorithm.encrypt(json, key);
        System.out.println(encryptStr);


        System.out.println(algorithm.decrypt(encryptStr, key));
    }
}
