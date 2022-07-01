package com.zzz.oauth.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.oauth.api.model.domain.SysUserPost;
import com.zzz.oauth.provider.mapper.SysUserPostMapper;
import com.zzz.oauth.provider.service.ISysUserPostService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户职位关联 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {

}
