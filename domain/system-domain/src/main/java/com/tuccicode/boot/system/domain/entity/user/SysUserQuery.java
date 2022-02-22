package com.tuccicode.boot.system.domain.entity.user;

import com.tuccicode.boot.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class SysUserQuery extends PageQuery {

    private String username;

    private String phone;

    private Boolean isLock;

    private Long deptId;
}
