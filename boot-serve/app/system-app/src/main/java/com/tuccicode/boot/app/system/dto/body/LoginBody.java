package com.tuccicode.boot.app.system.dto.body;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author tucci.lee
 */
@Data
public class LoginBody extends DTO {

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Boolean rememberMe;

    @NotBlank
    private String captcha;
}
