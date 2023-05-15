package com.zzz.system.api.service;


import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.bo.SysUserBo;
import com.zzz.system.api.model.domain.SysUser;
import com.zzz.system.api.model.dto.SysUserDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

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
    public ResponseData<SysUserBo> getFullInfoByUsername(@PathVariable("username") @NotEmpty(message = "用户名不能为空") String username);

    /**
     * 根据手机号获取用户信息
     * @param mobilePhone
     * @return
     */
    @GetMapping(name = "获取用户全部信息-根据手机号", value = "sysUser/fullInfo-mobilePhone/{mobilePhone}")
    public ResponseData<SysUserBo> getFullInfoByMobilePhone(@PathVariable("mobilePhone") @NotEmpty(message = "手机号码不能为空") String mobilePhone);


    /**
     * 创建用户信息
     * @return
     */
    @PostMapping(name = "创建用户信息", value = "sysUser")
    public ResponseData<SysUser> createSysUser(@RequestBody @Validated SysUserDTO sysUser);

}
