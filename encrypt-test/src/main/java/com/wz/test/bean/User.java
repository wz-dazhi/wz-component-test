package com.wz.test.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String userNo;

    private String username;

    @NotBlank(message = "{user.password.NotBlank}")
    private String password;

}