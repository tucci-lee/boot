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
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysLoginVersionService sysLoginVersionService;

    public SysUserServiceImpl(SysUserMapper sysUserMapper,
                              SysUserRoleMapper sysUserRoleMapper,
                              SysLoginVersionService sysLoginVersionService) {
        this.sysUserMapper = sysUserMapper;
        this.sysUserRoleMapper = sysUserRoleMapper;
        this.sysLoginVersionService = sysLoginVersionService;
    }

    @Override
    public SysUser getByUsername(String username) {
        SysUserDO sysUserDO = sysUserMapper.selectByUsername(username.toLowerCase());
        return SysUserConvertor.toEntity(sysUserDO);
    }

    @Override
    public SysUser getByUid(Long uid) {
        SysUserDO sysUserDO = sysUserMapper.selectByUid(uid);
        return SysUserConvertor.toEntity(sysUserDO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void create(SysUser sysUser) {
        List<Long> roleIds = sysUser.getRoleIds();
        SysUserDO sysUserDO = SysUserConvertor.toCreateDO(sysUser);
        synchronized (this) {
            SysUser queryUser = this.getByUsername(sysUserDO.getUsername());
            Assert.isNull(queryUser, BootBizCode.ACCOUNT_EXIST);
            sysUserMapper.insert(sysUserDO);
        }
        // 初始化登录版本号
        sysLoginVersionService.save(sysUserDO.getUid());
        // 添加关联的角色信息
        sysUserRoleMapper.insertList(sysUserDO.getUid(), roleIds);
    }

    @Override
    public void update(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConvertor.toUpdateDO(sysUser);
        sysUserMapper.updateById(sysUserDO);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updatePassword(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConvertor.toUpdatePasswordDO(sysUser);
        sysUserMapper.updateById(sysUserDO);
        sysLoginVersionService.save(sysUserDO.getUid());
    }

    @Override
    public boolean verifyPassword(String plaintext, String ciphertext) {
        try {
            return BCrypt.checkpw(plaintext, ciphertext);
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void updateLock(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConvertor.toUpdateLockDO(sysUser);
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
    public void updateRole(SysUser sysUser) {
        synchronized (this) {
            // 删除之前绑定的角色
            sysUserRoleMapper.deleteByUid(sysUser.getUid());

            // 添加新绑定的角色
            sysUserRoleMapper.insertList(sysUser.getUid(), sysUser.getRoleIds());
        }
    }
}
