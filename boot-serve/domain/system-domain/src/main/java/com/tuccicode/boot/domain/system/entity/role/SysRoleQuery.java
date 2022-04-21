package com.tuccicode.boot.domain.system.entity.role;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class SysRoleQuery extends PageQuery {

    private String name;

}

