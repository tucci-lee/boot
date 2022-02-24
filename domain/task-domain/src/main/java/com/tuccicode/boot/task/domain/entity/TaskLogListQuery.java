package com.tuccicode.boot.task.domain.entity;

import com.tuccicode.boot.dto.PageQuery;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class TaskLogListQuery extends PageQuery {

    @NotNull
    private Long taskId;
}
