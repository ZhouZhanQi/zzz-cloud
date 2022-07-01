package com.zzz.system.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.system.api.model.domain.SysUserPost;
import com.zzz.system.provider.mapper.SysUserPostMapper;
import com.zzz.system.provider.service.ISysUserPostService;
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
