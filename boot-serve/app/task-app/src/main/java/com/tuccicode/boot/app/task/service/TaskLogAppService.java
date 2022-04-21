package com.tuccicode.boot.app.task.service;

import com.tuccicode.boot.app.task.assembler.TaskLogAssembler;
import com.tuccicode.boot.app.task.dto.query.TaskLogListQuery;
import com.tuccicode.boot.app.task.dto.vo.TaskLogVO;
import com.tuccicode.boot.domain.task.entity.TaskLog;
import com.tuccicode.boot.domain.task.entity.TaskLogQuery;
import com.tuccicode.boot.domain.task.service.TaskLogService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class TaskLogAppService {

    private final TaskLogService taskLogService;

    public TaskLogAppService(TaskLogService taskLogService) {
        this.taskLogService = taskLogService;
    }

    public Response list(TaskLogListQuery query) {
        TaskLogQuery logQuery = new TaskLogQuery();
        BeanUtils.copyProperties(query, logQuery);
        PageResponse<TaskLog> page = taskLogService.list(logQuery);
        List<TaskLogVO> taskLogVOList = page.getData()
                .stream()
                .map(TaskLogAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(taskLogVOList, page.getTotal());
    }
}
