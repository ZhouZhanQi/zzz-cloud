package com.zzz.gateway.util;

import com.zzz.framework.common.exceptions.FrameworkException;
import com.zzz.framework.common.util.AssertUtils;
import com.zzz.gateway.model.code.BasePlatformGatewayExceptionCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2021/10/28-17:13
 * @desc: 令牌工具类
 * </pre>
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AccessTokenUtils {

    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    /**
     * 获取token
     * @param headValue
     * @return
     */
    public static String getTokenFromHead(String headValue) {
        AssertUtils.checkArgument(StringUtils.isBlank(headValue) || headValue.toLowerCase().startsWith(BEARER_TOKEN_PREFIX.toLowerCase()), new FrameworkException(BasePlatformGatewayExceptionCode.TOKEN_INFO_ERROR));
        return headValue.replaceFirst(BEARER_TOKEN_PREFIX, "");
    }

}
