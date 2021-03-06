package com.tuccicode.boot.app.shiro.credential;

import com.tuccicode.boot.domain.system.service.SysUserService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author tucci.lee
 */
public class BCryptCredentialsMatcher implements CredentialsMatcher {

    private final SysUserService sysUserService;

    public BCryptCredentialsMatcher(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }


    /**
     * 校验密码是否正确
     *
     * @param token 登陆填写的密码
     * @param info  真实的密码
     * @return boolean
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String loginCredential = String.valueOf((char[]) token.getCredentials());
        String userCredential = (String) info.getCredentials();
        return sysUserService.verifyPassword(loginCredential, userCredential);
    }
}
