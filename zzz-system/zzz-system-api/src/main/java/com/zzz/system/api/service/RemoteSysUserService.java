package com.zzz.system.api.service;


import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.bo.SysUserBo;
import com.zzz.system.api.model.domain.SysUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface RemoteSysUserService {

    /**
     * 根据Id查询用户信息
     * @param id
     * @return
     */
    @GetMapping(name = "获取用户信息", value = "/sysUser/{id}")
    public ResponseData<SysUser> getById(@PathVariable("id") Long id);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @GetMapping(name = "获取用户全部信息-根据用户名", value = "sysUser/fullInfo-username/{username}")
    public ResponseData<SysUserBo> getFullInfoByUsername(@PathVariable("username") String username);

    /**
     * 根据手机号获取用户信息
     * @param mobilePhone
     * @return
     */
    @GetMapping(name = "获取用户全部信息-根据手机号", value = "sysUser/fullInfo-mobilePhone/{mobilePhone}")
    public ResponseData<SysUserBo> getFullInfoByMobilePhone(@PathVariable("mobilePhone") String mobilePhone);

}
