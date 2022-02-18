package com.tuccicode.boot.sys.infrastructure.convertor;

import com.tuccicode.boot.sys.domain.entity.log.SysLoginLog;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysLoginLogDO;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogLoginConvertor {

    public static SysLoginLog toEntity(SysLoginLogDO dataobject) {
        if (dataobject == null) {
            return null;
        }
        SysLoginLog entity = new SysLoginLog();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static SysLoginLogDO toAddDO(SysLoginLog entity) {
        SysLoginLogDO dataobject = new SysLoginLogDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
    }
}
