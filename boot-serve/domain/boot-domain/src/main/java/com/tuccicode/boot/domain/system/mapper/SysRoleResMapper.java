package com.tuccicode.boot.domain.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author tucci.lee
 */
@Mapper
public interface SysRoleResMapper {

    int countByResId(Long resId);

    int insertList(@Param("roleId") Long roleId, @Param("resIds") List<Long> resIds);

    int deleteByRoleId(Long roleId);
}
