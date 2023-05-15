package com.zzz.auth.provider.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import com.google.common.collect.Lists;
import com.zzz.auth.api.model.code.AuthResponseCode;
import com.zzz.auth.provider.service.ZzzUserDetailService;
import com.zzz.framework.common.exceptions.BusinessException;
import com.zzz.framework.starter.cache.RedisCacheHelper;
import com.zzz.framework.starter.core.model.ZzzUser;
import com.zzz.framework.starter.core.utils.ClientUtils;
import com.zzz.framework.starter.security.model.bo.ZzzUserDetail;
import com.zzz.system.api.model.domain.SysRole;
import com.zzz.system.api.model.enums.SysRedisKeyPrefix;
import com.zzz.system.client.service.RemoteSysUserServiceClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author: zhouzq
 * @date: 2022/7/6-16:24
 * @desc: zzz用户详情服务
 */
@Service
@Slf4j
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
                }).orElseThrow(() -> new BusinessException(AuthResponseCode.USERNAME_OR_PASSWORD_ERROR));
    }

    @Override
    public UserDetails loadUserBySocial(String openId) throws UsernameNotFoundException {
        return User.builder().build();
    }

    @Override
    public ZzzUserDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(ClientUtils.serviceCallDataNoThrow(remoteSysUserServiceClient.getFullInfoByUsername(username)))
                .map(sysUserBo -> {
                    //todo 获取角色信息
                    List<String> authorityList = Lists.newArrayList("role");
                    List<GrantedAuthority> authorities = AuthorityUtils
                            .createAuthorityList(ArrayUtil.toArray(authorityList, String.class));

                    ZzzUserDetail zzzUserDetail = new ZzzUserDetail(sysUserBo.getUserName(), sysUserBo.getPassword(), authorities);
                    ZzzUser zzzUser = ZzzUser.builder()
                            .userId(sysUserBo.getId())
                            .userName(sysUserBo.getUserName())
                            .tenantId(sysUserBo.getTenantId())
                            .deptId(sysUserBo.getDeptId())
                            .postId(sysUserBo.getSysPost().getId()).build();
                   zzzUserDetail.setZzzUser(zzzUser);
                   return zzzUserDetail;
                }).orElseThrow(() -> new BusinessException(AuthResponseCode.USERNAME_OR_PASSWORD_ERROR));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
