package com.tuccicode.boot.app.system.dto.query;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class SysUserListQuery extends PageQuery {

    private String username;

    private String phone;

    private Boolean isLock;

    private Long deptId;
}
