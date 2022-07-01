package com.zzz.oauth.provider.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzz.oauth.api.model.domain.SysResource;
import com.zzz.oauth.provider.mapper.SysResourceMapper;
import com.zzz.oauth.provider.service.ISysResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统资源 服务实现类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

}
