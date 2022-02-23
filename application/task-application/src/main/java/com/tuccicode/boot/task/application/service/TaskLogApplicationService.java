package com.tuccicode.boot.task.application.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.task.application.assembler.TaskLogAssembler;
import com.tuccicode.boot.task.application.dto.vo.TaskLogVO;
import com.tuccicode.boot.task.domain.entity.TaskLog;
import com.tuccicode.boot.task.domain.entity.TaskLogListQuery;
import com.tuccicode.boot.task.domain.service.TaskLogService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class TaskLogApplicationService {

    private final TaskLogService taskLogService;

    public TaskLogApplicationService(TaskLogService taskLogService) {
        this.taskLogService = taskLogService;
    }

    public Response list(TaskLogListQuery query) {
        PageResponse<TaskLog> page = taskLogService.list(query);
        List<TaskLogVO> taskLogVOList = page.getData()
                .stream()
                .map(TaskLogAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(taskLogVOList, page.getTotal());
    }
}
