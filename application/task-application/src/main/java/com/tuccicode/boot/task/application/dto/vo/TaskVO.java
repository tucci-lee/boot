package com.tuccicode.boot.task.application.dto.vo;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class TaskVO extends DTO {

    private Long id;

    private String name;

    private String className;

    private String cron;

    private Boolean status;

    private String remarks;

    private Long createTime;

    private Long updatedTime;
}
