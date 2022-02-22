package com.tuccicode.boot.system.application.dto.body;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class SysUserEditLockBody {

    @NotNull
    private Long uid;

    @NotNull
    private Boolean isLock;
}
