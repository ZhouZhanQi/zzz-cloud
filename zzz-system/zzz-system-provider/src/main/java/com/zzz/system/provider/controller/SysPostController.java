package com.zzz.system.provider.controller;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.domain.SysPost;
import com.zzz.system.api.service.RemoteSysPostService;
import com.zzz.system.provider.service.ISysPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project : zzz-cloud
 * @Desc : 职位信息控制器
 * @Author : Zzz
 * @Datetime : 2023/5/9 13:38
 */
@RequiredArgsConstructor
@RestController
public class SysPostController implements RemoteSysPostService{


    private final ISysPostService sysPostService;

    @Override
    public ResponseData<SysPost> createPost(SysPost post) {
        return ResponseData.success();
    }
}
