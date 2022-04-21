package com.tuccicode.boot.domain.system.service;

import com.tuccicode.boot.domain.system.entity.res.SysRes;

import java.util.List;

/**
 * @author tucci.lee
 */
public interface SysResService {

    /**
     * 根据uid查询拥有的资源列表
     *
     * @param uid uid
     * @return List<SysRes>
     */
    List<SysRes> listByUid(Long uid);

    /**
     * 查询所有的资源列表
     *
     * @return List<SysRes>
     */
    List<SysRes> list();

    /**
     * 保存资源
     *
     * @param res 资源信息
     */
    void add(SysRes res);

    /**
     * 根据id删除资源
     *
     * @param id id
     */
    void delete(Long id);

    /**
     * 修改资源
     *
     * @param res 资源信息
     */
    void edit(SysRes res);

    /**
     * 根据角色id查询关联的资源
     *
     * @param roleId 角色id
     * @return SysRes
     */
    List<SysRes> listByRoleId(Long roleId);
}
