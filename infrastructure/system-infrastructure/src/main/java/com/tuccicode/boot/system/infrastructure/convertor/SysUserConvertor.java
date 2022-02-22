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
        SysUserDO dataobject = new SysUserDO();
        BeanUtils.copyProperties(entity, dataobject);
        dataobject.setUsername(entity.getUsername().toLowerCase());
        dataobject.setPassword(BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt()));
        return dataobject;
    }

    public static SysUserDO toEditDO(SysUser entity) {
        SysUserDO dataobject = new SysUserDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
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
