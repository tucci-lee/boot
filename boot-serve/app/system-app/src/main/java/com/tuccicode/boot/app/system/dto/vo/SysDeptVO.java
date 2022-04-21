package com.tuccicode.boot.app.system.dto.vo;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysDeptVO extends DTO {

    private Long id;

    private String name;

    private Long pid;

    private Integer seq;

    private String manager;

    private String managerPhone;

    private Long createTime;

    private Long updatedTime;
}
