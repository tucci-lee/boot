package com.tuccicode.boot.app.system.dto.query;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class SysRoleListQuery extends PageQuery {

    private String name;

}

