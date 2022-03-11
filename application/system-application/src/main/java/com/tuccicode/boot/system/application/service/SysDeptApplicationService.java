package com.tuccicode.boot.system.application.service;

import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import com.tuccicode.boot.system.application.assembler.SysDeptAssembler;
import com.tuccicode.boot.system.application.dto.body.SysDeptAddBody;
import com.tuccicode.boot.system.application.dto.body.SysDeptEditBody;
import com.tuccicode.boot.system.application.dto.vo.SysDeptVO;
import com.tuccicode.boot.system.domain.entity.dept.SysDept;
import com.tuccicode.boot.system.domain.service.SysDeptService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysDeptApplicationService {

    private final SysDeptService sysDeptService;

    public SysDeptApplicationService(SysDeptService sysDeptService) {
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
    public Response add(SysDeptAddBody body) {
        SysDept dept = new SysDept();
        BeanUtils.copyProperties(body, dept);
        sysDeptService.add(dept);
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
    public Response edit(SysDeptEditBody body) {
        SysDept dept = new SysDept();
        BeanUtils.copyProperties(body, dept);
        sysDeptService.edit(dept);
        return Response.success();
    }
}
