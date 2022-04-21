package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.aspect.Operate;
import com.tuccicode.boot.app.system.dto.body.SysDeptAddBody;
import com.tuccicode.boot.app.system.dto.body.SysDeptEditBody;
import com.tuccicode.boot.app.system.service.SysDeptAppService;
import com.tuccicode.raccoon.dto.Response;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("sys/dept")
public class SysDeptController {

    private final SysDeptAppService sysDeptAppService;

    public SysDeptController(SysDeptAppService sysDeptAppService) {
        this.sysDeptAppService = sysDeptAppService;
    }

    /**
     * 查询所有的部门
     *
     * @return List
     */
    @RequiresPermissions(value = {"sys:dept:list", "sys:user:list"}, logical = Logical.OR)
    @GetMapping
    public Response list() {
        return sysDeptAppService.list();
    }

    /**
     * 添加部门
     *
     * @param body 部门信息
     * @return Response
     */
    @Operate("添加部门")
    @RequiresPermissions(value = {"sys:dept:add"})
    @PostMapping
    public Response add(@Validated @RequestBody SysDeptAddBody body) {
        return sysDeptAppService.add(body);
    }

    /**
     * 删除部门
     *
     * @return Response
     */
    @Operate("删除部门")
    @RequiresPermissions(value = {"sys:dept:delete"})
    @DeleteMapping("{id}")
    public Response delete(@PathVariable Long id) {
        return sysDeptAppService.delete(id);
    }

    /**
     * 修改部门
     *
     * @param body 部门信息
     * @return Response
     */
    @Operate("修改部门")
    @RequiresPermissions(value = {"sys:dept:edit"})
    @PutMapping
    public Response edit(@Validated @RequestBody SysDeptEditBody body) {
        return sysDeptAppService.edit(body);
    }

}
