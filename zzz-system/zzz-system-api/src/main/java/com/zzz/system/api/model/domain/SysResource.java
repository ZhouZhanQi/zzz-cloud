package com.zzz.system.api.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zzz.framework.starter.data.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统资源
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysResource extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源编码
     */
    @TableField("resource_code")
    private String resourceCode;

    /**
     * 资源名称
     */
    @TableField("resource_name")
    private String resourceName;

    /**
     * 父资源Id
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 排序
     */
    @TableField("order_num")
    private Integer orderNum;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否为外链
     */
    @TableField("is_frame")
    private Boolean isFrame;

    /**
     * 是否缓存
     */
    @TableField("is_cache")
    private Boolean isCache;

    /**
     * 是否显示
     */
    @TableField("is_visible")
    private Boolean isVisible;

    /**
     * 资源图标
     */
    private String icon;

    /**
     * 资源类型
     */
    @TableField("resource_type")
    private Integer resourceType;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 资源备注
     */
    private String remark;
}
