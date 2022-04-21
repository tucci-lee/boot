package com.tuccicode.boot.domain.system.service.impl;

import com.tuccicode.boot.domain.exception.BootBizCode;
import com.tuccicode.boot.domain.system.convertor.SysDeptConvertor;
import com.tuccicode.boot.domain.system.dataobject.SysDeptDO;
import com.tuccicode.boot.domain.system.entity.dept.SysDept;
import com.tuccicode.boot.domain.system.mapper.SysDeptMapper;
import com.tuccicode.boot.domain.system.mapper.SysUserMapper;
import com.tuccicode.boot.domain.system.service.SysDeptService;
import com.tuccicode.raccoon.exception.Assert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysDeptServiceImpl implements SysDeptService {

    private final SysDeptMapper sysDeptMapper;
    private final SysUserMapper sysUserMapper;

    public SysDeptServiceImpl(SysDeptMapper sysDeptMapper,
                              SysUserMapper sysUserMapper) {
        this.sysDeptMapper = sysDeptMapper;
        this.sysUserMapper = sysUserMapper;
    }


    @Override
    public List<SysDept> list() {
        List<SysDeptDO> sysDeptDOList = sysDeptMapper.selectList();
        return sysDeptDOList.stream()
                .map(SysDeptConvertor::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void add(SysDept dept) {
        this.verifyParent(dept.getPid());

        SysDeptDO addDept = SysDeptConvertor.toAddDO(dept);

        // 校验资源名称是否有相同的
        synchronized (this) {
            Assert.isNull(sysDeptMapper.selectByName(addDept.getName()), BootBizCode.DEPT_NAME_EXIST);
            sysDeptMapper.insert(addDept);
        }
    }

    /**
     * 判断是否有用户关联
     * 判断是否有下级
     *
     * @param id id
     */
    @Override
    public void delete(Long id) {
        int userCount = sysUserMapper.countByDeptId(id);
        Assert.isTrue(userCount == 0, BootBizCode.DEPT_RELATED);

        int subDeptCount = sysDeptMapper.countByPid(id);
        Assert.isTrue(subDeptCount == 0, BootBizCode.DEPT_HAS_SUB);

        SysDeptDO editRes = new SysDeptDO()
                .setId(id)
                .setIsDeleted(true);
        sysDeptMapper.updateById(editRes);
    }

    /**
     * 校验上级id是否是自己的id
     * 校验上级是否存在
     * 校验名称是否重复
     * 修改资源
     *
     * @param dept 资源信息
     */
    @Override
    public void edit(SysDept dept) {
        Assert.isTrue(!dept.getId().equals(dept.getPid()), BootBizCode.LEVEL_ERROR);

        this.verifyParent(dept.getPid());

        SysDeptDO editDept = SysDeptConvertor.toEditDO(dept);
        synchronized (this) {
            SysDeptDO queryRes = sysDeptMapper.selectByName(editDept.getName());
            Assert.isTrue(queryRes == null || queryRes.getId().equals(editDept.getId()), BootBizCode.DEPT_NAME_EXIST);

            sysDeptMapper.updateById(editDept);
        }
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
        SysDeptDO parentDept = sysDeptMapper.selectById(pid);
        Assert.notNull(parentDept, BootBizCode.PARENT_NOT_EXIST);
    }
}
