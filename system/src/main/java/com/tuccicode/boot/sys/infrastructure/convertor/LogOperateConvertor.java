package com.tuccicode.boot.sys.infrastructure.convertor;

import com.tuccicode.boot.sys.domain.entity.log.LogOperate;
import com.tuccicode.boot.sys.infrastructure.dataobject.LogOperateDO;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogOperateConvertor {

    public static LogOperate toEntity(LogOperateDO dataobject) {
        if (dataobject == null) {
            return null;
        }
        LogOperate entity = new LogOperate();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static LogOperateDO toAddDO(LogOperate entity) {
        LogOperateDO dataobject = new LogOperateDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
    }
}
