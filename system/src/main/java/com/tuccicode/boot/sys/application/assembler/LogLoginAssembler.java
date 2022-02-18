package com.tuccicode.boot.sys.application.assembler;

import com.tuccicode.boot.sys.application.dto.vo.SysLoginLogVO;
import com.tuccicode.boot.sys.domain.entity.log.SysLoginLog;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogLoginAssembler {

    public static SysLoginLogVO toVO(SysLoginLog entitiy) {
        if(entitiy == null){
            return null;
        }
        SysLoginLogVO vo = new SysLoginLogVO();
        BeanUtils.copyProperties(entitiy, vo);
        return vo;
    }
}
