package com.tuccicode.boot.app.system.dto.body;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tucci.lee
 */
@Data
public class SysUserRoleUpdateBody extends DTO {

    @NotNull
    private Long uid;

    @NotEmpty
    private List<Long> roleIds;
}
