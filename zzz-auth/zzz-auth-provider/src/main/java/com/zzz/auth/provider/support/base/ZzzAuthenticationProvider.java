package com.zzz.auth.provider.support.base;

import cn.hutool.extra.spring.SpringUtil;
import com.zzz.auth.api.model.code.AuthResponseCode;
import com.zzz.auth.provider.service.ZzzUserDetailService;
import com.zzz.framework.common.exceptions.BusinessException;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.framework.starter.core.model.enums.ZzzHeadParamNameEnum;
import com.zzz.framework.starter.core.utils.ServletUtils;
import com.zzz.system.api.model.enums.GrantTypeEnum;
import com.zzz.system.client.service.RemoteSysUserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author: zhouzq
 * @date: 2022/7/5-16:36
 * @desc:
 */
public class ZzzAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private PasswordEncoder passwordEncoder;

    public ZzzAuthenticationProvider() {
        //初始化password encoder
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
                AssertUtils.checkArgument(this.passwordEncoder.matches(presentedPassword, userDetails.getPassword()), AuthResponseCode.USERNAME_OR_PASSWORD_ERROR);
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        //获取用户远程调用客户端
        ZzzUserDetailService userDetailService = SpringUtil.getBean(ZzzUserDetailService.class);



        return null;
    }
}
