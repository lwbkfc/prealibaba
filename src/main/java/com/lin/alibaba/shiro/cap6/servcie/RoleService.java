package com.lin.alibaba.shiro.cap6.servcie;

import com.lin.alibaba.shiro.cap6.entity.Role;

/**
 * Created by Administrator on 2017-07-02.
 */
public interface RoleService {
    public Role createRole(Role role);
    public void deleteRole(Long roleId);

    /**
     * 添加角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    public void correlationPermissions(Long roleId, Long... permissionIds);

    /**
     * 移除角色-权限之间关系
     * @param roleId
     * @param permissionIds
     */
    public void uncorrelationPermissions(Long roleId, Long... permissionIds);
}
