package com.tuccicode.boot.app.task.dto.query;

import com.tuccicode.raccoon.dto.PageQuery;
import lombok.Data;

/**
 * @author tucci.lee
 */
@Data
public class TaskListQuery extends PageQuery {

    private String name;

    private Boolean status;
}
