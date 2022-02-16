package com.tuccicode.boot.sys.domain.service;

import com.tuccicode.boot.sys.domain.entity.dept.SysDept;

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
    void add(SysDept dept);

    /**
     * 根据id删除部门
     *
     * @param id id
     */
    void delete(long id);

    /**
     * 修改部门
     *
     * @param dept 部门信息
     */
    void edit(SysDept dept);
}
