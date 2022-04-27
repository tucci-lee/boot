package com.tuccicode.boot.app.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.app.system.assembler.LogOperateAssembler;
import com.tuccicode.boot.app.system.dto.vo.SysOperateLogVO;
import com.tuccicode.boot.domain.system.dataobject.SysOperateLogDO;
import com.tuccicode.boot.domain.system.dataobject.SysUserDO;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLog;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLogQuery;
import com.tuccicode.boot.domain.system.mapper.SysOperateLogMapper;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysOperateLogAppService {

    private final SysOperateLogMapper sysOperateLogMapper;

    public SysOperateLogAppService(SysOperateLogMapper sysOperateLogMapper) {
        this.sysOperateLogMapper = sysOperateLogMapper;
    }

    public Response page(SysOperateLogQuery query) {
        Page<SysOperateLogDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysOperateLogMapper.selectPage(page, query);
        List<SysOperateLogVO> sysOperateLogVOList = page.getRecords().stream()
                .map(LogOperateAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysOperateLogVOList, (int) page.getTotal());
    }
}
