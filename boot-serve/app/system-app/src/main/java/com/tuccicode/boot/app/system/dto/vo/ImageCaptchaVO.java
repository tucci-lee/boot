package com.tuccicode.boot.app.system.dto.vo;

import com.tuccicode.raccoon.dto.DTO;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author tucci.lee
 */
@Data
@Accessors(chain = true)
public class ImageCaptchaVO extends DTO {

    /**
     * http传输会自动进行base64编码
     */
    private byte[] captcha;
}
