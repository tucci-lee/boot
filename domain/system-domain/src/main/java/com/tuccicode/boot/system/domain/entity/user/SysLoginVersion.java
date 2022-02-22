package com.tuccicode.boot.system.domain.entity.user;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysLoginVersion extends DTO {
    private Long uid;

    private Integer version;

    private Long createTime;

    private Long updatedTime;

}
