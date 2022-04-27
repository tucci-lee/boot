package com.tuccicode.boot.domain.system.convertor;

import com.tuccicode.boot.domain.system.dataobject.SysUserDO;
import com.tuccicode.boot.domain.system.entity.user.SysUser;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author tucci.lee
 */
public class SysUserConvertor {

    public static SysUser toEntity(SysUserDO entity) {
        if (entity == null) {
            return null;
        }
        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(entity, sysUser);
        return sysUser;
    }

    public static SysUserDO toCreateDO(SysUser entity) {
        return new SysUserDO()
                .setUsername(entity.getUsername().toLowerCase())
                .setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt()))
                .setPhone(entity.getPhone())
                .setEmail(entity.getEmail())
                .setNickname(entity.getNickname())
                .setRemarks(entity.getRemarks())
                .setDeptId(entity.getDeptId())
                .setCreateTime(System.currentTimeMillis());
    }

    public static SysUserDO toUpdateDO(SysUser entity) {
        return new SysUserDO()
                .setUid(entity.getUid())
                .setPhone(entity.getPhone())
                .setEmail(entity.getEmail())
                .setNickname(entity.getNickname())
                .setRemarks(entity.getRemarks())
                .setDeptId(entity.getDeptId())
                .setUpdatedTime(System.currentTimeMillis());
    }

    public static SysUserDO toUpdatePasswordDO(SysUser entity) {
        return new SysUserDO()
                .setUid(entity.getUid())
                .setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt()))
                .setUpdatedTime(System.currentTimeMillis());
    }

    public static SysUserDO toUpdateLockDO(SysUser entity) {
        return new SysUserDO()
                .setUid(entity.getUid())
                .setIsLock(entity.getIsLock())
                .setUpdatedTime(System.currentTimeMillis());
    }
}
