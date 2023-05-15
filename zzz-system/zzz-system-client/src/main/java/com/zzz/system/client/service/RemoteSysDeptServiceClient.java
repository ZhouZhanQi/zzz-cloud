package com.zzz.system.client.service;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.constants.ZzzSystemConstant;
import com.zzz.system.api.model.domain.SysDept;
import com.zzz.system.api.service.RemoteSysDeptService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Project : zzz-cloud
 * @Desc : 远程调用部门服务客户端
 * @Author : Zzz
 * @Datetime : 2023/5/9 13:55
 */
@FeignClient(contextId = "remoteSysDeptService", name = ZzzSystemConstant.SERVICE_NAME, path = "/zzz-system", fallback = RemoteSysDeptServiceClient.Fallback.class)
public interface RemoteSysDeptServiceClient extends RemoteSysDeptService {
    class Fallback implements RemoteSysDeptService{
        @Override
        public ResponseData<SysDept> createDept(SysDept dept) {
            return null;
        }
    }
}
