package com.tuccicode.boot.app.system.service;

import com.tuccicode.boot.app.system.dto.vo.ImageCaptchaVO;
import com.tuccicode.boot.domain.system.entity.captcha.CaptchaType;
import com.tuccicode.boot.domain.system.service.ImageCaptchaService;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.dto.SingletonResponse;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author tucci.lee
 */
@Service
public class CaptchaAppService {

    private final ImageCaptchaService imageCaptchaService;

    public CaptchaAppService(ImageCaptchaService imageCaptchaService) {
        this.imageCaptchaService = imageCaptchaService;
    }

    /**
     * 生成图片验证码
     *
     * @param key 验证码的key
     * @return ImageCaptchaVO
     */
    public Response generate(String key) {
        BufferedImage image = imageCaptchaService.generate(CaptchaType.LOGIN, key);
        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            ImageIO.write(image, "jpeg", os);
            ImageCaptchaVO imageCaptchaVO = new ImageCaptchaVO()
                    .setCaptcha(os.toByteArray());
            return SingletonResponse.success(imageCaptchaVO);
        } catch (IOException e) {
            return Response.success();
        }
    }
}
