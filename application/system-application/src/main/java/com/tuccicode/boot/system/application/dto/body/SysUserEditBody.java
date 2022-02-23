package com.tuccicode.boot.system.application.dto.body;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author tucci.lee
 */
@Data
public class SysUserEditBody {

    @NotNull
    private Long uid;

    @NotBlank
    @Pattern(regexp = "^[1][3-9][0-9]{9}$")
    private String phone;

    @Email
    private String email;

    @Size(max = 20)
    private String nickname;

    @Size(max = 200)
    private String remarks;

    private Long deptId;
}
