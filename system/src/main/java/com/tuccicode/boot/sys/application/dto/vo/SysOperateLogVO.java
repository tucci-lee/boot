package com.tuccicode.boot.sys.application.dto.vo;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysOperateLogVO extends DTO {

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
