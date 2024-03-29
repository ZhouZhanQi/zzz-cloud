package com.zzz.system.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzz.system.api.model.domain.SysPost;

/**
 * <p>
 * 系统租户职位 服务类
 * </p>
 *
 * @author zhouzhanqi
 * @since 2021-07-27
 */
public interface ISysPostService extends IService<SysPost> {


    /**
     * 根据用户Id获取职位
     * @param userId
     * @return
     */
    SysPost getByUserId(Long userId);


    /**
     * 创建职位信息
     * @param sysPost
     * @return
     */
    SysPost createSysPost(SysPost sysPost);

}
