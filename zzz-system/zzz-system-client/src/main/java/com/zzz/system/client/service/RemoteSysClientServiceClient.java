package com.zzz.system.client.service;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.constants.ZzzSystemConstant;
import com.zzz.system.api.model.domain.SysClient;
import com.zzz.system.api.model.domain.SysUser;
import com.zzz.system.api.service.RemoteSysClientService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author: zhouzq
 * @date: 2022/7/4-16:25
 * @desc: 客户端信息远程调用服务
 */
@FeignClient(name = ZzzSystemConstant.SERVICE_NAME, fallback = RemoteSysClientServiceClient.FallBack.class)
public interface RemoteSysClientServiceClient extends RemoteSysClientService {

    @Component
    public class FallBack implements RemoteSysClientServiceClient {
        @Override
        public ResponseData<SysClient> getByClientId(String clientId) {
            return null;
        }
    }
}
