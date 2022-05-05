package com.tuccicode.boot.domain.system.service.impl;

import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.system.constant.CacheConst;
import com.tuccicode.boot.domain.system.convertor.SysResConvertor;
import com.tuccicode.boot.domain.system.dataobject.SysResDO;
import com.tuccicode.boot.domain.system.entity.res.SysRes;
import com.tuccicode.boot.domain.system.mapper.SysResMapper;
import com.tuccicode.boot.domain.system.mapper.SysRoleResMapper;
import com.tuccicode.boot.domain.system.service.SysResService;
import com.tuccicode.raccoon.exception.Assert;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysResServiceImpl implements SysResService {

    private final SysResMapper sysResMapper;
    private final SysRoleResMapper sysRoleResMapper;

    public SysResServiceImpl(SysResMapper sysResMapper,
                             SysRoleResMapper sysRoleResMapper) {
        this.sysResMapper = sysResMapper;
        this.sysRoleResMapper = sysRoleResMapper;
    }

    @Cacheable(value = CacheConst.USER_RES, key = "#p0")
    @Override
    public List<SysRes> listByUid(Long uid) {
        List<SysResDO> sysResDOList = sysResMapper.selectByUid(uid);
        return sysResDOList.stream()
                .map(SysResConvertor::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysRes> list() {
        List<SysResDO> sysResDOList = sysResMapper.selectAll();
        return sysResDOList.stream()
                .map(SysResConvertor::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void create(SysRes res) {
        this.verifyParent(res.getPid());

        SysResDO createRes = SysResConvertor.toCreateDO(res);

        // 校验资源名称是否有相同的
        synchronized (this) {
            Assert.isNull(sysResMapper.selectByName(createRes.getName()), BootBizCode.RES_NAME_EXIST);
            sysResMapper.insert(createRes);
        }
    }

    @Override
    public void delete(Long id) {
        int roleCount = sysRoleResMapper.countByResId(id);
        Assert.isTrue(roleCount == 0, BootBizCode.RES_RELATED);

        SysResDO updateRes = new SysResDO()
                .setId(id)
                .setIsDeleted(true);
        sysResMapper.updateById(updateRes);
    }

    /**
     * 校验上级id是否是自己的id
     * 校验上级是否存在
     * 校验名称是否重复
     * 修改资源
     *
     * @param res 资源信息
     */
    @CacheEvict(value = CacheConst.USER_RES, allEntries = true)
    @Override
    public void update(SysRes res) {
        Assert.isTrue(!res.getId().equals(res.getPid()), BootBizCode.LEVEL_ERROR);

        this.verifyParent(res.getPid());

        SysResDO updateRes = SysResConvertor.toUpdateDO(res);
        synchronized (this) {
            SysResDO queryRes = sysResMapper.selectByName(updateRes.getName());
            Assert.isTrue(queryRes == null || queryRes.getId().equals(updateRes.getId()), BootBizCode.RES_NAME_EXIST);

            sysResMapper.updateById(updateRes);
        }
    }

    @Override
    public List<SysRes> listByRoleId(Long roleId) {
        List<SysResDO> sysResDOList = sysResMapper.selectByRoleId(roleId);
        return sysResDOList.stream()
                .map(SysResConvertor::toEntity)
                .collect(Collectors.toList());
    }

    /**
     * 校验上级是否存在
     *
     * @param pid 上级id
     */
    private void verifyParent(Long pid) {
        if (pid == null || pid == 0) {
            return;
        }
        SysResDO parentRes = sysResMapper.selectById(pid);
        Assert.notNull(parentRes, BootBizCode.PARENT_NOT_EXIST);
    }
}
