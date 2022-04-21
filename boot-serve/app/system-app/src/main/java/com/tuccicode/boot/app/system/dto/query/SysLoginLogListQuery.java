package com.tuccicode.boot.app.system.dto.query;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class SysLoginLogListQuery extends PageQuery {

    private String username;

    private String ip;

    private Boolean status;

    private Long beginTime;

    private Long endTime;
}
