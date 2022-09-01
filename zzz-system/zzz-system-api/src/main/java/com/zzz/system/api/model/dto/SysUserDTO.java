package com.zzz.system.api.model.dto;

import com.zzz.framework.starter.core.model.BaseDTO;
import com.zzz.framework.starter.core.model.BaseValidateGroup;
import com.zzz.system.api.model.domain.SysUser;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * <pre>
 * @author: zhouzhanqi
 * @datetime: 2022/9/1-14:33
 * @desc: 用户创建
 * </pre>
 */

@Data
public class SysUserDTO extends SysUser {

    /**
     * 角色Id列表
     */
    @NotNull(message = "", groups = BaseValidateGroup.InsertGroup.class)
    private List<Long> roleIdList;

    /**
     * 职位Id列表
     */
    @NotNull(message = "", groups = BaseValidateGroup.InsertGroup.class)
    private List<Long> postIdList;

    /**
     * 部门Id
     */
    @NotNull(message = "", groups = BaseValidateGroup.InsertGroup.class)
    private Long deptId;

}
