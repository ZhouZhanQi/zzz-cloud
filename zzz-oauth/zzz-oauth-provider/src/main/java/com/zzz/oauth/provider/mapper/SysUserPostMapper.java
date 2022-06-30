package com.zzz.oauth.provider.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzz.oauth.model.domain.SysUserPost;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户职位关联 Mapper 接口
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@InterceptorIgnore(tenantLine = "true")
@Mapper
public interface SysUserPostMapper extends BaseMapper<SysUserPost> {

}
