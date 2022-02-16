package com.tuccicode.boot.sys.domain.entity.log;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LogOperate extends DTO {
    private Long id;

    private String username;

    private String ip;

    private String url;

    private String method;

    private String params;

    private String result;

    private String description;

    private String errorMessage;

    private Long createTime;

    private Boolean status;

}