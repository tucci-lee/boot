package com.tuccicode.boot.app.system.assembler;

import com.tuccicode.boot.app.system.dto.vo.SysLoginLogVO;
import com.tuccicode.boot.domain.system.dataobject.SysLoginLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLog;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogLoginAssembler {

    public static SysLoginLogVO toVO(SysLoginLogDO dataObject) {
        if(dataObject == null){
            return null;
        }
        SysLoginLogVO vo = new SysLoginLogVO();
        BeanUtils.copyProperties(dataObject, vo);
        return vo;
    }
}
