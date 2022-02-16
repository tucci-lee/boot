package com.tuccicode.boot.sys.domain.service;

import com.tuccicode.boot.sys.domain.entity.captcha.CaptchaType;

import java.awt.image.BufferedImage;

/**
 * @author tucci.lee
 */
public interface ImageCaptchaService {

    /**
     * 生成图片验证码
     *
     * @param type 验证码类型（方便扩展）
     * @param key  缓存图片验证码的key
     * @return BufferedImage
     */
    BufferedImage generate(CaptchaType type, String key);

    /**
     * 校验图片验证码
     *
     * @param type    验证码类型
     * @param key     缓存图片验证码的key
     * @param captcha 需要校验的验证码
     */
    void verify(CaptchaType type, String key, String captcha);
}
