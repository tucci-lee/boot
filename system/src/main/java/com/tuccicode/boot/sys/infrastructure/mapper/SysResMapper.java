package com.tuccicode.boot.sys.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysResDO;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysResMapper extends BaseMapper<SysResDO> {

    SysResDO selectById(Long id);

    SysResDO selectByName(String name);

    List<SysResDO> selectByUid(Long uid);

    List<SysResDO> selectList();

    List<SysResDO> selectByRoleId(Long roleId);
}