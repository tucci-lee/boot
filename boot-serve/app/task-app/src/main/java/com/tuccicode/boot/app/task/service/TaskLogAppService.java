package com.tuccicode.boot.app.task.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.app.task.assembler.TaskLogAssembler;
import com.tuccicode.boot.app.task.dto.vo.TaskLogVO;
import com.tuccicode.boot.domain.task.dataobject.TaskDO;
import com.tuccicode.boot.domain.task.dataobject.TaskLogDO;
import com.tuccicode.boot.domain.task.entity.TaskLog;
import com.tuccicode.boot.domain.task.entity.TaskLogQuery;
import com.tuccicode.boot.domain.task.mapper.TaskLogMapper;
import com.tuccicode.boot.domain.task.service.TaskLogService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class TaskLogAppService {

    private final TaskLogMapper taskLogMapper;

    public TaskLogAppService(TaskLogMapper taskLogMapper) {
        this.taskLogMapper = taskLogMapper;
    }

    public Response list(TaskLogQuery query) {
        Page<TaskLogDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        taskLogMapper.selectPage(page, query);
        List<TaskLogVO> taskLogVOList = page.getRecords().stream()
                .map(TaskLogAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(taskLogVOList, (int) page.getTotal());
    }
}
