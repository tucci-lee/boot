package com.tuccicode.boot.app.system.controller;

import com.tuccicode.boot.app.aspect.Limit;
import com.tuccicode.boot.app.shiro.PrincipalUtils;
import com.tuccicode.boot.app.system.dto.body.ChangePasswordBody;
import com.tuccicode.boot.app.system.dto.body.LoginBody;
import com.tuccicode.boot.app.system.service.SysLoginLogAppService;
import com.tuccicode.boot.app.system.service.SysUserAppService;
import com.tuccicode.boot.app.util.WebUtils;
import com.tuccicode.boot.domain.core.exception.BootBizCode;
import com.tuccicode.boot.domain.system.entity.captcha.CaptchaType;
import com.tuccicode.boot.domain.system.service.ImageCaptchaService;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.raccoon.exception.BizException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author tucci.lee
 */
@RestController
public class AuthorizeController {

    private final ImageCaptchaService imageCaptchaService;

    private final SysLoginLogAppService sysLoginLogAppService;
    private final SysUserAppService sysUserAppService;

    public AuthorizeController(ImageCaptchaService imageCaptchaService,
                               SysLoginLogAppService sysLoginLogAppService,
                               SysUserAppService sysUserAppService) {
        this.imageCaptchaService = imageCaptchaService;
        this.sysLoginLogAppService = sysLoginLogAppService;
        this.sysUserAppService = sysUserAppService;
    }

    /**
     * 账号密码登录, 限制5分总内只能请求3次
     * 1. 校验验证码
     * 2. shiro登录
     * 3. 记录日志（无论成功失败）
     *
     * @param body 登录参数
     * @return Response
     */
    @Limit(key = "#body.username", rate = 3, rateInterval = 5 * 60)
    @PostMapping("login")
    public Response login(@Validated @RequestBody LoginBody body) {
        boolean status = true;
        String message = "";
        try {
            HttpSession session = WebUtils.getRequest().getSession();
            imageCaptchaService.verify(CaptchaType.LOGIN, session.getId(), body.getCaptcha());

            UsernamePasswordToken token = new UsernamePasswordToken(body.getUsername(), body.getPassword(), body.getRememberMe());
            try {
                SecurityUtils.getSubject().login(token);
            } catch (UnknownAccountException | CredentialsException e) {
                throw new BizException(BootBizCode.ACCOUNT_OR_PASSWORD_ERROR);
            } catch (LockedAccountException e) {
                throw new BizException(BootBizCode.ACCOUNT_LOCKED);
            }
        } catch (Exception e) {
            status = false;
            message = e.getMessage();
            throw e;
        } finally {
            String userAgent = WebUtils.getRequest().getHeader("User-Agent");
            String ip = WebUtils.getIp();
            sysLoginLogAppService.create(body.getUsername(), status, message, ip, userAgent);
        }
        return Response.success();
    }

    /**
     * 退出登录
     *
     * @return Response
     */
    @PostMapping("logout")
    public Response logout() {
        SecurityUtils.getSubject().logout();
        return Response.success();
    }

    /**
     * 修改自己的密码
     *
     * @param body 请求
     * @return Response
     */
    @PutMapping("change_password")
    public Response changePassword(@Validated @RequestBody ChangePasswordBody body) {
        Long uid = PrincipalUtils.getUid();
        return sysUserAppService.changePassword(body, uid);
    }
}
