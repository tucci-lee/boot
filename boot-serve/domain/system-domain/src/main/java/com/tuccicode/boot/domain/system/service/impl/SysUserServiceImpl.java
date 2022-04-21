package com.tuccicode.boot.domain.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.system.convertor.SysUserConvertor;
import com.tuccicode.boot.domain.system.dataobject.SysDeptDO;
import com.tuccicode.boot.domain.system.dataobject.SysUserDO;
import com.tuccicode.boot.domain.system.entity.user.SysUser;
import com.tuccicode.boot.domain.system.entity.user.SysUserQuery;
import com.tuccicode.boot.domain.system.mapper.SysDeptMapper;
import com.tuccicode.boot.domain.system.mapper.SysUserMapper;
import com.tuccicode.boot.domain.system.mapper.SysUserRoleMapper;
import com.tuccicode.boot.domain.system.service.SysLoginVersionService;
import com.tuccicode.boot.domain.system.service.SysUserService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.exception.Assert;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tucci.lee
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysDeptMapper sysDeptMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysLoginVersionService sysLoginVersionService;

    public SysUserServiceImpl(SysUserMapper sysUserMapper,
                              SysDeptMapper sysDeptMapper,
                              SysUserRoleMapper sysUserRoleMapper,
                              SysLoginVersionService sysLoginVersionService) {
        this.sysUserMapper = sysUserMapper;
        this.sysDeptMapper = sysDeptMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysLoginVersionService = sysLoginVersionService;
    }

    @Override
    public SysUser getAllByUsername(String username) {
        SysUserDO sysUserDO = sysUserMapper.selectAllByUsername(username.toLowerCase());
        return SysUserConvertor.toEntity(sysUserDO, null);
    }

    @Override
    public PageResponse<SysUser> list(SysUserQuery query) {
        Page<SysUserDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysUserMapper.selectPage(page, query);

        List<SysUser> entities = new ArrayList<>();
        page.getRecords().forEach(sysUserDO -> {
            SysDeptDO sysDeptDO = new SysDeptDO();
            if (sysUserDO.getDeptId() != null) {
                sysDeptDO = sysDeptMapper.selectById(sysUserDO.getDeptId());
            }
            SysUser sysUser = SysUserConvertor.toEntity(sysUserDO, sysDeptDO.getName());
            entities.add(sysUser);
        });

        return PageResponse.success(entities, (int) page.getTotal());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void add(SysUser sysUser) {
        List<Long> roleIds = sysUser.getRoleIds();
        SysUserDO sysUserDO = SysUserConvertor.toAddDO(sysUser);
        synchronized (this) {
            SysUser queryUser = this.getAllByUsername(sysUserDO.getUsername());
            Assert.isNull(queryUser, BootBizCode.ACCOUNT_EXIST);
            sysUserMapper.insert(sysUserDO);
        }
        // 初始化登录版本号
        sysLoginVersionService.save(sysUserDO.getUid());
        // 添加关联的角色信息
        sysUserRoleMapper.insertList(sysUserDO.getUid(), roleIds);
    }

    @Override
    public void edit(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConvertor.toEditDO(sysUser);
        sysUserMapper.updateById(sysUserDO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void editPassword(SysUser sysUser) {
        // 如果是自己修改密码
        if (sysUser.getOldPassword() != null) {
            String password = sysUserMapper.selectPasswordByUid(sysUser.getUid());
            Assert.isTrue(BCrypt.checkpw(sysUser.getOldPassword(), password), BootBizCode.PASSWORD_ERROR);
        }
        SysUserDO sysUserDO = SysUserConvertor.toEditPasswordDO(sysUser);
        sysUserMapper.updateById(sysUserDO);
        sysLoginVersionService.save(sysUserDO.getUid());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void editLock(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConvertor.toEditLockDO(sysUser);
        sysUserMapper.updateById(sysUserDO);
        sysLoginVersionService.save(sysUserDO.getUid());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void delete(Long uid) {
        SysUserDO sysUserDO = new SysUserDO()
                .setUid(uid)
                .setIsDeleted(true);
        sysUserMapper.updateById(sysUserDO);
        sysLoginVersionService.save(uid);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void editRole(SysUser sysUser) {
        synchronized (this) {
            // 删除之前绑定的角色
            sysUserRoleMapper.deleteByUid(sysUser.getUid());

            // 添加新绑定的角色
            sysUserRoleMapper.insertList(sysUser.getUid(), sysUser.getRoleIds());
        }
    }
}
