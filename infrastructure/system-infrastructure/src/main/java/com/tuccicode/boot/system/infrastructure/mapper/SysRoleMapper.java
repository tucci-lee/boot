package com.tuccicode.boot.system.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.system.domain.entity.role.SysRoleQuery;
import com.tuccicode.boot.system.infrastructure.dataobject.SysRoleDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    SysRoleDO selectByName(String name);

    Page<SysRoleDO> selectList(Page<?> page, @Param("q") SysRoleQuery query);

    List<SysRoleDO> selectByUid(Long uid);
}
