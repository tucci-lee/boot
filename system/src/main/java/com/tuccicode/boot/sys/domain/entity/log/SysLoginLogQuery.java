package com.tuccicode.boot.sys.domain.entity.log;

import com.tuccicode.boot.dto.PageQuery;
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
