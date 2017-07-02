package com.lin.test.shiro;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by lwb on 2017-6-30.
 */
public class RoleTest extends BaseTest {
    @Test
    public void testHasRole(){
        login("classpath:shiro-role.ini","zhang","123");
        //判断拥有角色1
        Assert.assertTrue(subject().hasRole("role1"));
        //判断拥有角色role1,role2
        Assert.assertTrue(subject().hasAllRoles(Arrays.asList("role1","role2")));
        //判断拥有角色：role1，role2，和!role3

        boolean[] booleans = subject().hasRoles(Arrays.asList("role1", "role2", "role3"));
        Assert.assertEquals(true,booleans[0]);
        Assert.assertEquals(true,booleans[1]);
        Assert.assertEquals(false,booleans[2]);
    }

    @Test(expected = UnauthorizedException.class)
    public void testCheckRole(){
        login("classpath:shiro-role.ini","zhang","123");
        subject().checkRole("role1");
        subject().checkRoles("role1","role3");
    }
}
