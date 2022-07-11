package com.zzz.auth.provider.support.base;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenClaimsContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;

/**
 * @author: zhouzq
 * @date: 2022/7/11-18:41
 * @desc: oauth2 token生成器
 */
public class ZzzOauth2TokenGenerate implements OAuth2TokenGenerator<OAuth2AccessToken> {

    private OAuth2TokenCustomizer<OAuth2TokenClaimsContext> accessTokenCustomizer;

    @Override
    public OAuth2AccessToken generate(OAuth2TokenContext context) {
        return null;
    }
}
