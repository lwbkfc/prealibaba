package com.lin.test.shiro;

import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by lwb on 2017-6-30.
 */
public class PermissionTest extends BaseTest{
    @Test
    public void testIsPermitted(){
        login("classpath:shiro-authorizer.ini","zhang","123");
//        Assert.assertTrue(subject().isPermitted("user:create"));
//        Assert.assertTrue(subject().isPermittedAll("user:update","user:delete"));
//        Assert.assertFalse(subject().isPermitted("user:view"));


        Assert.assertTrue(subject().isPermitted("+user1+2"));
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckPermission(){
        login("classpath:shiro-permission.ini","zhang","123");
        subject().checkPermission("user:create");
        subject().checkPermissions("user:delete","user:update");
        subject().checkPermissions("user:view");
    }
}
