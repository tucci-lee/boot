package com.tuccicode.boot.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.domain.system.dataobject.SysResDO;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysResMapper extends BaseMapper<SysResDO> {

    SysResDO selectById(Long id);

    SysResDO selectByName(String name);

    List<SysResDO> selectByUid(Long uid);

    List<SysResDO> selectAll();

    List<SysResDO> selectByRoleId(Long roleId);
}