package com.tuccicode.boot.app.config;

import com.tuccicode.boot.app.shiro.filter.UserFilter;
import com.tuccicode.boot.app.shiro.realm.AccountRealm;
import com.tuccicode.boot.domain.system.service.SysLoginVersionService;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author tucci.lee
 */
@Configuration
@ConditionalOnClass(ShiroFilterFactoryBean.class)
public class ShiroConfig {

    private final SysLoginVersionService sysLoginVersionService;
    private final AccountRealm accountRealm;

    public ShiroConfig(SysLoginVersionService sysLoginVersionService,
                       AccountRealm accountRealm) {
        this.sysLoginVersionService = sysLoginVersionService;
        this.accountRealm = accountRealm;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        // 过滤链
        Map<String, String> filterChainMap = new LinkedHashMap<>();
        filterChainMap.put("/error/**", "anon");
        filterChainMap.put("/captcha/**", "anon");
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/druid/**", "perms[monitor:druid:view]");
        filterChainMap.put("/**", "user");

        // 自定义filter
        UserFilter userFilter = new UserFilter(sysLoginVersionService);
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("user", userFilter);

        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(securityManager);
        bean.setFilterChainDefinitionMap(filterChainMap);
        bean.setFilters(filters);
        return bean;
    }

    @Bean
    public SecurityManager securityManager(RememberMeManager rememberMeManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm);
        securityManager.setRememberMeManager(rememberMeManager);
        return securityManager;
    }

    /**
     * cookie管理对象;记住我功能
     *
     * @return RememberMeManager
     */
    @Bean
    public RememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        /*
         * rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
         *
         * KeyGenerator keygen = KeyGenerator.getInstance("AES");
         * SecretKey deskey = keygen.generateKey();
         * System.out.println(Base64.encodeToString(deskey.getEncoded()));
         */
        cookieRememberMeManager.setCipherKey(Base64.decode("L2nL4oh/0TSPlMk99a6TSw=="));
        return cookieRememberMeManager;
    }

    /**
     * 开启shiro注解，依赖spring aop
     *
     * @param securityManager securityManager
     * @return AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
