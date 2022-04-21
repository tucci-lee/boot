package com.tuccicode.boot.domain.task.entity;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class TaskLogListQuery extends PageQuery {

    private Long taskId;
}
