package com.tuccicode.boot.sys.application.dto.body;

import com.tuccicode.boot.dto.DTO;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author tucci.lee
 */
@Data
public class ChangePasswordBody extends DTO {

    @NotBlank
    @Length(min = 6, max = 32)
    private String oldPassword;

    @NotBlank
    @Length(min = 6, max = 32)
    private String password;
}
