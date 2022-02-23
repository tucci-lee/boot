package com.tuccicode.boot.system.infrastructure.convertor;

import com.tuccicode.boot.system.domain.entity.role.SysRole;
import com.tuccicode.boot.system.infrastructure.dataobject.SysRoleDO;
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

    public static SysRoleDO toAddDO(SysRole entity) {
        return new SysRoleDO()
                .setName(entity.getName())
                .setRoleChar(entity.getRoleChar())
                .setRemarks(entity.getRemarks());
    }

    public static SysRoleDO toEditDO(SysRole entity) {
        return new SysRoleDO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setRoleChar(entity.getRoleChar())
                .setRemarks(entity.getRemarks());
    }
}
