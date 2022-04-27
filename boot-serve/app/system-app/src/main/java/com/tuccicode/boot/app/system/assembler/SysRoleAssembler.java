package com.tuccicode.boot.app.system.assembler;

import com.tuccicode.boot.app.system.dto.vo.SysRoleVO;
import com.tuccicode.boot.domain.system.dataobject.SysRoleDO;
import com.tuccicode.boot.domain.system.entity.role.SysRole;
import org.springframework.beans.BeanUtils;

/**
 * @author tucci.lee
 */
public class SysRoleAssembler {

    public static SysRoleVO toVO(SysRoleDO dataObject) {
        if(dataObject == null){
            return null;
        }
        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copyProperties(dataObject, vo);
        return vo;
    }
}
