package com.zzz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzz.system.api.model.domain.SysDept;
import com.zzz.system.provider.controller.SysDeptController;

/**
 * <p>
 * 系统租户部门 服务类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 根据职位Id查询部门信息
     * @userId 用户Id
     * @return 返回部门信息
     */
    SysDept getByUserId(Long userId);

    /**
     * 根据部门Id获取部门信息
     * @param id
     * @return
     */
    SysDept getByIdIgnoreTenant(Long id);

    /**
     * 创建部门信息
     * @param dept
     * @return
     */
    SysDept createDept(SysDept dept);
}
