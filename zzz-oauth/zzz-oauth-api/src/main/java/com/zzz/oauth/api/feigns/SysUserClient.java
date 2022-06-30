package com.zzz.oauth.api.feigns;


import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.oauth.api.model.constants.ZzzOauthConstant;
import com.zzz.oauth.api.model.domain.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface SysUserClient {

    /**
     * 根据Id查询用户信息
     * @param id
     * @return
     */
    @GetMapping(name = "获取用户信息", value = "/sysUser/{id}")
    public ResponseData<SysUser> getById(@PathVariable("id") Long id);

}
