package com.tuccicode.boot.app.system.assembler;

import com.tuccicode.boot.app.system.dto.vo.SysLoginLogVO;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLog;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogLoginAssembler {

    public static SysLoginLogVO toVO(SysLoginLog entity) {
        if(entity == null){
            return null;
        }
        SysLoginLogVO vo = new SysLoginLogVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
