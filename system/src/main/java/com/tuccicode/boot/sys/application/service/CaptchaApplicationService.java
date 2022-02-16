package com.tuccicode.boot.sys.application.service;

import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.dto.SingletonResponse;
import com.tuccicode.boot.sys.application.dto.vo.ImageCaptchaVO;
import com.tuccicode.boot.sys.domain.entity.captcha.CaptchaType;
import com.tuccicode.boot.sys.domain.service.ImageCaptchaService;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author tucci.lee
 */
@Service
public class CaptchaApplicationService {

    private final ImageCaptchaService imageCaptchaService;

    public CaptchaApplicationService(ImageCaptchaService imageCaptchaService) {
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
