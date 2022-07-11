package com.tuccicode.boot.domain.system.service.impl;

import com.tuccicode.boot.domain.core.exception.BootBizCode;
import com.tuccicode.boot.domain.system.constant.CacheConst;
import com.tuccicode.boot.domain.system.convertor.SysRoleConvertor;
import com.tuccicode.boot.domain.system.dataobject.SysRoleDO;
import com.tuccicode.boot.domain.system.entity.role.SysRole;
import com.tuccicode.boot.domain.system.mapper.SysRoleMapper;
import com.tuccicode.boot.domain.system.mapper.SysRoleResMapper;
import com.tuccicode.boot.domain.system.mapper.SysUserRoleMapper;
import com.tuccicode.boot.domain.system.service.SysRoleService;
import com.tuccicode.raccoon.exception.Assert;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleResMapper sysRoleResMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    public SysRoleServiceImpl(SysRoleMapper sysRoleMapper,
                              SysRoleResMapper sysRoleResMapper,
                              SysUserRoleMapper sysUserRoleMapper) {
        this.sysRoleMapper = sysRoleMapper;
        this.sysRoleResMapper = sysRoleResMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void create(SysRole sysRole) {
        List<Long> resIds = sysRole.getResIds();
        SysRoleDO createRole = SysRoleConvertor.toCreateDO(sysRole);

        // 校验角色名称是否有相同的
        synchronized (this) {
            Assert.isNull(sysRoleMapper.selectByName(createRole.getName()), BootBizCode.ROLE_NAME_EXIST);
            sysRoleMapper.insert(createRole);
        }
        // 添加关联的资源
        sysRoleResMapper.insertList(createRole.getId(), resIds);
    }

    @Override
    public void update(SysRole sysRole) {
        SysRoleDO updateRole = SysRoleConvertor.toUpdateDO(sysRole);
        // 校验角色名称是否有相同的
        synchronized (this) {
            SysRoleDO queryRole = sysRoleMapper.selectByName(updateRole.getName());
            Assert.isTrue(queryRole == null || queryRole.getId().equals(updateRole.getId()), BootBizCode.ROLE_NAME_EXIST);
            sysRoleMapper.updateById(updateRole);
        }
    }

    @Override
    public void delete(Long id) {
        int userCount = sysUserRoleMapper.countByRoleId(id);
        Assert.isTrue(userCount == 0, BootBizCode.ROLE_RELATED);

        SysRoleDO updateRole = new SysRoleDO()
                .setId(id)
                .setIsDeleted(true);
        sysRoleMapper.updateById(updateRole);
    }

    @CacheEvict(value = CacheConst.USER_RES, allEntries = true)
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updateRes(SysRole sysRole) {
        synchronized (this) {
            // 删除之前绑定的资源
            sysRoleResMapper.deleteByRoleId(sysRole.getId());

            // 添加新绑定的资源
            sysRoleResMapper.insertList(sysRole.getId(), sysRole.getResIds());
        }
    }

    @Override
    public List<SysRole> listByUid(Long uid) {
        List<SysRoleDO> sysRoleDOList = sysRoleMapper.selectByUid(uid);
        return sysRoleDOList.stream()
                .map(SysRoleConvertor::toEntity)
                .collect(Collectors.toList());
    }
}
