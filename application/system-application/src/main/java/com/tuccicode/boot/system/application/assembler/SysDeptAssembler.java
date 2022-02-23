package com.tuccicode.boot.system.application.assembler;

import com.tuccicode.boot.system.application.dto.vo.SysDeptVO;
import com.tuccicode.boot.system.domain.entity.dept.SysDept;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysDeptAssembler {

    public static SysDeptVO toVO(SysDept entity) {
        if(entity == null){
            return null;
        }
        SysDeptVO vo = new SysDeptVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
