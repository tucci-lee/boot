package com.tuccicode.boot.system.application.dto.body;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author tucci.lee
 */
@Data
public class SysRoleAddBody extends DTO {

    @NotBlank
    @Length(max = 20)
    private String name;

    @Size(max = 50)
    private String roleChar;

    @Size(max = 200)
    private String remarks;

    @NotEmpty
    private List<Long> resIds;
}
