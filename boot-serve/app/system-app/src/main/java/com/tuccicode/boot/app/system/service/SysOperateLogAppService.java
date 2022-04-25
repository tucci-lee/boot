package com.tuccicode.boot.app.system.service;

import com.tuccicode.boot.app.system.assembler.LogOperateAssembler;
import com.tuccicode.boot.app.system.dto.vo.SysOperateLogVO;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLog;
import com.tuccicode.boot.domain.system.entity.log.SysOperateLogQuery;
import com.tuccicode.boot.domain.system.service.SysOperateLogService;
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

    private final SysOperateLogService sysOperateLogService;

    public SysOperateLogAppService(SysOperateLogService sysOperateLogService) {
        this.sysOperateLogService = sysOperateLogService;
    }

    public Response page(SysOperateLogQuery query) {
        PageResponse<SysOperateLog> page = sysOperateLogService.page(query);
        List<SysOperateLogVO> sysOperateLogVOList = page.getData().stream()
                .map(LogOperateAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysOperateLogVOList, page.getTotal());
    }
}
