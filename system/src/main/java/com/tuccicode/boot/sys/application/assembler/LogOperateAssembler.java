package com.tuccicode.boot.sys.application.assembler;

import com.tuccicode.boot.sys.application.dto.vo.SysOperateLogVO;
import com.tuccicode.boot.sys.domain.entity.log.SysOperateLog;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogOperateAssembler {

    public static SysOperateLogVO toVO(SysOperateLog entitiy) {
        if(entitiy == null){
            return null;
        }
        SysOperateLogVO vo = new SysOperateLogVO();
        BeanUtils.copyProperties(entitiy, vo);
        return vo;
    }
}
