package com.tuccicode.boot.system.application.controller;

import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.system.application.service.CaptchaApplicationService;
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

    private final CaptchaApplicationService captchaService;

    public CaptchaController(CaptchaApplicationService captchaService) {
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
