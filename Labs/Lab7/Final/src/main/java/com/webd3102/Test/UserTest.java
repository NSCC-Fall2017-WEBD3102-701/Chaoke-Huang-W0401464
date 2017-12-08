package com.webd3102.Test;

import com.webd3102.User;
import com.webd3102.UserDBUtil;
import com.webd3102.UserService;
import junit.framework.Assert;
import org.junit.Test;


public class UserTest {

    public UserTest() throws Exception {
    }

    @Test
    public void UserInstantiate() {

        User user = new User();
        Assert.assertEquals(true, user != null);

        return;

    }
    @Test
    public void TestUserBalance() {

        User user1 = new User();
        Assert.assertEquals(0.0,user1.getBalance());

        User user = new User("Hi", "CK", "Huang", "ck@email.com", "password");

                Assert.assertEquals(1000.0,user.getBalance());
        return;

    }

    @Test
    public void TestPasswordEncryption() {

        Assert.assertNotSame(UserService.generateHash("password"),"password");

        Assert.assertEquals(true,UserService.generateHash("password").length()>20);
    }

}
