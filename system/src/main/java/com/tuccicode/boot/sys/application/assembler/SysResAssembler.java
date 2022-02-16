package com.tuccicode.boot.sys.application.assembler;

import com.tuccicode.boot.sys.application.dto.vo.SysResVO;
import com.tuccicode.boot.sys.domain.entity.res.SysRes;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysResAssembler {

    public static SysResVO toVO(SysRes entitiy) {
        if(entitiy == null){
            return null;
        }
        SysResVO vo = new SysResVO();
        BeanUtils.copyProperties(entitiy, vo);
        return vo;
    }
}
