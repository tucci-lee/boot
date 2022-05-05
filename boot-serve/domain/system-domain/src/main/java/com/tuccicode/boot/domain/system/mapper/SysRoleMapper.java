package com.tuccicode.boot.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.system.dataobject.SysRoleDO;
import com.tuccicode.boot.domain.system.entity.role.SysRoleQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysRoleMapper extends BaseMapper<SysRoleDO> {

    SysRoleDO selectByName(String name);

    Page<SysRoleDO> selectAll(Page<?> page, @Param("q") SysRoleQuery query);

    List<SysRoleDO> selectByUid(Long uid);
}
