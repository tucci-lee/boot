package com.tuccicode.boot.system.application.dto.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysResVO {

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
