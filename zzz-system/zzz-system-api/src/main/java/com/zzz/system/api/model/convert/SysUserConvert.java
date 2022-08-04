package com.zzz.system.api.model.convert;

import com.zzz.system.api.model.bo.SysUserBo;
import com.zzz.system.api.model.domain.SysUser;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/21-20:02
 * @desc: 用户信息转换类
 * </pre>
 */
@Mapper(builder = @Builder(disableBuilder = true))
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper( SysUserConvert.class );

    /**
     * 用户信息转换
     * @param sysUser
     * @return
     */
    @Mappings({
        @Mapping(target = "sysDept", ignore = true),
        @Mapping(target = "sysPost", ignore = true),
        @Mapping(target = "sysTenant", ignore = true),
        @Mapping(target = "sysRoleList", ignore = true)
    })
    SysUserBo convert2Bo(SysUser sysUser);
}
