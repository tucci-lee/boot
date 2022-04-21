package com.tuccicode.boot.app.system.dto.vo;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysLoginLogVO extends DTO {


    private Long id;

    private String username;

    private String os;

    private String browser;

    private String ip;

    private Boolean status;

    private String message;

    private Long createTime;
}
