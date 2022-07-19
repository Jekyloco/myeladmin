package com.jekyloco.service.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AuthUserDto {
    //@NotBlank 作用于String上，该属性不能null，调用trim()后，长度必需大于0
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";
}
