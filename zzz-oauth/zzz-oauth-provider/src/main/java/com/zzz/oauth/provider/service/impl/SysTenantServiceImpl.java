package com.zzz.oauth.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.oauth.api.model.domain.SysTenant;
import com.zzz.oauth.provider.mapper.SysTenantMapper;
import com.zzz.oauth.provider.service.ISysTenantService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统租户 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
public class SysTenantServiceImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

}
