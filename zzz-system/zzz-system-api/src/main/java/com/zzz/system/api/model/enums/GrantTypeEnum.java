package com.zzz.system.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/9/14-10:58
 * @desc: 授权类型枚举
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum GrantTypeEnum {

    PASSWORD(1, "password", "密码登录"),

    CAPTCHA(2, "captcha", "图片验证码"),

    SMS_CODE(3, "sms", "短信验证码"),

    SOCIAL(4, "social", "社交账号"),
    ;

    private final Integer code;

    private final String key;

    private final String value;

    /**
     * 根据key值获取枚举
     * @param key
     * @return
     */
    public static GrantTypeEnum fromKey(String key) {
        return Arrays.stream(GrantTypeEnum.values()).filter(item -> item.getKey().equals(key)).findFirst().orElse(null);
    }
}
