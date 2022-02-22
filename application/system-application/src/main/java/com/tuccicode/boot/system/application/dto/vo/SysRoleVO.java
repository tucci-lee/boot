package com.tuccicode.boot.system.application.dto.vo;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysRoleVO extends DTO {

    private Long id;

    private String roleChar;

    private String name;

    private String remarks;

    private Long createTime;

    private Long updatedTime;
}
