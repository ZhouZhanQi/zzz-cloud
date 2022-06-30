package com.zzz.oauth.provider.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzz.oauth.api.model.domain.SysTenant;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统租户 Mapper 接口
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@InterceptorIgnore(tenantLine = "true")
@Mapper
public interface SysTenantMapper extends BaseMapper<SysTenant> {

}
