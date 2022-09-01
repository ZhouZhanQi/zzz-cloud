package com.zzz.auth.provider.support.pwd;

import cn.hutool.core.util.StrUtil;
import com.zzz.auth.provider.support.customize.ZzzOauth2AuthenticationConvert;
import com.zzz.framework.starter.core.utils.ServletUtils;
import com.zzz.system.api.model.enums.GrantTypeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:13
 * @desc:
 */
public class OAuth2ResourceOwnerPwdAuthenticationConvert extends ZzzOauth2AuthenticationConvert<OAuth2ResourceOwnerPwdAuthenticationToken> {



//    @Override
//    public Authentication convert(HttpServletRequest request) {
//        return null;
//    }

    @Override
    public boolean support(String grantType) {
        return AuthorizationGrantType.PASSWORD.getValue().equals(grantType);
    }

    @Override
    public void checkRequestParam(HttpServletRequest request) {
        Map<String, String> requestParam = ServletUtils.getServletRequestParamMap(request);
        if (StrUtil.hasBlank(requestParam.get(OAuth2ParameterNames.USERNAME), requestParam.get(OAuth2ParameterNames.PASSWORD))) {

            //todo 抛出异常
        }
    }

    @Override
    public OAuth2ResourceOwnerPwdAuthenticationToken buildToken(Authentication clientPrincipal, Set<String> requestedScopes, Map<String, Object> additionalParameters) {
        return new OAuth2ResourceOwnerPwdAuthenticationToken(AuthorizationGrantType.PASSWORD, clientPrincipal, requestedScopes, additionalParameters);
    }
}
