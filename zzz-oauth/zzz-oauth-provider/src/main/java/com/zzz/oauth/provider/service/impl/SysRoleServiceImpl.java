package com.zzz.oauth.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.oauth.api.model.domain.SysRole;
import com.zzz.oauth.provider.mapper.SysRoleMapper;
import com.zzz.oauth.provider.service.ISysRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统角色 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

}
