package com.zzz.system.client.service;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.bo.SysUserBo;
import com.zzz.system.api.model.constants.ZzzSystemConstant;
import com.zzz.system.api.model.domain.SysUser;
import com.zzz.system.api.model.dto.SysUserDTO;
import com.zzz.system.api.service.RemoteSysUserService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * 用户信息远程调用客户端
 * @author zhouzhanqi
 */
@FeignClient(contextId = "remoteSysUserService", name = ZzzSystemConstant.SERVICE_NAME, path = "/zzz-system", fallback = RemoteSysUserServiceClient.Fallback.class)
public interface RemoteSysUserServiceClient extends RemoteSysUserService {


    @Component
    public static class Fallback implements RemoteSysUserServiceClient {

        @Override
        public ResponseData<SysUser> getById(Long id) {
            return null;
        }

        @Override
        public ResponseData<SysUserBo> getFullInfoByUsername(String username) {
            return null;
        }

        @Override
        public ResponseData<SysUserBo> getFullInfoByMobilePhone(String mobilePhone) {
            return null;
        }

        @Override
        public ResponseData<SysUser> createSysUser(SysUserDTO sysUser) {
            return null;
        }
    }

}
