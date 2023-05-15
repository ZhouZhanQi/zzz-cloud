package com.zzz.system.provider.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.framework.common.exceptions.BusinessException;
import com.zzz.system.api.model.domain.SysDept;
import com.zzz.system.api.model.domain.SysPost;
import com.zzz.system.api.model.domain.SysTenant;
import com.zzz.system.provider.mapper.SysDeptMapper;
import com.zzz.system.provider.service.ISysDeptService;
import com.zzz.system.provider.service.ISysPostService;
import com.zzz.system.provider.service.ISysTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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


    private final ISysTenantService sysTenantService;

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

    @Override
    public SysDept createDept(SysDept dept) {
        //校验租户信息是否正确
        //todo 校验租户状态
        Optional.ofNullable(sysTenantService.getById(dept.getTenantId())).orElseThrow(() -> new BusinessException("租户信息异常"));
        this.save(dept);
        return dept;
    }
}
