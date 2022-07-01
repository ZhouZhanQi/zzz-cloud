package com.zzz.oauth.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.oauth.api.model.domain.SysRolePost;
import com.zzz.oauth.provider.mapper.SysRolePostMapper;
import com.zzz.oauth.provider.service.ISysRolePostService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色身份关联 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
public class SysRolePostServiceImpl extends ServiceImpl<SysRolePostMapper, SysRolePost> implements ISysRolePostService {

}
