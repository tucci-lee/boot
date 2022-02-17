package com.tuccicode.boot.sys.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.role.SysRole;
import com.tuccicode.boot.sys.domain.entity.role.SysRoleQuery;
import com.tuccicode.boot.sys.domain.service.SysRoleService;
import com.tuccicode.boot.sys.infrastructure.constant.CacheConst;
import com.tuccicode.boot.sys.infrastructure.convertor.SysRoleConvertor;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysRoleDO;
import com.tuccicode.boot.sys.infrastructure.exception.SysBizCode;
import com.tuccicode.boot.sys.infrastructure.mapper.SysRoleMapper;
import com.tuccicode.boot.sys.infrastructure.mapper.SysRoleResMapper;
import com.tuccicode.boot.sys.infrastructure.mapper.SysUserRoleMapper;
import com.tuccicode.boot.util.Assert;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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

    @Override
    public PageResponse<SysRole> list(SysRoleQuery query) {
        Page<SysRoleDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysRoleMapper.selectList(page, query);

        List<SysRole> sysRoleList = page.getRecords()
                .stream()
                .map(SysRoleConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(sysRoleList, (int) page.getTotal());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void add(SysRole sysRole) {
        SysRoleDO addRole = SysRoleConvertor.toAddDO(sysRole);

        // 校验角色名称是否有相同的
        synchronized (this) {
            Assert.isNull(sysRoleMapper.selectByName(addRole.getName()), SysBizCode.ROLE_NAME_EXIST);
            sysRoleMapper.insert(addRole);
        }
        // 添加关联的资源
        sysRoleResMapper.insertList(addRole.getId(), sysRole.getResIds());
    }

    @Override
    public void edit(SysRole sysRole) {
        SysRoleDO editRole = SysRoleConvertor.toEditDO(sysRole);
        // 校验角色名称是否有相同的
        synchronized (this) {
            SysRoleDO queryRole = sysRoleMapper.selectByName(sysRole.getName());
            Assert.isTrue(queryRole == null || queryRole.getId().equals(editRole.getId()), SysBizCode.ROLE_NAME_EXIST);
            sysRoleMapper.updateById(editRole);
        }
    }

    @Override
    public void delete(long id) {
        int userCount = sysUserRoleMapper.countByRoleId(id);
        Assert.isTrue(userCount == 0, SysBizCode.ROLE_RELATED);

        SysRoleDO editRole = new SysRoleDO()
                .setId(id)
                .setIsDeleted(true);
        sysRoleMapper.updateById(editRole);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void editRes(SysRole sysRole) {
        synchronized (this) {
            // 删除之前绑定的资源
            sysRoleResMapper.deleteByRoleId(sysRole.getId());

            // 添加新绑定的资源
            sysRoleResMapper.insertList(sysRole.getId(), sysRole.getResIds());
        }
    }

    @Override
    public List<SysRole> listByUid(long uid) {
        List<SysRoleDO> sysRoleDOList = sysRoleMapper.selectByUid(uid);
        return sysRoleDOList.stream()
                .map(SysRoleConvertor::toEntity)
                .collect(Collectors.toList());
    }
}
