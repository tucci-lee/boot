package com.tuccicode.boot.domain.system.convertor;

import com.tuccicode.boot.domain.system.dataobject.SysResDO;
import com.tuccicode.boot.domain.system.entity.res.SysRes;
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

    public static SysResDO toCreateDO(SysRes entity) {
        return new SysResDO()
                .setName(entity.getName())
                .setType(entity.getType())
                .setUrl(entity.getUrl())
                .setPid(entity.getPid())
                .setResChar(entity.getResChar())
                .setSeq(entity.getSeq())
                .setCreateTime(System.currentTimeMillis());
    }

    public static SysResDO toUpdateDO(SysRes entity) {
        return new SysResDO()
                .setId(entity.getId())
                .setName(entity.getName())
                .setUrl(entity.getUrl())
                .setPid(entity.getPid() == null ? 0L : entity.getPid())
                .setResChar(entity.getResChar())
                .setSeq(entity.getSeq())
                .setUpdatedTime(System.currentTimeMillis());
    }
}
