package com.tuccicode.boot.system.application.dto.body;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author tucci.lee
 */
@Data
public class SysUserEditPasswordBody {
    @NotNull
    private Long uid;

    @NotBlank
    @Size(min = 6, max = 16)
    private String password;
}
