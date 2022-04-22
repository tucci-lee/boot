package com.tuccicode.boot.domain.task.entity;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class TaskLogQuery extends PageQuery {

    @NotNull
    private Long taskId;
}
