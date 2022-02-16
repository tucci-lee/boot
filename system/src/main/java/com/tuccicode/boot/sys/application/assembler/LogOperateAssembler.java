package com.tuccicode.boot.sys.application.assembler;

import com.tuccicode.boot.sys.application.dto.vo.LogOperateVO;
import com.tuccicode.boot.sys.domain.entity.log.LogOperate;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogOperateAssembler {

    public static LogOperateVO toVO(LogOperate entitiy) {
        if(entitiy == null){
            return null;
        }
        LogOperateVO vo = new LogOperateVO();
        BeanUtils.copyProperties(entitiy, vo);
        return vo;
    }
}
