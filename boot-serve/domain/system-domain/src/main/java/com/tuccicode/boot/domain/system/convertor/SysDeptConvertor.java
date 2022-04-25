package com.tuccicode.boot.domain.system.convertor;

import com.tuccicode.boot.domain.system.dataobject.SysDeptDO;
import com.tuccicode.boot.domain.system.entity.dept.SysDept;
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

    public static SysDeptDO toCreateDO(SysDept entity) {
        return new SysDeptDO()
                .setName(entity.getName())
                .setPid(entity.getPid())
                .setSeq(entity.getSeq())
                .setManager(entity.getManager())
                .setManagerPhone(entity.getManagerPhone());
    }

    public static SysDeptDO toUpdateDO(SysDept entity) {
        return new SysDeptDO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setId(entity.getPid() == null ? 0L : entity.getPid())
                .setSeq(entity.getSeq())
                .setManager(entity.getManager())
                .setManagerPhone(entity.getManagerPhone());
    }
}
