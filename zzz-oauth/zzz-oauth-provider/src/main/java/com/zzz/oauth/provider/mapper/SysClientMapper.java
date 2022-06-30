package com.zzz.oauth.provider.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzz.oauth.api.model.domain.SysClient;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 客户端 Mapper 接口
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-10-27
 */
@Mapper
public interface SysClientMapper extends BaseMapper<SysClient> {

}
