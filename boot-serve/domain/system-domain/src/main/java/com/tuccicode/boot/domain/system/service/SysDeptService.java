package com.tuccicode.boot.domain.system.service;

import com.tuccicode.boot.domain.system.entity.dept.SysDept;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysDeptService {

    /**
     * 查询所有的部门列表
     *
     * @return List<SysDept>
     */
    List<SysDept> list();

    /**
     * 保存部门
     *
     * @param dept 部门信息
     */
    void create(SysDept dept);

    /**
     * 根据id删除部门
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 修改部门
     *
     * @param dept 部门信息
     */
    void update(SysDept dept);
}
