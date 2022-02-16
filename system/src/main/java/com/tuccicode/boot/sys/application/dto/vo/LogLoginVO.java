package com.tuccicode.boot.sys.application.dto.vo;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class LogLoginVO extends DTO {


    private Long id;

    private String username;

    private String os;

    private String browser;

    private String ip;

    private Boolean status;

    private String message;

    private Long createTime;
}
