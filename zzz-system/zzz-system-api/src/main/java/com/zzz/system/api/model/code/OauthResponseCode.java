package com.zzz.system.api.model.code;

import com.zzz.framework.common.model.code.BaseExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/8/18-14:30
 * @desc: 用户中心响应编码枚举
 * code 10****
 *
 * </pre>
 */
@Getter
@AllArgsConstructor
public enum OauthResponseCode implements BaseExceptionCode {
    USERNAME_NOT_FOUND_ERROR(100001, "未找到用户信息"),
    TENANT_NOT_FOUND_ERROR(100002, "未找到租户信息"),
    DEPT_NOT_FOUND_ERROR(100003, "未找到部门信息"),
    POST_NOT_FOUND_ERROR(100003, "未找到职位信息"),

    AUTHENTICATION_ERROR(104010, "认证失败"),
    UNSUPPORT_GRANT_TYPE_ERROR(104011, "认证失败, 不支持的人这个类型"),
    INVALID_TOKEN_ERROR(104012, "认证失败, token信息错误"),
    INVALID_SCOPE_ERROR(104013, "认证失败, scope信息错误"),
    INVALID_GRANT_ERROR(104014, "认证失败, grant信息错误"),
    USERNAME_OR_PASSWORD_ERROR(104015, "用户名或密码错误"),

    SMS_CODE_AUTH_PARAM_EMPTY(105001, "请输入手机验证码"),
    SMS_CODE_AUTH_HAS_EXPIRED(105002, "验证码已过期"),
    SMS_CODE_AUTH_PARAM_ERROR(105002, "请输入正确的手机验证码"),
    ;
    private final int code;

    private final String message;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
