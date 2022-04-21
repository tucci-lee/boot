package com.tuccicode.boot.app.system.assembler;

import com.tuccicode.boot.app.system.dto.vo.SysRoleVO;
import com.tuccicode.boot.domain.system.entity.role.SysRole;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysRoleAssembler {

    public static SysRoleVO toVO(SysRole entity) {
        if(entity == null){
            return null;
        }
        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
