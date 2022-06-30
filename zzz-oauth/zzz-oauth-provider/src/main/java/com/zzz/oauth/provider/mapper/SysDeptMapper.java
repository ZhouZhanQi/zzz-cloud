package com.zzz.oauth.provider.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzz.oauth.api.model.domain.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 系统租户部门 Mapper 接口
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 忽略租户查询部门信息
     * @param queryWrapper
     * @return
     */
    @InterceptorIgnore(tenantLine = "true")
    @Select("select * from sys_dept ${ew.customSqlSegment}")
    SysDept selectOneIgnoreTenant(@Param("ew") Wrapper<SysDept> queryWrapper);
}
