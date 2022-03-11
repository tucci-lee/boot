package com.tuccicode.boot.system.application.dto.body;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
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
