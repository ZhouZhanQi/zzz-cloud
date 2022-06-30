package com.zzz.oauth.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/26-20:23
 * @desc: 用户类型枚举
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum SysUserTypeEnum {

    PLATFORM_USER(10, "平台用户"),
    TENANT_USER(20, "租户用户"),
    ;

    private final Integer code;

    private final String desc;

    public static SysUserTypeEnum fromCode(Integer code) {
        return Arrays.stream(SysUserTypeEnum.values())
                .filter(userType -> userType.code.equals(code))
                .findFirst().orElse(null);
    }
}
