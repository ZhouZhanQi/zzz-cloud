package com.zzz.system.provider.controller;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.domain.SysDept;
import com.zzz.system.api.service.RemoteSysDeptService;
import com.zzz.system.provider.service.ISysDeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Project : zzz-cloud
 * @Desc : 部门信息控制器
 * @Author : Zzz
 * @Datetime : 2023/5/9 13:56
 */
@RequiredArgsConstructor
@RestController
public class SysDeptController implements RemoteSysDeptService {


    private final ISysDeptService sysDeptService;

    @Override
    public ResponseData<SysDept> createDept(SysDept dept) {
        return ResponseData.success(sysDeptService.createDept(dept));
    }
}
