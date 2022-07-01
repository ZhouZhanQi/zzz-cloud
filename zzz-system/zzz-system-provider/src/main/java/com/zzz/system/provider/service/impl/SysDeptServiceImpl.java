package com.zzz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.system.api.model.domain.SysDept;
import com.zzz.system.api.model.domain.SysPost;
import com.zzz.system.provider.mapper.SysDeptMapper;
import com.zzz.system.provider.service.ISysDeptService;
import com.zzz.system.provider.service.ISysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统租户部门 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
@RequiredArgsConstructor
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    private final ISysPostService sysPostService;

    @Override
    public SysDept getByUserId(Long userId) {
        //查询用户职位
        SysPost sysPost = sysPostService.getByUserId(userId);
        //查询用户部门
        return this.getById(sysPost.getDeptId());
    }

    @Override
    public SysDept getByIdIgnoreTenant(Long id) {
        return this.getBaseMapper().selectOneIgnoreTenant(Wrappers.lambdaQuery(SysDept.class).eq(SysDept::getId, id));
    }
}
