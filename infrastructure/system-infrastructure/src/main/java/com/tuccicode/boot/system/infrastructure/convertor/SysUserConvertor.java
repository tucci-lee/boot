package com.tuccicode.boot.system.infrastructure.convertor;

import com.tuccicode.boot.system.domain.entity.user.SysUser;
import com.tuccicode.boot.system.infrastructure.dataobject.SysUserDO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author tucci.lee
 */
public class SysUserConvertor {

    public static SysUser toEntity(SysUserDO entity, String deptName) {
        if (entity == null) {
            return null;
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(entity, sysUser);
        sysUser.setDeptName(deptName);
        return sysUser;
    }

    public static SysUserDO toAddDO(SysUser entity) {
        return new SysUserDO()
                .setUsername(entity.getUsername().toLowerCase())
                .setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt()))
                .setPhone(entity.getPhone())
                .setEmail(entity.getEmail())
                .setNickname(entity.getNickname())
                .setRemarks(entity.getRemarks())
                .setDeptId(entity.getDeptId());
    }

    public static SysUserDO toEditDO(SysUser entity) {
        return new SysUserDO()
                .setUid(entity.getUid())
                .setPhone(entity.getPhone())
                .setEmail(entity.getEmail())
                .setNickname(entity.getNickname())
                .setRemarks(entity.getRemarks())
                .setDeptId(entity.getDeptId());
    }

    public static SysUserDO toEditPasswordDO(SysUser entity) {
        return new SysUserDO()
                .setUid(entity.getUid())
                .setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt()));
    }

    public static SysUserDO toEditLockDO(SysUser entity) {
        return new SysUserDO()
                .setUid(entity.getUid())
                .setIsLock(entity.getIsLock());
    }
}
