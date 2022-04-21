package com.tuccicode.boot.domain.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.system.convertor.SysRoleConvertor;
import com.tuccicode.boot.domain.system.dataobject.SysRoleDO;
import com.tuccicode.boot.domain.system.entity.role.SysRole;
import com.tuccicode.boot.domain.system.entity.role.SysRoleQuery;
import com.tuccicode.boot.domain.system.mapper.SysRoleMapper;
import com.tuccicode.boot.domain.system.mapper.SysRoleResMapper;
import com.tuccicode.boot.domain.system.mapper.SysUserRoleMapper;
import com.tuccicode.boot.domain.system.service.SysRoleService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.exception.Assert;
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
        sysRoleMapper.selectPage(page, query);

        List<SysRole> sysRoleList = page.getRecords()
                .stream()
                .map(SysRoleConvertor::toEntity)
                .collect(Collectors.toList());
        return PageResponse.success(sysRoleList, (int) page.getTotal());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void add(SysRole sysRole) {
        List<Long> resIds = sysRole.getResIds();
        SysRoleDO addRole = SysRoleConvertor.toAddDO(sysRole);

        // 校验角色名称是否有相同的
        synchronized (this) {
            Assert.isNull(sysRoleMapper.selectByName(addRole.getName()), BootBizCode.ROLE_NAME_EXIST);
            sysRoleMapper.insert(addRole);
        }
        // 添加关联的资源
        sysRoleResMapper.insertList(addRole.getId(), resIds);
    }

    @Override
    public void edit(SysRole sysRole) {
        SysRoleDO editRole = SysRoleConvertor.toEditDO(sysRole);
        // 校验角色名称是否有相同的
        synchronized (this) {
            SysRoleDO queryRole = sysRoleMapper.selectByName(editRole.getName());
            Assert.isTrue(queryRole == null || queryRole.getId().equals(editRole.getId()), BootBizCode.ROLE_NAME_EXIST);
            sysRoleMapper.updateById(editRole);
        }
    }

    @Override
    public void delete(Long id) {
        int userCount = sysUserRoleMapper.countByRoleId(id);
        Assert.isTrue(userCount == 0, BootBizCode.ROLE_RELATED);

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
    public List<SysRole> listByUid(Long uid) {
        List<SysRoleDO> sysRoleDOList = sysRoleMapper.selectByUid(uid);
        return sysRoleDOList.stream()
                .map(SysRoleConvertor::toEntity)
                .collect(Collectors.toList());
    }
}
