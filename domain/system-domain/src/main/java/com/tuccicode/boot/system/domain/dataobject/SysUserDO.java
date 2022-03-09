package com.tuccicode.boot.system.domain.dataobject;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
@TableName("sys_user")
public class SysUserDO implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long uid;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Boolean isLock;

    private String nickname;

    private String remarks;

    private Long deptId;

    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Long updatedTime;

    private Boolean isDeleted;

    private static final long serialVersionUID = 1L;
}