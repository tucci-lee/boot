package com.tuccicode.boot.system.domain.service.impl;

import com.google.code.kaptcha.Producer;
import com.tuccicode.boot.system.domain.constant.CacheConst;
import com.tuccicode.boot.system.domain.entity.captcha.CaptchaType;
import com.tuccicode.boot.system.domain.exception.SysBizCode;
import com.tuccicode.boot.system.domain.service.ImageCaptchaService;
import com.tuccicode.raccoon.cache.CacheOperate;
import com.tuccicode.raccoon.exception.Assert;
import com.tuccicode.raccoon.exception.BizException;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

/**
 * @author tucci.lee
 */
@Service
public class ImageCaptchaServiceImpl implements ImageCaptchaService {

    private final Producer producer;
    private final CacheOperate cacheOperate;

    public ImageCaptchaServiceImpl(Producer producer,
                                   CacheOperate cacheOperate) {
        this.producer = producer;
        this.cacheOperate = cacheOperate;
    }

    @Override
    public BufferedImage generate(CaptchaType type, String key) {
        String captcha = producer.createText();
        BufferedImage image = producer.createImage(captcha);
        switch (type) {
            case LOGIN:
                cacheOperate.set(CacheConst.LOGIN_IMAGE_CAPTCHA + key, captcha, 5, TimeUnit.MINUTES);
                break;
            default:
                throw new BizException(SysBizCode.CAPTCHA_TYPE_ERROR);
        }
        return image;
    }

    @Override
    public void verify(CaptchaType type, String key, String captcha) {
        String cacheCaptcha;
        String cacheKey;
        switch (type){
            case LOGIN:
                cacheKey = CacheConst.LOGIN_IMAGE_CAPTCHA + key;
                cacheCaptcha = cacheOperate.get(cacheKey);
                break;
            default:
                throw new BizException(SysBizCode.CAPTCHA_TYPE_ERROR);
        }
        boolean satisfiedBy = cacheCaptcha != null && cacheCaptcha.equals(captcha);
        Assert.isTrue(satisfiedBy, SysBizCode.IMAGE_CAPTCHA_ERROR);
        cacheOperate.delete(cacheKey);
    }
}
