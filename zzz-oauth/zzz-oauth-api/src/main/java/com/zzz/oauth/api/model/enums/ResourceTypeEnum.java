package com.zzz.oauth.api.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zhouzhanqi
 * @date 2021/8/17 8:45 下午
 * @desc 资源类型枚举
 */
@Getter
@AllArgsConstructor
public enum ResourceTypeEnum {

    CONTENT(1, "目录"),

    MENU(2, "菜单"),

    BUTTON(3, "按钮"),

    LINK(4, "链接"),
    ;
    private final Integer code;

    private final String value;
}
