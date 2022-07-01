package com.zzz.oauth.provider.service;

import com.shadowlayover.common.security.user.ShadowlayoverUserDetailService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author zhouzhanqi
 * @date 2021/9/12 2:52 上午
 * @desc 系统用户服务
 */
public interface IZzzUserService {

    /**
     * 根据手机号码登录
     * @param mobilePhone 手机号
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByPhone(String mobilePhone) throws UsernameNotFoundException;


    /**
     * 根据用户名登录
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserByName(String username) throws UsernameNotFoundException;

    /**
     * 根据社交账号登录
     * @return
     * @param openId
     * @throws UsernameNotFoundException
     */
    UserDetails loadUserBySocial(String openId) throws UsernameNotFoundException;
}
