package com.tuccicode.boot.sys.application.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.sys.application.assembler.LogOperateAssembler;
import com.tuccicode.boot.sys.application.dto.vo.SysOperateLogVO;
import com.tuccicode.boot.sys.domain.entity.log.SysOperateLog;
import com.tuccicode.boot.sys.domain.entity.log.SysOperateLogQuery;
import com.tuccicode.boot.sys.domain.service.SysOperateLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysOperateLogApplicationService {

    private final SysOperateLogService sysOperateLogService;

    public SysOperateLogApplicationService(SysOperateLogService sysOperateLogService) {
        this.sysOperateLogService = sysOperateLogService;
    }

    public Response list(SysOperateLogQuery query) {
        PageResponse<SysOperateLog> page = sysOperateLogService.list(query);
        List<SysOperateLogVO> sysOperateLogVOList = page.getData().stream()
                .map(LogOperateAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysOperateLogVOList, page.getTotal());
    }
}
