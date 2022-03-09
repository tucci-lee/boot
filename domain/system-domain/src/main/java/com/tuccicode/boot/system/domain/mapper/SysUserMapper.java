package com.tuccicode.boot.system.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.system.domain.dataobject.SysUserDO;
import com.tuccicode.boot.system.domain.entity.user.SysUserQuery;
import org.apache.ibatis.annotations.Param;

/**
 * @author tucci.lee
 */
public interface SysUserMapper extends BaseMapper<SysUserDO> {

    int countByDeptId(Long deptId);

    Page<SysUserDO> selectPage(Page<?> page, @Param("q") SysUserQuery query);

    SysUserDO selectAllByUsername(String username);

    String selectPasswordByUid(Long uid);
}
