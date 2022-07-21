package com.zzz.auth.provider.service.impl;

import com.zzz.auth.provider.service.ZzzUserDetailService;
import com.zzz.framework.starter.core.utils.ClientUtils;
import com.zzz.system.client.service.RemoteSysUserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

/**
 * @author: zhouzq
 * @date: 2022/7/6-16:24
 * @desc: zzz用户详情服务
 */
@Service
@RequiredArgsConstructor
public class ZzzUserDetailServiceImpl implements ZzzUserDetailService {

    private final RemoteSysUserServiceClient remoteSysUserServiceClient;

    @Override
    public UserDetails loadUserByPhone(String mobilePhone) throws UsernameNotFoundException {
        return Optional.ofNullable(ClientUtils.serviceCallDataNoThrow(remoteSysUserServiceClient.getFullInfoByMobilePhone(mobilePhone)))
                .map(sysUserBo -> {
                    return User.builder()
                            .username(sysUserBo.getUserName())
                            .build();
                }).orElseThrow();
    }

    @Override
    public UserDetails loadUserBySocial(String openId) throws UsernameNotFoundException {
        return User.builder().build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(ClientUtils.serviceCallDataNoThrow(remoteSysUserServiceClient.getFullInfoByUsername(username)))
                .map(sysUserBo -> {
                    return User.builder()
                            .username(sysUserBo.getUserName())
                            .build();
                }).orElseThrow();
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
