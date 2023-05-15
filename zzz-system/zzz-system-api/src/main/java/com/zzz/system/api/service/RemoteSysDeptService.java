package com.zzz.system.api.service;

import com.zzz.framework.starter.core.model.ResponseData;
import com.zzz.system.api.model.domain.SysDept;
import com.zzz.system.api.model.domain.SysPost;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Project : zzz-cloud
 * @Desc : 远程调用部门服务
 * @Author : Zzz
 * @Datetime : 2023/5/9 13:53
 */
public interface RemoteSysDeptService {

    @PostMapping(name = "创建部门信息", value = "/sysDept")
    public ResponseData<SysDept> createDept(@RequestBody @Validated SysDept dept);
}
