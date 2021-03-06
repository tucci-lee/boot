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
public class SysUserUpdatePasswordBody extends DTO {
    @NotNull
    private Long uid;

    @NotBlank
    @Size(min = 6, max = 16)
    private String password;
}
