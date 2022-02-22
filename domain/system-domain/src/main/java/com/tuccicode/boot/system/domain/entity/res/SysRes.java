package com.tuccicode.boot.system.domain.entity.res;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysRes extends DTO {
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