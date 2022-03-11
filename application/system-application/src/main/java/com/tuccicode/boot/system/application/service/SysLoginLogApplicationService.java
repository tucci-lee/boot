package com.tuccicode.boot.system.application.service;

import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import com.tuccicode.boot.system.application.assembler.LogLoginAssembler;
import com.tuccicode.boot.system.application.dto.vo.SysLoginLogVO;
import com.tuccicode.boot.system.domain.entity.log.SysLoginLog;
import com.tuccicode.boot.system.domain.entity.log.SysLoginLogQuery;
import com.tuccicode.boot.system.domain.service.SysLoginLogService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysLoginLogApplicationService {

    private final SysLoginLogService sysLoginLogService;

    public SysLoginLogApplicationService(SysLoginLogService sysLoginLogService) {
        this.sysLoginLogService = sysLoginLogService;
    }

    /**
     * 添加登录日志
     *
     * @param username     用户名
     * @param status       登录状态
     * @param message      登录信息
     * @param ip           ip
     * @param userAgentStr userAgent
     */
    public void addLog(String username, boolean status, String message, String ip, String userAgentStr) {
        // 获取浏览器信息（操作系统，浏览器）
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        String os = userAgent.getOperatingSystem().getName();
        String browser = userAgent.getBrowser().getName();
        SysLoginLog log = new SysLoginLog()
                .setUsername(username)
                .setOs(os)
                .setBrowser(browser)
                .setStatus(status)
                .setMessage(message)
                .setIp(ip);
        sysLoginLogService.add(log);
    }

    public Response list(SysLoginLogQuery query) {
        PageResponse<SysLoginLog> page = sysLoginLogService.list(query);
        List<SysLoginLogVO> sysLoginLogVOList = page.getData().stream()
                .map(LogLoginAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysLoginLogVOList, page.getTotal());
    }
}
