package com.tuccicode.boot.app.task.dto.query;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class TaskLogListQuery extends PageQuery {

    @NotNull
    private Long taskId;
}
