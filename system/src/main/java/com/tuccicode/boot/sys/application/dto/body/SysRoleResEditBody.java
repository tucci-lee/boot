package com.tuccicode.boot.sys.application.dto.body;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author tucci.lee
 */
@Data
public class SysRoleResEditBody extends DTO {

    @NotNull
    private Long id;

    @NotEmpty
    private List<Long> resIds;
}
