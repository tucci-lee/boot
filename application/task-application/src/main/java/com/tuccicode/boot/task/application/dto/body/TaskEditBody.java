package com.tuccicode.boot.task.application.dto.body;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author tucci.lee
 */
@Data
public class TaskEditBody extends DTO {

    @NotNull
    private Long id;

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
