package com.zzz.system.provider.controller;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.bo.SysUserBo;
import com.zzz.system.api.model.dto.SysUserDTO;
import com.zzz.system.api.service.RemoteSysUserService;
import com.zzz.system.api.model.domain.SysUser;
import com.zzz.system.provider.mapper.SysUserMapper;
import com.zzz.system.provider.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RequiredArgsConstructor
@RestController
public class SysUserController implements RemoteSysUserService {

    private final ISysUserService sysUserService;

    @Override
    public ResponseData<SysUser> getById(Long id) {
        return ResponseData.success(sysUserService.getById(id));
    }

    @Override
    public ResponseData<SysUserBo> getFullInfoByUsername(String username) {
        return ResponseData.success(sysUserService.loadUserDetailByUsername(username));
    }

    @Override
    public ResponseData<SysUserBo> getFullInfoByMobilePhone(String mobilePhone) {
        return ResponseData.success(sysUserService.loadUserDetailByMobilePhone(mobilePhone));
    }

    @Override
    public ResponseData<SysUser> createSysUser(SysUserDTO sysUser) {
        return null;
    }
}
