package com.zzz.system.api.model.enums;

import com.zzz.framework.starter.core.model.code.RedisKeyPrefix;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Project : zzz-cloud
 * @Desc : 系统缓存key
 * @Author : Zzz
 * @Datetime : 2023/5/9 14:24
 */
@AllArgsConstructor
@Getter
public enum SysRedisKeyPrefix implements RedisKeyPrefix {


    SYS_USER("sys_user", true),
    ;

    private final String key;

    private final Boolean expireNotify;

    @Override
    public String key() {
        return this.getKey();
    }

    @Override
    public Boolean expireNotify() {
        return this.getExpireNotify();
    }
}