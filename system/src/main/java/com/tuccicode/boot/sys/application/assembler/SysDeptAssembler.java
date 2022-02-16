package com.tuccicode.boot.sys.application.assembler;

import com.tuccicode.boot.sys.application.dto.vo.SysDeptVO;
import com.tuccicode.boot.sys.domain.entity.dept.SysDept;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysDeptAssembler {

    public static SysDeptVO toVO(SysDept entitiy) {
        if(entitiy == null){
            return null;
        }
        SysDeptVO vo = new SysDeptVO();
        BeanUtils.copyProperties(entitiy, vo);
        return vo;
    }
}
