package com.tuccicode.boot.app.system.service;

import com.tuccicode.boot.app.system.assembler.SysDeptAssembler;
import com.tuccicode.boot.app.system.dto.body.SysDeptCreateBody;
import com.tuccicode.boot.app.system.dto.body.SysDeptUpdateBody;
import com.tuccicode.boot.app.system.dto.vo.SysDeptVO;
import com.tuccicode.boot.domain.system.entity.dept.SysDept;
import com.tuccicode.boot.domain.system.service.SysDeptService;
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
public class SysDeptAppService {

    private final SysDeptService sysDeptService;

    public SysDeptAppService(SysDeptService sysDeptService) {
        this.sysDeptService = sysDeptService;
    }
    

    /**
     * 查询所有的部门列表
     *
     * @return List<SysDeptVO>
     */
    public Response list() {
        List<SysDept> sysDeptList = sysDeptService.list();
        List<SysDeptVO> sysDeptVOList = sysDeptList.stream()
                .map(SysDeptAssembler::toVO)
                .collect(Collectors.toList());
        return SingletonResponse.success(sysDeptVOList);
    }

    /**
     * 添加部门
     *
     * @param body 部门信息
     * @return Response
     */
    public Response create(SysDeptCreateBody body) {
        SysDept dept = new SysDept();
        BeanUtils.copyProperties(body, dept);
        sysDeptService.create(dept);
        return Response.success();
    }

    /**
     * 删除部门
     *
     * @param id 部门id
     * @return Response
     */
    public Response delete(Long id) {
        sysDeptService.delete(id);
        return Response.success();
    }

    /**
     * 修改部门
     *
     * @param body 部门信息
     * @return Response
     */
    public Response update(SysDeptUpdateBody body) {
        SysDept dept = new SysDept();
        BeanUtils.copyProperties(body, dept);
        sysDeptService.update(dept);
        return Response.success();
    }
}
