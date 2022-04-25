package com.tuccicode.boot.app.task.dto.body;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author tucci.lee
 */
@Data
public class TaskUpdateStatusBody extends DTO {

    @NotNull
    private Long id;

    @NotNull
    private Boolean status;
}
