package com.tuccicode.boot.task.domain.entity;

import com.tuccicode.boot.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class TaskListQuery extends PageQuery {

    private String name;

    private Boolean status;
}
