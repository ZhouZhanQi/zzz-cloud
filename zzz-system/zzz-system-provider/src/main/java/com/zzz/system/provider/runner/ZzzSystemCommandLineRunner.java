package com.zzz.system.provider.runner;

import cn.hutool.core.bean.BeanUtil;
import com.zzz.framework.starter.cache.RedisCacheHelper;
import com.zzz.framework.starters.security.model.bo.ClientDetailBo;
import com.zzz.framework.starters.security.model.code.SecurityRedisKeyPrefix;
import com.zzz.system.api.model.domain.SysClient;
import com.zzz.system.provider.service.ISysClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author: zhouzq
 * @date: 2022/7/11-13:35
 * @desc:
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ZzzSystemCommandLineRunner implements CommandLineRunner {

    private final ISysClientService sysClientService;

//    private final ISysRoleService sysRoleService;

    private final RedisCacheHelper<ClientDetailBo> redisCacheHelper;

    @Override
    public void run(String... args) {
        //缓存客户端信息
        List<SysClient> clientList = sysClientService.list();
        if (!CollectionUtils.isEmpty(clientList)) {
            log.info(">>> zzz system cache client detail ...");
            clientList.forEach(sysClient -> {
                ClientDetailBo clientDetail = BeanUtil.copyProperties(sysClient, ClientDetailBo.class);
                redisCacheHelper.set(SecurityRedisKeyPrefix.OAUTH_CLIENT, clientDetail.getClientId(), clientDetail);
            });
        }

        //缓存角色权限信息
//        List<SysRole> sysRoleList = sysRoleService.list();
//        if (!CollectionUtils.isEmpty(sysRoleList)) {
//            log.info(">>> zzz system cache role resource ...");
//            sysRoleList.stream().forEach(sysRole -> {
//
//            });
//        }
    }
}
