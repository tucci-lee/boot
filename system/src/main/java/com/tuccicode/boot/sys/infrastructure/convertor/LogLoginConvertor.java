package com.tuccicode.boot.sys.infrastructure.convertor;

import com.tuccicode.boot.sys.domain.entity.log.LogLogin;
import com.tuccicode.boot.sys.infrastructure.dataobject.LogLoginDO;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogLoginConvertor {

    public static LogLogin toEntity(LogLoginDO dataobject) {
        if (dataobject == null) {
            return null;
        }
        LogLogin entity = new LogLogin();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static LogLoginDO toAddDO(LogLogin entity) {
        LogLoginDO dataobject = new LogLoginDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
    }
}
