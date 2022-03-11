package com.tuccicode.boot.system.application.dto.body;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author tucci.lee
 */
@Data
public class SysResAddBody extends DTO {
    private Long pid;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotNull
    @Range(min = 1, max = 2)
    private Integer type;

    @Size(max = 100)
    private String url;

    @Size(max = 50)
    private String resChar;

    @Range(max = 99)
    private Integer seq;

}
