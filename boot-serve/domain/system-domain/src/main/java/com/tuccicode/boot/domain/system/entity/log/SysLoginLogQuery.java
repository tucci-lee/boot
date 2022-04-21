package com.tuccicode.boot.domain.system.entity.log;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class SysLoginLogQuery extends PageQuery {

    private String username;

    private String ip;

    private Boolean status;

    private Long beginTime;

    private Long endTime;
}
