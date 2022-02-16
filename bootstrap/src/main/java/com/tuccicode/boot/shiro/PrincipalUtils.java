package com.tuccicode.boot.shiro;

import com.tuccicode.boot.sys.domain.entity.user.SysUser;
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
