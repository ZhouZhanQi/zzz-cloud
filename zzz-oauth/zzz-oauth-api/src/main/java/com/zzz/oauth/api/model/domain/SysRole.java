package com.zzz.oauth.api.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zzz.framework.starter.data.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统角色
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色编码
     */
    @TableField("role_code")
    private String roleCode;

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;

    /**
     * 数据权限
     */
    @TableField("data_scop")
    private Integer dataScop;

    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 是否删除
     */
    @TableField("is_deleted")
    private Boolean isDeleted;

    /**
     * 角色备注
     */
    private String remark;
}
