package com.tuccicode.boot.domain.system.convertor;

import com.tuccicode.boot.domain.system.dataobject.SysRoleDO;
import com.tuccicode.boot.domain.system.entity.role.SysRole;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysRoleConvertor {

    public static SysRole toEntity(SysRoleDO dataobject) {
        if (dataobject == null) {
            return null;
        }
        SysRole entity = new SysRole();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static SysRoleDO toCreateDO(SysRole entity) {
        return new SysRoleDO()
                .setName(entity.getName())
                .setRoleChar(entity.getRoleChar())
                .setRemarks(entity.getRemarks());
    }

    public static SysRoleDO toUpdateDO(SysRole entity) {
        return new SysRoleDO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setRoleChar(entity.getRoleChar())
                .setRemarks(entity.getRemarks());
    }
}
