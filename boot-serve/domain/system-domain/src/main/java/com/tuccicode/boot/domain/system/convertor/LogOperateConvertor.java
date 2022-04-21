package com.tuccicode.boot.domain.system.convertor;

import com.tuccicode.boot.domain.system.dataobject.SysOperateLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLog;
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
        return new SysOperateLogDO()
                .setUsername(entity.getUsername())
                .setIp(entity.getIp())
                .setUrl(entity.getUrl())
                .setMethod(entity.getMethod())
                .setParams(entity.getParams())
                .setResult(entity.getResult())
                .setDescription(entity.getDescription())
                .setErrorMessage(entity.getErrorMessage())
                .setStatus(entity.getStatus());
    }
}
