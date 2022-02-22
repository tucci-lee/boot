package com.tuccicode.boot.system.domain.entity.role;

import com.tuccicode.boot.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class SysRoleQuery extends PageQuery {

    private String name;

}

