package com.tuccicode.boot.system.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.system.domain.entity.user.SysUserQuery;
import com.tuccicode.boot.system.infrastructure.dataobject.SysUserDO;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    int countByDeptId(Long deptId);

    Page<SysUserDO> selectPage(Page<?> page, @Param("q") SysUserQuery query);

    SysUserDO selectByUsername(String username);

    SysUserDO selectByUid(Long uid);
}
