package com.zzz.system.api.service;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.domain.SysClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: zhouzq
 * @date: 2022/7/4-16:22
 * @desc:
 */
public interface RemoteSysClientService {

    /**
     * 根据客户端id获取客户端信息
     * @param clientId
     * @return
     */
    @GetMapping("/sysClient/clientId/{clientId}")
    ResponseData<SysClient> getByClientId(@PathVariable("clientId") String clientId);


}
