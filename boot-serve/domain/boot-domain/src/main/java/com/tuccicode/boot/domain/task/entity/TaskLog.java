package com.tuccicode.boot.domain.task.entity;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class TaskLog extends DTO {

    private Long id;

    private Long taskId;

    private Boolean status;

    private String message;

    private Long startTime;

    private Long runTime;

    private Long createTime;
}
