package com.zzz.system.api.model.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.zzz.framework.starter.core.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 系统租户部门
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysDept extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    private Long id;

    /**
     * 部门名称
     */
    @NotEmpty(message = "部门名称不能为空")
    @TableField("dept_name")
    private String deptName;

    /**
     * 部门负责人
     */
    private String leader;

    /**
     * 部门电话
     */
    private String phone;

    /**
     * 部门邮件
     */
    private String email;

    /**
     * 部门状态
     */
    private Integer status;

    /**
     * 部门排序
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 父部门Id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 部门所属租户
     */
    @NotNull(message = "部门所属租户不能为空")
    @TableField("tenant_id")
    private Long tenantId;
}
