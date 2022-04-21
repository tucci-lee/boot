package com.tuccicode.boot.app.system.dto.body;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class SysUserEditLockBody extends DTO {

    @NotNull
    private Long uid;

    @NotNull
    private Boolean isLock;
}
