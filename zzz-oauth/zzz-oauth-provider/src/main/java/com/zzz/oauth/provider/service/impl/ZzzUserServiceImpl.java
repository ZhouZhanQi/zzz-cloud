package com.zzz.oauth.provider.service.impl;

import com.google.common.collect.Lists;
import com.zzz.oauth.api.model.bo.SysUserBo;
import com.zzz.oauth.api.model.domain.SysRole;
import com.zzz.oauth.api.model.enums.SysUserTypeEnum;
import com.zzz.oauth.provider.service.IZzzUserService;
import com.zzz.oauth.provider.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhouzhanqi
 * @date 2021/9/12 2:53 上午
 * @desc 系统用户认证服务
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ZzzUserServiceImpl implements IZzzUserService {

    private final ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByPhone(String mobilePhone) throws UsernameNotFoundException {
        SysUserBo sysUserBo = sysUserService.loadUserDetailByUsername(mobilePhone);
        //todo 用户状态校验
        return null;
    }

    @Override
    public UserDetails loadUserBySocial(String openId) throws UsernameNotFoundException {
        return null;
    }

    @Override
    public UserDetails loadUserByName(String username) throws UsernameNotFoundException {
        SysUserBo sysUserBo = sysUserService.loadUserDetailByUsername(username);
        List<Long> roleIdList = Lists.newArrayList();

        if (SysUserTypeEnum.PLATFORM_USER == SysUserTypeEnum.fromCode(sysUserBo.getUserType())
                && sysUserBo.getIsSuper()) {
            //平台超级管理员
            roleIdList.add(-1L);
        } else {
            roleIdList = sysUserBo.getSysRoleList().stream().mapToLong(SysRole::getId).boxed().collect(Collectors.toList());
        }
        return null;
    }
}
