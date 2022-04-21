package com.tuccicode.boot.app.shiro;

import com.tuccicode.boot.domain.system.entity.user.SysUser;
import org.apache.shiro.SecurityUtils;

/**
 * @author tucci.lee
 */
public class PrincipalUtils {

    public static SysUser getUser() {
        return (SysUser) SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getUid() {
        return getUser().getUid();
    }
}
