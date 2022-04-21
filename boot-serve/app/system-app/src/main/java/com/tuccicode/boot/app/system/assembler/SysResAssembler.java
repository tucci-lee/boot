package com.tuccicode.boot.app.system.assembler;

import com.tuccicode.boot.app.system.dto.vo.SysResVO;
import com.tuccicode.boot.domain.system.entity.res.SysRes;
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
