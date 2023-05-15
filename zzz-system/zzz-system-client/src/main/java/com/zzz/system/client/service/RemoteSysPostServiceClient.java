package com.zzz.system.client.service;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.constants.ZzzSystemConstant;
import com.zzz.system.api.model.domain.SysPost;
import com.zzz.system.api.service.RemoteSysPostService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Project : zzz-cloud
 * @Desc : 远程调用职位服务熔断降级
 * @Author : Zzz
 * @Datetime : 2023/5/9 13:45
 */
@FeignClient(contextId = "remoteSysPostService", name = ZzzSystemConstant.SERVICE_NAME, path = "/zzz-system", fallback = RemoteSysPostServiceClient.Fallback.class)
public interface RemoteSysPostServiceClient extends RemoteSysPostService {
    class Fallback implements RemoteSysPostService {

        @Override
        public ResponseData<SysPost> createPost(SysPost post) {
            return null;
        }
    }
}
