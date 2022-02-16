package com.tuccicode.boot.sys.domain.entity.log;

import com.tuccicode.boot.dto.PageQuery;
import lombok.Data;

@Data
public class LogOperateQuery extends PageQuery {

    private String username;

    private String ip;

    private Boolean status;

    private Long beginTime;

    private Long endTime;
}
