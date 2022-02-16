package com.tuccicode.boot.sys.application.dto.body;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tucci.lee
 */
@Data
public class SysRoleEditBody extends DTO {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 10)
    private String name;

    @Size(max = 20)
    private String roleChar;

    @Size(max = 200)
    private String remarks;
}
