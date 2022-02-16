package com.tuccicode.boot.sys.infrastructure.service.impl;

import com.tuccicode.boot.sys.domain.entity.res.SysRes;
import com.tuccicode.boot.sys.domain.service.SysResService;
import com.tuccicode.boot.sys.infrastructure.constant.CacheConst;
import com.tuccicode.boot.sys.infrastructure.convertor.SysResConvertor;
import com.tuccicode.boot.sys.infrastructure.dataobject.SysResDO;
import com.tuccicode.boot.sys.infrastructure.exception.SysBizCode;
import com.tuccicode.boot.sys.infrastructure.mapper.SysResMapper;
import com.tuccicode.boot.sys.infrastructure.mapper.SysRoleResMapper;
import com.tuccicode.boot.util.Assert;
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
    public List<SysRes> listByUid(long uid) {
        List<SysResDO> sysResDOList = sysResMapper.selectByUid(uid);
        return sysResDOList.stream()
                .map(SysResConvertor::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<SysRes> list() {
        List<SysResDO> sysResDOList = sysResMapper.selectList();
        return sysResDOList.stream()
                .map(SysResConvertor::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void add(SysRes res) {
        this.verifyParent(res.getPid());

        SysResDO addRes = SysResConvertor.toAddDO(res);

        // 校验资源名称是否有相同的
        synchronized (this) {
            Assert.isNull(sysResMapper.selectByName(addRes.getName()), SysBizCode.RES_NAME_EXIST);
            sysResMapper.insert(addRes);
        }
    }

    @Override
    public void delete(long id) {
        int roleCount = sysRoleResMapper.countByResId(id);
        Assert.isTrue(roleCount == 0, SysBizCode.RES_RELATED);

        SysResDO editRes = new SysResDO()
                .setId(id)
                .setIsDeleted(true);
        sysResMapper.updateById(editRes);
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
    public void edit(SysRes res) {
        Assert.isTrue(!res.getId().equals(res.getPid()), SysBizCode.LEVEL_ERROR);

        this.verifyParent(res.getPid());

        SysResDO editRes = SysResConvertor.toEditDO(res);
        synchronized (this) {
            SysResDO queryRes = sysResMapper.selectByName(editRes.getName());
            Assert.isTrue(queryRes == null || queryRes.getId().equals(editRes.getId()), SysBizCode.RES_NAME_EXIST);

            sysResMapper.updateById(editRes);
        }
    }

    @Override
    public List<SysRes> listByRoleId(long roleId) {
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
        Assert.notNull(parentRes, SysBizCode.PARENT_NOT_EXIST);
    }
}
