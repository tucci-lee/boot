package com.tuccicode.boot.domain.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.domain.system.dataobject.SysDeptDO;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysDeptMapper extends BaseMapper<SysDeptDO> {

    SysDeptDO selectById(Long id);

    SysDeptDO selectByName(String name);

    List<SysDeptDO> selectAll();

    int countByPid(Long pid);
}
