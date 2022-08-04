package com.zzz.auth.provider.support.customize;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starter.core.utils.ServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author: zhouzq
 * @date: 2022/7/12-11:09
 * @desc:
 */
public abstract class ZzzOauth2AuthenticationConvert<T extends ZzzOauth2AuthenticationToken> implements AuthenticationConverter {

    /**
     * 是否支持
     * @param grantType
     * @return
     */
    public abstract boolean support(String grantType);

    /**
     * 校验请求参数
     * @param request
     */
    public abstract void checkRequestParam(HttpServletRequest request);


    /**
     * 构造token
     * @param clientPrincipal
     * @param requestedScopes
     * @param additionalParameters
     * @return
     */
    public abstract T buildToken(Authentication clientPrincipal, Set<String> requestedScopes,
                                 Map<String, Object> additionalParameters);

    @Override
    public Authentication convert(HttpServletRequest request) {
        //
        String grantType = request.getParameter(OAuth2ParameterNames.GRANT_TYPE);
        if (!support(grantType)) {
            return null;
        }

        MultiValueMap<String, String> parameterMap = ServletUtils.getServletRequestParamMultiValueMap(request);
        //scope 为空||唯一
        AssertUtils.checkArgument(StrUtil.isBlank(parameterMap.getFirst(OAuth2ParameterNames.SCOPE))
                || (StrUtil.hasBlank(parameterMap.getFirst(OAuth2ParameterNames.SCOPE)) && parameterMap.get(OAuth2ParameterNames.SCOPE).size() != 1), "");

        // 获取当前已经认证的客户端信息
        Authentication clientPrincipal = SecurityContextHolder.getContext().getAuthentication();

        Map<String, Object> additionalParameterMap = parameterMap.entrySet().stream()
                .filter(entry -> !entry.getKey().equals(OAuth2ParameterNames.GRANT_TYPE) && !entry.getKey().equals(OAuth2ParameterNames.SCOPE))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));
        // 创建token
        return buildToken(clientPrincipal, Sets.newHashSet(), additionalParameterMap);
    }
}
