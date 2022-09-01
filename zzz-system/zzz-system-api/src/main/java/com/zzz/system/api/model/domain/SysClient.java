package com.zzz.system.api.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zzz.framework.starter.core.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 客户端
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysClient extends BaseModel {

    private static final long serialVersionUID = 1L;

    /**
     * 主键Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 客户端Id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 资源集合
     */
    private String resourceIds;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 授权类型
     */
    private String authorizedGrantTypes;

    /**
     * 权限
     */
    private String authorities;

    /**
     * 回调地址
     */
    private String redirectUri;

    /**
     * 令牌过期时间
     */
    private Integer accessTokenValidity;

    /**
     * 刷新令牌过期时间
     */
    private Integer refreshTokenValidity;

    /**
     * 是否自动授权
     */
    private Boolean autoapprove;

    /**
     * 说明
     */
    private String additionalInformation;
}
