package com.tuccicode.boot.system.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuccicode.boot.system.infrastructure.dataobject.SysDeptDO;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysDeptMapper extends BaseMapper<SysDeptDO> {

    SysDeptDO selectById(Long id);

    SysDeptDO selectByName(String name);

    List<SysDeptDO> selectList();

    int countByPid(Long pid);
}
