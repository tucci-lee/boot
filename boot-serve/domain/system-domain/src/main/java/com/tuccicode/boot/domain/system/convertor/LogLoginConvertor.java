package com.tuccicode.boot.domain.system.convertor;

import com.tuccicode.boot.domain.system.dataobject.SysLoginLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLog;
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

    public static SysLoginLogDO toCreateDO(SysLoginLog entity) {
        return new SysLoginLogDO()
                .setUsername(entity.getUsername())
                .setOs(entity.getOs())
                .setBrowser(entity.getBrowser())
                .setIp(entity.getIp())
                .setStatus(entity.getStatus())
                .setMessage(entity.getMessage());
    }
}
