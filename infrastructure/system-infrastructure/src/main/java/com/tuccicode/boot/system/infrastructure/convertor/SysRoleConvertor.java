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
        SysRoleDO dataobject = new SysRoleDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
    }

    public static SysRoleDO toEditDO(SysRole entity) {
        SysRoleDO dataobject = new SysRoleDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
    }
}
