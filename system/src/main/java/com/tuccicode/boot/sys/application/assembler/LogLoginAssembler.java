package com.tuccicode.boot.sys.application.assembler;

import com.tuccicode.boot.sys.application.dto.vo.LogLoginVO;
import com.tuccicode.boot.sys.domain.entity.log.LogLogin;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class LogLoginAssembler {

    public static LogLoginVO toVO(LogLogin entitiy) {
        if(entitiy == null){
            return null;
        }
        LogLoginVO vo = new LogLoginVO();
        BeanUtils.copyProperties(entitiy, vo);
        return vo;
    }
}
