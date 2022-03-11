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
public class SysResEditBody extends DTO {

    @NotNull
    private Long id;

    private Long pid;

    @NotBlank
    @Size(max = 20)
    private String name;

    @Size(max = 100)
    private String url;

    @Size(max = 50)
    private String resChar;

    @Range(max = 999)
    private Integer seq;
}
