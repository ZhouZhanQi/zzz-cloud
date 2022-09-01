package com.zzz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzz.system.api.model.bo.SysUserBo;
import com.zzz.system.api.model.domain.SysUser;
import com.zzz.system.api.model.dto.SysUserDTO;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
public interface ISysUserService extends IService<SysUser> {


    /**
     * 获取用户详细信息
     * 包含部门，职位，租户信息
     * @param username
     * @return
     */
    SysUserBo loadUserDetailByUsername(String username);

    /**
     *
     * @param mobilePhone
     * @return
     */
    SysUserBo loadUserDetailByMobilePhone(String mobilePhone);


    /**
     * 用户信息创建
     * @param sysUser
     * @return
     */
    SysUserDTO sysUserCreate(SysUserDTO sysUser);
}
