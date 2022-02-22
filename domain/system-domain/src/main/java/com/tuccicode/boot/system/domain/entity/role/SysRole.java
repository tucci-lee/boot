package com.tuccicode.boot.system.domain.entity.role;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysRole extends DTO {

    private Long id;

    private String roleChar;

    private String name;

    private String remarks;

    private Long createTime;

    private Long updatedTime;

    private List<Long> resIds;
}
