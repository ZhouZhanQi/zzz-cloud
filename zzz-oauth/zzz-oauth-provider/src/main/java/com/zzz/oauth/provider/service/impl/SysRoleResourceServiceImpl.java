package com.zzz.oauth.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.oauth.api.model.domain.SysRoleResource;
import com.zzz.oauth.provider.mapper.SysRoleResourceMapper;
import com.zzz.oauth.provider.service.ISysRoleResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色资源关联 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements ISysRoleResourceService {

}
