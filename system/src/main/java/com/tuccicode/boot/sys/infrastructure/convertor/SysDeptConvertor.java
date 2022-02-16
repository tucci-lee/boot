package com.tuccicode.boot.sys.infrastructure.convertor;

import com.tuccicode.boot.sys.domain.entity.dept.SysDept;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysDeptDO;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysDeptConvertor {

    public static SysDept toEntity(SysDeptDO dataobject) {
        if (dataobject == null) {
            return null;
        }
        SysDept entity = new SysDept();
        BeanUtils.copyProperties(dataobject, entity);
        return entity;
    }

    public static SysDeptDO toAddDO(SysDept entity) {
        SysDeptDO dataobject = new SysDeptDO();
        BeanUtils.copyProperties(entity, dataobject);
        return dataobject;
    }

    public static SysDeptDO toEditDO(SysDept entity) {
        SysDeptDO dataobject = new SysDeptDO();
        BeanUtils.copyProperties(entity, dataobject);
        if (dataobject.getPid() == null) {
            dataobject.setPid(0L);
        }
        return dataobject;
    }
}
