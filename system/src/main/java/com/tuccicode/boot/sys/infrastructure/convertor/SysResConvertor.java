package com.tuccicode.boot.sys.infrastructure.convertor;

import com.tuccicode.boot.sys.domain.entity.res.SysRes;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysResDO;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysResConvertor {

    public static SysRes toEntity(SysResDO dataobject) {
        if (dataobject == null) {
            return null;
        }
        SysRes entity = new SysRes();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static SysResDO toAddDO(SysRes entity) {
        SysResDO dataobject = new SysResDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
    }

    public static SysResDO toEditDO(SysRes entity) {
        SysResDO dataobject = new SysResDO();
        BeanUtils.copyProperties(entity, dataobject);
        if (entity.getPid() == null) {
            dataobject.setPid(0L);
        }
        return dataobject;
    }
}
