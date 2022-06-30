package com.zzz.oauth.api.model.bo;


import com.zzz.oauth.api.domain.SysDept;
import com.zzz.oauth.api.domain.SysPost;
import com.zzz.oauth.api.domain.SysRole;
import com.zzz.oauth.api.domain.SysTenant;
import com.zzz.oauth.api.domain.SysUser;
import lombok.*;

import java.util.List;

/**
 * @author zhouzhanqi
 * @date 2021/9/12 2:57 上午
 * @desc 用户信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserBo extends SysUser {

    /**
     * 部门信息
     */
    private SysDept sysDept;

    /**
     * 职位信息
     */
    private SysPost sysPost;

    /**
     * 租户信息
     */
    private SysTenant sysTenant;

    /**
     * 角色信息
     */
    private List<SysRole> sysRoleList;
}
