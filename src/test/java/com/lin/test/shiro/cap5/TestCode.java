package com.lin.test.shiro.cap5;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.StringUtils;

/**
 * Created by lwb on 2017-6-30.
 */
public class TestCode {
    @Test
    public void test(){
        String str = "hello";

        String base64Encoded = Base64.encodeToString(str.getBytes());

        String str2 = Base64.decodeToString(base64Encoded);

        Assert.assertEquals(str,str2);

        String str3 = "hello";
        String base64Encoded2 = Hex.encodeToString(str.getBytes());
        System.out.println(base64Encoded2);
        String str4 = new String(Hex.decode(base64Encoded2.getBytes()));
        Assert.assertEquals(str3, str4);
    }

    @Test
    public void test2(){
        String str = "hello";
        String salt = "123";
        String s = new Md5Hash(str, salt,2).toString();
        System.out.println(s);
    }
}
