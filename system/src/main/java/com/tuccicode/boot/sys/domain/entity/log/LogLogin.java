package com.tuccicode.boot.sys.domain.entity.log;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class LogLogin extends DTO {

    private Long id;

    private String username;

    private String os;

    private String browser;

    private String ip;

    private Boolean status;

    private String message;

    private Long createTime;
}

