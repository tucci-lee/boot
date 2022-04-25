package com.tuccicode.boot.app.task.dto.body;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author tucci.lee
 */
@Data
public class TaskCreateBody extends DTO {

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String className;

    @NotBlank
    @Size(max = 100)
    private String cron;

    @Size(max = 200)
    private String remarks;
}
