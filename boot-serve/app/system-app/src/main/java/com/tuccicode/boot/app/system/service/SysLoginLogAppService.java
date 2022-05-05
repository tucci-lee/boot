package com.tuccicode.boot.app.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tuccicode.boot.app.system.assembler.LogLoginAssembler;
import com.tuccicode.boot.app.system.dto.vo.SysLoginLogVO;
import com.tuccicode.boot.domain.system.dataobject.SysLoginLogDO;
import com.tuccicode.boot.domain.system.dataobject.SysOperateLogDO;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLog;
import com.tuccicode.boot.domain.system.entity.log.SysLoginLogQuery;
import com.tuccicode.boot.domain.system.mapper.SysLoginLogMapper;
import com.tuccicode.boot.domain.system.service.SysLoginLogService;
import com.tuccicode.raccoon.dto.PageResponse;
import com.tuccicode.raccoon.dto.Response;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tucci.lee
 */
@Service
public class SysLoginLogAppService {

    private final SysLoginLogService sysLoginLogService;
    private final SysLoginLogMapper sysLoginLogMapper;

    public SysLoginLogAppService(SysLoginLogService sysLoginLogService,
                                 SysLoginLogMapper sysLoginLogMapper) {
        this.sysLoginLogService = sysLoginLogService;
        this.sysLoginLogMapper = sysLoginLogMapper;
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
    public void create(String username, boolean status, String message, String ip, String userAgentStr) {
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
        sysLoginLogService.create(log);
    }

    public Response page(SysLoginLogQuery query) {
        Page<SysLoginLogDO> page = new Page<>(query.getPageNo(), query.getPageSize());
        sysLoginLogMapper.selectList(page, query);
        List<SysLoginLogVO> sysLoginLogVOList = page.getRecords().stream()
                .map(LogLoginAssembler::toVO)
                .collect(Collectors.toList());
        return PageResponse.success(sysLoginLogVOList, (int) page.getTotal());
    }
}
