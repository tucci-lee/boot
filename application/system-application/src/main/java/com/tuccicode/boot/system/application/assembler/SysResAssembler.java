package com.tuccicode.boot.system.application.assembler;

import com.tuccicode.boot.system.application.dto.vo.SysResVO;
import com.tuccicode.boot.system.domain.entity.res.SysRes;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysResAssembler {

    public static SysResVO toVO(SysRes entity) {
        if(entity == null){
            return null;
        }
        SysResVO vo = new SysResVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
