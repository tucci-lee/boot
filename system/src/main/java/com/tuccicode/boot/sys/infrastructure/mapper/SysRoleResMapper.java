package com.tuccicode.boot.sys.infrastructure.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysRoleResMapper {

    int countByResId(Long resId);

    int insertList(@Param("roleId") Long roleId, @Param("resIds") List<Long> resIds);

    int deleteByRoleId(Long roleId);
}
