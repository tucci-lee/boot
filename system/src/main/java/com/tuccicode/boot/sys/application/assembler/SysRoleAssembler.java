package com.tuccicode.boot.sys.application.assembler;

import com.tuccicode.boot.sys.application.dto.vo.SysRoleVO;
import com.tuccicode.boot.sys.domain.entity.role.SysRole;
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
