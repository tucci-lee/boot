package com.tuccicode.boot.sys.application.service;

import com.tuccicode.boot.dto.PageResponse;
import com.tuccicode.boot.dto.Response;
import com.tuccicode.boot.sys.application.assembler.LogLoginAssembler;
import com.tuccicode.boot.sys.application.dto.vo.LogLoginVO;
import com.tuccicode.boot.sys.domain.entity.log.LogLogin;
import com.tuccicode.boot.sys.domain.entity.log.LogLoginQuery;
import com.tuccicode.boot.sys.domain.service.LogLoginService;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogLoginApplicationService {

    private final LogLoginService logLoginService;

    public LogLoginApplicationService(LogLoginService logLoginService) {
        this.logLoginService = logLoginService;
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
        LogLogin log = new LogLogin()
                .setUsername(username)
                .setOs(os)
                .setBrowser(browser)
                .setStatus(status)
                .setMessage(message)
                .setIp(ip);
        logLoginService.add(log);
    }

    public Response list(LogLoginQuery query) {
        PageResponse<LogLogin> page = logLoginService.list(query);
        List<LogLoginVO> logLoginVOList = page.getData().stream()
                .map(LogLoginAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(logLoginVOList, page.getTotal());
    }
}
