package com.tuccicode.boot.sys.infrastructure.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.sys.domain.entity.user.SysUser;
import com.tuccicode.boot.sys.domain.entity.user.SysUserQuery;
import com.tuccicode.boot.sys.domain.service.SysLoginVersionService;
import com.tuccicode.boot.sys.domain.service.SysUserService;
import com.tuccicode.boot.sys.infrastructure.constant.CacheConst;
import com.tuccicode.boot.sys.infrastructure.convertor.SysUserConvertor;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysDeptDO;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysUserDO;
import com.tuccicode.boot.sys.infrastructure.exception.SysBizCode;
import com.tuccicode.boot.sys.infrastructure.mapper.SysDeptMapper;
import com.tuccicode.boot.sys.infrastructure.mapper.SysUserMapper;
import com.tuccicode.boot.sys.infrastructure.mapper.SysUserRoleMapper;
import com.tuccicode.boot.util.Assert;
import org.springframework.cache.annotation.CacheEvict;
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
    public SysUser getByUsername(String username) {
        SysUserDO sysUserDO = sysUserMapper.selectByUsername(username.toLowerCase());
        return SysUserConvertor.toEntity(sysUserDO, null);
    }

    @Override
    public PageResponse<SysUser> list(SysUserQuery query) {
        Page<SysUserDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysUserMapper.selectList(page, query);

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
        SysUserDO sysUserDO = SysUserConvertor.toAddDO(sysUser);
        synchronized (this) {
            SysUser queryUser = this.getByUsername(sysUserDO.getUsername());
            Assert.isNull(queryUser, SysBizCode.USER_EXIST);
            sysUserMapper.insert(sysUserDO);
        }
        // 初始化登录版本号
        sysLoginVersionService.save(sysUser.getUid());
        // 添加关联的角色信息
        sysUserRoleMapper.insertList(sysUserDO.getUid(), sysUser.getRoleIds());
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
            SysUserDO sysUserDO = sysUserMapper.selectByUid(sysUser.getUid());
            Assert.isTrue(BCrypt.checkpw(sysUser.getOldPassword(), sysUserDO.getPassword()), SysBizCode.PASSWORD_ERROR);
        }
        SysUserDO sysUserDO = SysUserConvertor.toEditPasswordDO(sysUser);
        sysUserMapper.updateById(sysUserDO);
        sysLoginVersionService.save(sysUser.getUid());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void editLock(SysUser sysUser) {
        SysUserDO sysUserDO = SysUserConvertor.toEditLockDO(sysUser);
        sysUserMapper.updateById(sysUserDO);
        sysLoginVersionService.save(sysUser.getUid());
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void delete(long uid) {
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
