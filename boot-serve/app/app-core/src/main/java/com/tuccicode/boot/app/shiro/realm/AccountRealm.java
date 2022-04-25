package com.tuccicode.boot.app.shiro.realm;

import com.tuccicode.boot.app.shiro.credential.BCryptCredentialsMatcher;
import com.tuccicode.boot.domain.system.entity.res.SysRes;
import com.tuccicode.boot.domain.system.entity.user.SysUser;
import com.tuccicode.boot.domain.system.service.SysLoginVersionService;
import com.tuccicode.boot.domain.system.service.SysResService;
import com.tuccicode.boot.domain.system.service.SysUserService;
import com.tuccicode.boot.domain.system.util.AdminUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理账号密码登陆的Realm
 *
 * @author tucci.lee
 */
@Component
public class AccountRealm extends AuthorizingRealm {

    private final SysUserService sysUserService;
    private final SysResService sysResService;
    private final SysLoginVersionService sysLoginVersionService;

    public AccountRealm(SysUserService sysUserService,
                        SysResService sysResService,
                        SysLoginVersionService sysLoginVersionService) {
        this.sysUserService = sysUserService;
        this.sysResService = sysResService;
        this.sysLoginVersionService = sysLoginVersionService;
        // 凭证校验器
        this.setCredentialsMatcher(new BCryptCredentialsMatcher(sysUserService));
    }

    /**
     * 授权：获取用户的权限
     *
     * @param principalCollection principal
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SysUser sysUser = (SysUser) principalCollection.getPrimaryPrincipal();
        long uid = sysUser.getUid();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        if (uid == AdminUtils.getUid()) {
            info.addStringPermission("*");
        } else {
            List<String> resChars = sysResService.listByUid(uid)
                    .stream()
                    .filter(sysRes -> sysRes.getResChar() != null && !sysRes.getResChar().isEmpty())
                    .map(SysRes::getResChar)
                    .collect(Collectors.toList());
            info.addStringPermissions(resChars);
        }
        return info;
    }

    /**
     * 认证：用户登陆
     *
     * @param token Subject.login传入的token
     * @return AuthenticationInfo
     * @throws AuthenticationException exception
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        SysUser sysUser = sysUserService.getByUsername((String) token.getPrincipal());
        if (sysUser == null) {
            throw new UnknownAccountException();
        }
        if (sysUser.getIsLock()) {
            throw new LockedAccountException();
        }
        String userPassword = sysUser.getPassword();
        // 设置当前的登录版本
        int loginVersion = sysLoginVersionService.getVersionByUid(sysUser.getUid());
        sysUser.setLoginVersion(loginVersion)
                .setPassword(null);
        // 第一个参数可以通过SecurityUtils.getSubject().getPrincipal()获取
        return new SimpleAuthenticationInfo(sysUser, userPassword, getName());
    }

    /**
     * 多种登陆方式：当前realm处理的token类型
     *
     * @return Class
     */
    @Override
    public Class<? extends AuthenticationToken> getAuthenticationTokenClass() {
        return UsernamePasswordToken.class;
    }

}
