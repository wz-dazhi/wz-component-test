package com.wz.test.bean;

import com.wz.test.enums.DefaultLoginType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @projectName: test-wz-component
 * @package: com.wz.shiro.bean
 * @className: User
 * @description:
 * @author: zhi
 * @date: 2020/12/15 下午4:18
 * @version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String userNo;
    private String username;
    private DefaultLoginType loginType;

}
