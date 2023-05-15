package com.zzz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.framework.common.exceptions.BusinessException;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.system.api.model.code.OauthResponseCode;
import com.zzz.system.api.model.domain.SysPost;
import com.zzz.system.api.model.domain.SysUserPost;
import com.zzz.system.provider.mapper.SysPostMapper;
import com.zzz.system.provider.service.ISysPostService;
import com.zzz.system.provider.service.ISysUserPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 系统租户职位 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
@RequiredArgsConstructor
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {

    private final ISysUserPostService sysUserPostService;

    @Override
    public SysPost getByUserId(Long userId) {
        SysUserPost userPost = sysUserPostService.getOne(Wrappers.lambdaQuery(SysUserPost.class).eq(SysUserPost::getUserId, userId));
        AssertUtils.checkNotNull(userPost, new BusinessException(OauthResponseCode.POST_NOT_FOUND_ERROR));
        return this.getBaseMapper().selectOneIgnoreTenant(Wrappers.lambdaQuery(SysPost.class).eq(SysPost::getId, userPost.getPostId()));
    }


    @Override
    public SysPost createSysPost(SysPost sysPost) {
        //校验租户信息是否存在


        return null;
    }
}
