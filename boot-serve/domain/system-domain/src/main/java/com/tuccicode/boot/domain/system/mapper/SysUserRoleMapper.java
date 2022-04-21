package com.tuccicode.boot.domain.system.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysUserRoleMapper {

    int insertList(@Param("uid") Long uid, @Param("roleIds") List<Long> roleIds);

    int countByRoleId(Long roleId);

    void deleteByUid(Long uid);
}
