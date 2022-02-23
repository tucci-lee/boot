package com.tuccicode.boot.system.infrastructure.convertor;

import com.tuccicode.boot.system.domain.entity.res.SysRes;
import com.tuccicode.boot.system.infrastructure.dataobject.SysResDO;
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
        return new SysResDO()
                .setName(entity.getName())
                .setType(entity.getType())
                .setUrl(entity.getUrl())
                .setPid(entity.getPid())
                .setResChar(entity.getResChar())
                .setSeq(entity.getSeq());
    }

    public static SysResDO toEditDO(SysRes entity) {
        return new SysResDO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setUrl(entity.getUrl())
                .setPid(entity.getPid() == null ? 0L : entity.getPid())
                .setResChar(entity.getResChar())
                .setSeq(entity.getSeq());
    }
}