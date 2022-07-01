package com.zzz.oauth.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.oauth.api.model.domain.SysUserGroup;
import com.zzz.oauth.provider.mapper.SysUserGroupMapper;
import com.zzz.oauth.provider.service.ISysUserGroupService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户组关联 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
public class SysUserGroupServiceImpl extends ServiceImpl<SysUserGroupMapper, SysUserGroup> implements ISysUserGroupService {

}
