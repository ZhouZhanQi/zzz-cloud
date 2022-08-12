package com.zzz.auth.provider.support.customize;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

import java.util.*;

/**
 * @author: zhouzq
 * @date: 2022/7/12-10:54
 * @desc: 自定义令牌信息
 */
@Getter
public abstract class ZzzOauth2AuthenticationToken extends AbstractAuthenticationToken {

    /**
     * 授权类型
     */
    private final AuthorizationGrantType authorizationGrantType;

    /**
     * 令牌信息
     */
    private final Authentication clientPrincipal;

    /**
     * 域
     */
    private final Set<String> scopes;

    /**
     * 附加信息
     */
    private final Map<String, Object> additionalParameters;

    public ZzzOauth2AuthenticationToken(AuthorizationGrantType authorizationGrantType, Authentication clientPrincipal, Set<String> scopes, Map<String, Object> additionalParameters) {
        super(Collections.emptyList());
        this.authorizationGrantType = authorizationGrantType;
        this.clientPrincipal = clientPrincipal;
        this.scopes = Collections.unmodifiableSet(scopes != null ? new HashSet<>(scopes) : Collections.emptySet());
        this.additionalParameters = Collections.unmodifiableMap(additionalParameters != null ? new HashMap<>(additionalParameters) : Collections.emptyMap());
    }

    /**
     * 扩展模式
     * @return
     */
    @Override
    public Object getCredentials() {
        return "";
    }

    /**
     * 返回用户名
     * @return
     */
    @Override
    public Object getPrincipal() {
        return this.clientPrincipal;
    }
}
