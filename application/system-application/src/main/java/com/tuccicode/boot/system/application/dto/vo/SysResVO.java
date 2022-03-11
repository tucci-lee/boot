package com.tuccicode.boot.system.application.dto.vo;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysResVO extends DTO {

    private Long id;

    private String name;

    private Integer type;

    private String url;

    private Long pid;

    private String resChar;

    private Integer seq;

    private Long createTime;

    private Long updatedTime;
}
