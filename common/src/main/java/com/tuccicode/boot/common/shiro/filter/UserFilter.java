package com.tuccicode.boot.common.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.tuccicode.boot.common.shiro.PrincipalUtils;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.system.domain.entity.user.SysUser;
import com.tuccicode.boot.system.domain.exception.SysBizCode;
import com.tuccicode.boot.system.domain.service.SysLoginVersionService;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tucci.lee
 */
public class UserFilter extends AccessControlFilter {
    private final SysLoginVersionService sysLoginVersionService;

    public UserFilter(SysLoginVersionService sysLoginVersionService) {
        this.sysLoginVersionService = sysLoginVersionService;
    }

    /**
     * 判断用户是否登陆
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 如果没登录直接返回false
        SysUser user = PrincipalUtils.getUser();
        if(user == null){
            return false;
        }
        // 如果当前登录用户的版本号不等于数据库的版本号返回false
        int loginVersion = sysLoginVersionService.getVersionByUid(user.getUid());
        return user.getLoginVersion() == loginVersion;
    }

    /**
     * 未登陆的错误处理
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setContentType("application/json;charset=UTF-8");
        String result = JSON.toJSONString(Response.failure(SysBizCode.UNAUTHENTICATED));
        res.getWriter().print(result);
        return false;
    }
}
