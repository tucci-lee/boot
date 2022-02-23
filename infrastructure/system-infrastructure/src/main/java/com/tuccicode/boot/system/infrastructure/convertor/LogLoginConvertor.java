package com.tuccicode.boot.system.infrastructure.convertor;

import com.tuccicode.boot.system.domain.entity.log.SysLoginLog;
import com.tuccicode.boot.system.infrastructure.dataobject.SysLoginLogDO;
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
        return new SysLoginLogDO()
                .setUsername(entity.getUsername())
                .setOs(entity.getOs())
                .setBrowser(entity.getBrowser())
                .setIp(entity.getIp())
                .setStatus(entity.getStatus())
                .setMessage(entity.getMessage());
    }
}
