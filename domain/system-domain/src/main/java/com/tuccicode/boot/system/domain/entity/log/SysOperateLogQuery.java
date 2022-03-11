package com.tuccicode.boot.system.domain.entity.log;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class SysOperateLogQuery extends PageQuery {

    private String username;

    private String ip;

    private Boolean status;

    private Long beginTime;

    private Long endTime;
}
