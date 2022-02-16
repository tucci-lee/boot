package com.tuccicode.boot.sys.application.dto.body;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class LoginBody extends DTO {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Boolean rememberMe;

    @NotBlank
    private String captcha;
}
