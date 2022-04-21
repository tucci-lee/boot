package com.tuccicode.boot.app.system.assembler;

import com.tuccicode.boot.app.system.dto.vo.SysUserVO;
import com.tuccicode.boot.domain.system.entity.user.SysUser;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysUserAssembler {

    public static SysUserVO toVO(SysUser entity) {
        if(entity == null){
            return null;
        }
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

}
