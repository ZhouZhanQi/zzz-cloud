package com.zzz.system.provider.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzz.system.api.model.domain.SysUserGroup;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户组关联 Mapper 接口
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@InterceptorIgnore(tenantLine = "true")
@Mapper
public interface SysUserGroupMapper extends BaseMapper<SysUserGroup> {
}
