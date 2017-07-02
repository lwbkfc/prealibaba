package com.lin.alibaba.shiro.cap6.servcie;


import com.lin.alibaba.shiro.cap6.entity.Permission;

/**
 * Created by Administrator on 2017-07-02.
 */
public interface PermissionService {
    public Permission createPermission(Permission permission);

    public void deletePermission(Long permissionId);

}
