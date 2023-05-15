package com.zzz.system.api.service;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.domain.SysPost;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Project : zzz-cloud
 * @Desc : 远程调用职位服务
 * @Author : Zzz
 * @Datetime : 2023/5/9 13:38
 */
public interface RemoteSysPostService {


    @PostMapping(name = "创建职位信息", value = "/sysPost")
    public ResponseData<SysPost> createPost(@RequestBody SysPost post);
}
