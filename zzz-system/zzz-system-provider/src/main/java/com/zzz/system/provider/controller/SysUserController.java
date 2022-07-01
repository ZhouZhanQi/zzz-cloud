package com.zzz.system.provider.controller;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.feigns.SysUserClient;
import com.zzz.system.api.model.domain.SysUser;
import com.zzz.system.provider.mapper.SysUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RequiredArgsConstructor
@RestController
public class SysUserController implements SysUserClient {

    private final SysUserMapper sysUserMapper;

    @Override
    public ResponseData<SysUser> getById(Long id) {
        return ResponseData.success(sysUserMapper.selectById(id));
    }
}
