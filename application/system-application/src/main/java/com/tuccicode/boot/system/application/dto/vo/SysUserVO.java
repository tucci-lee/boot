package com.tuccicode.boot.system.application.dto.vo;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysUserVO extends DTO {

    private Long uid;

    private String username;

    private String phone;

    private String email;

    private Boolean isLock;

    private String nickname;

    private String remarks;

    private Long deptId;

    private Long createTime;

    private Long updatedTime;

    private String deptName;
}
