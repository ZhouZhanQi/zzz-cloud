package com.zzz.oauth.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzz.oauth.api.model.domain.SysRolePost;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色身份关联 Mapper 接口
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Mapper
public interface SysRolePostMapper extends BaseMapper<SysRolePost> {
}
