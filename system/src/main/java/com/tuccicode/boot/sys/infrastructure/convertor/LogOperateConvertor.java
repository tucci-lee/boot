package com.tuccicode.boot.sys.infrastructure.convertor;

import com.tuccicode.boot.sys.domain.entity.log.SysOperateLog;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysOperateLogDO;
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
