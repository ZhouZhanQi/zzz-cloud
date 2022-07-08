package com.zzz.auth.provider.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author: zhouzq
 * @date: 2022/7/6-16:21
 * @desc: zzz用户详情服务
 */
@Service
public interface ZzzUserDetailService extends UserDetailsService {

    /**
     * 根据手机号码登录
     * @param mobilePhone 手机号
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByPhone(String mobilePhone) throws UsernameNotFoundException;

    /**
     * 根据社交账号登录
     * @return
     * @param openId
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserBySocial(String openId) throws UsernameNotFoundException;
}
