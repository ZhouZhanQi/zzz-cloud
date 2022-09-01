package com.zzz.system.api.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zzz.framework.starter.core.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统租户职位
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysPost extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 职位编码
     */
    @TableField("post_code")
    private String postCode;

    /**
     * 职位名称
     */
    @TableField("post_name")
    private String postName;

    /**
     * 部门Id
     */
    @TableField("dept_id")
    private Long deptId;

    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;
}
