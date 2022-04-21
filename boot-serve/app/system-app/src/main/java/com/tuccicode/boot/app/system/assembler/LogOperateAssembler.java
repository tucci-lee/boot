package com.tuccicode.boot.app.system.assembler;

import com.tuccicode.boot.app.system.dto.vo.SysOperateLogVO;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLog;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogOperateAssembler {

    public static SysOperateLogVO toVO(SysOperateLog entity) {
        if(entity == null){
            return null;
        }
        SysOperateLogVO vo = new SysOperateLogVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
