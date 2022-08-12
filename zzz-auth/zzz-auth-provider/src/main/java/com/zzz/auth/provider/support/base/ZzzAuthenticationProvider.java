package com.zzz.auth.provider.support.base;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.zzz.auth.api.model.code.AuthResponseCode;
import com.zzz.auth.provider.service.ZzzUserDetailService;
import com.zzz.framework.common.exceptions.BusinessException;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starter.core.model.enums.ZzzHeadParamNameEnum;
import com.zzz.framework.starter.core.utils.ServletUtils;
import com.zzz.system.api.model.enums.GrantTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Map;

/**
 * @author: zhouzq
 * @date: 2022/7/5-16:36
 * @desc:
 */
public class ZzzAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Getter
    @Setter
    private PasswordEncoder passwordEncoder;

    private final static BasicAuthenticationConverter BASIC_CONVERT = new BasicAuthenticationConverter();

    public ZzzAuthenticationProvider() {
        //初始化password encoder
        this.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //获取请求头信息中的grant_type
        String grantType = ServletUtils.getRequest().map(request -> {
            return request.getHeader(ZzzHeadParamNameEnum.GRANT_TYPE.getCode());
        }).orElseThrow(() -> new BusinessException(AuthResponseCode.AUTHENTICATION_GRANT_TYPE_IS_NULL));

        AssertUtils.checkNotNull(authentication.getCredentials(), AuthResponseCode.AUTHENTICATION_INFO_IS_NULL);
        //需要校验密码正确性
        switch (GrantTypeEnum.fromKey(grantType)) {
            case PASSWORD -> {
                String presentedPassword = authentication.getCredentials().toString();
                AssertUtils.checkArgument(this.passwordEncoder.matches(presentedPassword, userDetails.getPassword()), () -> new BusinessException(AuthResponseCode.USERNAME_OR_PASSWORD_ERROR));
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        HttpServletRequest request = ServletUtils.getRequest().orElseThrow(() -> new InternalAuthenticationServiceException("http servlet request is null"));
        //请求参数集合
        Map<String, String> paramMap = ServletUtils.getServletRequestParamMap(request);

        String grantType = paramMap.get(OAuth2ParameterNames.GRANT_TYPE);
        String clientId = StrUtil.isBlank(paramMap.get(OAuth2ParameterNames.CLIENT_ID)) ? BASIC_CONVERT.convert(request).getName() : paramMap.get(OAuth2ParameterNames.CLIENT_ID);

        Map<String, ZzzUserDetailService> userDetailServiceMap = SpringUtil.getBeansOfType(ZzzUserDetailService.class);
        UserDetails userDetails  = userDetailServiceMap.values().stream()
                .filter(service -> service.support(clientId, grantType))
                .max(Comparator.comparingInt(Ordered::getOrder))
                .orElseThrow(() -> new InternalAuthenticationServiceException("UserDetailService is error, may not register"))
                .loadUserByUsername(username);
        return userDetails;
    }
}
