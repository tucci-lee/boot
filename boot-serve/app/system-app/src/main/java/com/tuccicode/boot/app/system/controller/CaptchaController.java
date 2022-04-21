package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.system.service.CaptchaAppService;
import com.tuccicode.raccoon.dto.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author tucci.lee
 */
@RestController
@RequestMapping("captcha")
public class CaptchaController {

    private final CaptchaAppService captchaService;

    public CaptchaController(CaptchaAppService captchaService) {
        this.captchaService = captchaService;
    }

    /**
     * 生成图片验证码
     *
     * @param session session id 作为验证码的缓存key
     * @return 验证码
     */
    @GetMapping("image")
    public Response image(HttpSession session) {
        return captchaService.generate(session.getId());
    }
}
