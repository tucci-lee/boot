package com.tuccicode.boot.app.system.dto.body;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author tucci.lee
 */
@Data
public class SysDeptAddBody extends DTO {

    private Long pid;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 20)
    private String manager;

    @NotBlank
    @Pattern(regexp = "^[1][3-9][0-9]{9}$")
    private String managerPhone;

    @Range(max = 99)
    private Integer seq;
}
