package com.zzz.auth.provider.support.base;

import com.zzz.framework.starter.core.model.ZzzUser;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsSet;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:20
 * @desc: token增强
 */
public class ZzzOAuth2TokenCustomizer implements OAuth2TokenCustomizer<OAuth2TokenClaimsContext> {
    @Override
    public void customize(OAuth2TokenClaimsContext context) {
        OAuth2TokenClaimsSet.Builder claims = context.getClaims();
        String clientId = context.getAuthorizationGrant().getName();
        claims.claim("clientId", clientId);
        // 客户端模式不返回具体用户信息
    }
}
