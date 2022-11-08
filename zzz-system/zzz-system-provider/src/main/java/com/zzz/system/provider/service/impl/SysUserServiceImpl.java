package com.zzz.system.provider.service.impl;

import cn.hutool.core.builder.Builder;
import cn.hutool.core.builder.GenericBuilder;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.framework.common.exceptions.BusinessException;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.system.api.model.bo.SysUserBo;
import com.zzz.system.api.model.code.OauthResponseCode;
import com.zzz.system.api.model.convert.SysUserConvert;
import com.zzz.system.api.model.domain.SysDept;
import com.zzz.system.api.model.domain.SysPost;
import com.zzz.system.api.model.domain.SysTenant;
import com.zzz.system.api.model.domain.SysUser;
import com.zzz.system.api.model.domain.SysUserPost;
import com.zzz.system.api.model.dto.SysUserDTO;
import com.zzz.system.provider.mapper.SysUserMapper;
import com.zzz.system.provider.service.ISysDeptService;
import com.zzz.system.provider.service.ISysPostService;
import com.zzz.system.provider.service.ISysTenantService;
import com.zzz.system.provider.service.ISysUserPostService;
import com.zzz.system.provider.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    private final ISysDeptService sysDeptService;

    private final ISysPostService sysPostService;

    private final ISysTenantService sysTenantService;


    private final ISysUserPostService sysUserPostService;


    @Override
    public SysUserBo loadUserDetailByUsername(String username) {
        SysUser sysUser = this.baseMapper.selectOneIgnoreTenant(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getUserName, username));
        return completionSysUserData(sysUser);
    }

    @Override
    public SysUserBo loadUserDetailByMobilePhone(String mobilePhone) {
        SysUser sysUser = this.baseMapper.selectOneIgnoreTenant(Wrappers.lambdaQuery(SysUser.class).eq(SysUser::getMobilePhone, mobilePhone));
        return completionSysUserData(sysUser);
    }

    @Override
    public SysUserDTO sysUserCreate(SysUserDTO sysUser) {
        this.save(sysUser);


        /**
         * 用户职位关联信息保存
         */
        List<SysUserPost> userPostList = sysUser.getPostIdList().stream().map(postId -> {
            return GenericBuilder.of(SysUserPost::new)
                    .with(SysUserPost::setUserId, sysUser.getId())
                    .with(SysUserPost::setPostId, postId)
                    .build();
        }).collect(Collectors.toList());


        //

        //保存职位关联信息
        sysUserPostService.saveBatch(userPostList);
        //保存角色关联信息

        return sysUser;
    }

    /**
     * 补全用户信息
     * @param sysUser
     * @return
     */
    private SysUserBo completionSysUserData(SysUser sysUser) {
        //校验密码
        AssertUtils.checkNotNull(sysUser, new BusinessException(OauthResponseCode.USERNAME_OR_PASSWORD_ERROR));
        //查询租户
        SysTenant sysTenant = sysTenantService.getById(sysUser.getTenantId());
        AssertUtils.checkNotNull(sysTenant, new BusinessException(OauthResponseCode.TENANT_NOT_FOUND_ERROR));

        //职位信息
        SysPost sysPost = sysPostService.getByUserId(sysUser.getId());
        AssertUtils.checkNotNull(sysPost, new BusinessException(OauthResponseCode.POST_NOT_FOUND_ERROR));
        //查询角色

        //部门信息
        SysDept sysDept = sysDeptService.getByIdIgnoreTenant(sysPost.getDeptId());
        AssertUtils.checkNotNull(sysDept, new BusinessException(OauthResponseCode.DEPT_NOT_FOUND_ERROR));

        //复制更新用户信息
        SysUserBo userBo = SysUserConvert.INSTANCE.convert2Bo(sysUser);
        userBo.setSysTenant(sysTenant);
        userBo.setSysDept(sysDept);
        userBo.setSysPost(sysPost);
        return userBo;
    }
}
