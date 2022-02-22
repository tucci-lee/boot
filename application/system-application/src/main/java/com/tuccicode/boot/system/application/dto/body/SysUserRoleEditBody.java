package com.tuccicode.boot.system.application.dto.body;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tucci.lee
 */
@Data
public class SysUserRoleEditBody extends DTO {

    @NotNull
    private Long uid;

    @NotEmpty
    private List<Long> roleIds;
}
