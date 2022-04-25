package com.tuccicode.boot.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.system.dataobject.SysUserDO;
import com.tuccicode.boot.domain.system.entity.user.SysUserQuery;
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
