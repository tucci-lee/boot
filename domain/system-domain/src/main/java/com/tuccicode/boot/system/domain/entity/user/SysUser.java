package com.tuccicode.boot.system.domain.entity.user;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class SysUser extends DTO {

    private Long uid;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Boolean isLock;

    private String nickname;

    private String remarks;

    private Long deptId;

    private Long createTime;

    private Long updatedTime;

    private String deptName;

    private List<Long> roleIds;

    private Integer loginVersion;

    private String oldPassword;
}
