package com.zzz.system.api.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zzz.framework.starter.data.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统资源权限
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysResourcePermit extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 权限编码
     */
    @TableField("permit_code")
    private String permitCode;

    /**
     * 权限名称
     */
    @TableField("permit_name")
    private String permitName;

    /**
     * 权限请求
     */
    @TableField("permit_action_uri")
    private String permitActionUri;

    /**
     * 权限所属资源
     */
    @TableField("resource_code")
    private String resourceCode;
}
