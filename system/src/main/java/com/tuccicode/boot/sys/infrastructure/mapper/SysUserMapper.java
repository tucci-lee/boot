package com.tuccicode.boot.sys.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.sys.domain.entity.user.SysUserQuery;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysUserDO;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper extends BaseMapper<SysUserDO> {

    int countByDeptId(Long deptId);

    Page<SysUserDO> selectList(Page<?> page, @Param("q") SysUserQuery query);

    SysUserDO selectByUsername(String username);

    SysUserDO selectByUid(Long uid);
}
