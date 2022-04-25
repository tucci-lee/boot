package com.tuccicode.boot.app.system.service;

import com.tuccicode.boot.app.system.assembler.SysResAssembler;
import com.tuccicode.boot.app.system.dto.body.SysResCreateBody;
import com.tuccicode.boot.app.system.dto.body.SysResUpdateBody;
import com.tuccicode.boot.app.system.dto.vo.SysResVO;
import com.tuccicode.boot.domain.system.entity.res.SysRes;
import com.tuccicode.boot.domain.system.service.SysResService;
import com.tuccicode.boot.domain.system.util.AdminUtils;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysResAppService {

    private final SysResService sysResService;

    public SysResAppService(SysResService sysResService) {
        this.sysResService = sysResService;
    }

    /**
     * 根据uid查询拥有的菜单
     *
     * @param uid uid
     * @return List<SysResVO>
     */
    public Response listMenuByUid(Long uid) {
        List<SysRes> sysResList;
        // 如果是admin用户则返回所有菜单
        if (uid == AdminUtils.getUid()) {
            sysResList = sysResService.list()
                    .stream()
                    .filter(sysResDO -> sysResDO.getType().equals(1))
                    .collect(Collectors.toList());
        } else {
            sysResList = sysResService.listByUid(uid)
                    .stream()
                    .filter(sysRes -> sysRes.getType().equals(1))
                    .collect(Collectors.toList());
        }
        List<SysResVO> sysResVOList = sysResList.stream()
                .map(SysResAssembler::toVO)
                .collect(Collectors.toList());
        return SingletonResponse.success(sysResVOList);
    }

    /**
     * 查询所有的资源列表
     *
     * @return List<SysResVO>
     */
    public Response list() {
        List<SysRes> sysResList = sysResService.list();
        List<SysResVO> sysResVOList = sysResList.stream()
                .map(SysResAssembler::toVO)
                .collect(Collectors.toList());
        return SingletonResponse.success(sysResVOList);
    }

    /**
     * 添加资源
     *
     * @param body 资源信息
     * @return Response
     */
    public Response create(SysResCreateBody body) {
        SysRes res = new SysRes();
        BeanUtils.copyProperties(body, res);
        sysResService.create(res);
        return Response.success();
    }

    /**
     * 删除资源
     *
     * @param id 资源id
     * @return Response
     */
    public Response delete(Long id) {
        sysResService.delete(id);
        return Response.success();
    }

    /**
     * 修改资源
     *
     * @param body 资源信息
     * @return Response
     */
    public Response update(SysResUpdateBody body) {
        SysRes res = new SysRes();
        BeanUtils.copyProperties(body, res);
        sysResService.update(res);
        return Response.success();
    }

    /**
     * 根据角色id查询关联的资源id
     *
     * @param roleId 角色id
     * @return 资源id
     */
    public Response listIdByRoleId(Long roleId) {
        List<Long> resIds = sysResService.listByRoleId(roleId)
                .stream()
                .map(SysRes::getId)
                .collect(Collectors.toList());
        return SingletonResponse.success(resIds);
    }
}
