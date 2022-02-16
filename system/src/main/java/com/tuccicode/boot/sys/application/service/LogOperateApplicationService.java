package com.tuccicode.boot.sys.application.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.sys.application.assembler.LogOperateAssembler;
import com.tuccicode.boot.sys.application.dto.vo.LogOperateVO;
import com.tuccicode.boot.sys.domain.entity.log.LogOperate;
import com.tuccicode.boot.sys.domain.entity.log.LogOperateQuery;
import com.tuccicode.boot.sys.domain.service.LogOperateService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class LogOperateApplicationService {

    private final LogOperateService logOperateService;

    public LogOperateApplicationService(LogOperateService logOperateService) {
        this.logOperateService = logOperateService;
    }

    public Response list(LogOperateQuery query) {
        PageResponse<LogOperate> page = logOperateService.list(query);
        List<LogOperateVO> logOperateVOList = page.getData().stream()
                .map(LogOperateAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(logOperateVOList, page.getTotal());
    }
}
