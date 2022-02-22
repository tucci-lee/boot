package com.tuccicode.boot.system.infrastructure.convertor;

import com.tuccicode.boot.system.domain.entity.log.SysOperateLog;
import com.tuccicode.boot.system.infrastructure.dataobject.SysOperateLogDO;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogOperateConvertor {

    public static SysOperateLog toEntity(SysOperateLogDO dataobject) {
        if (dataobject == null) {
            return null;
        }
        SysOperateLog entity = new SysOperateLog();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static SysOperateLogDO toAddDO(SysOperateLog entity) {
        SysOperateLogDO dataobject = new SysOperateLogDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
    }
}
