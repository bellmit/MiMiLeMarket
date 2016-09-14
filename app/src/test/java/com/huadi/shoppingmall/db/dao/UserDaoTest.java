package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.BuildConfig;
import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.User;
import com.huadi.shoppingmall.util.BaseApplication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

/**
 * Created by smartershining on 16-7-15.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)

public class UserDaoTest {

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        UserDao dao = new UserDao(RuntimeEnvironment.application);
        assertTrue(dao.saveUser(user));

    }

    @Test
    public void saveUser() throws Exception {

    }

    @Test
    public void findUserByNameAndPassword() throws Exception {

        UserDao dao = new UserDao(RuntimeEnvironment.application);
        assertTrue(dao.findUserByNameAndPassword("admin","admin"));

    }

    @Test
    public void findUserbyName() throws Exception {

        UserDao dao = new UserDao(RuntimeEnvironment.application);
        assertEquals(true, dao.findUserbyName("admin"));

    }

    @Test
    public void updateUser() throws Exception {
        UserDao dao = new UserDao(RuntimeEnvironment.application);
        User u = new User();
        u.setName("ad");
        u.setPassword("tt");
        assertTrue(dao.saveUser(u));
        User user = dao.getUserById(1);
        System.out.println(user.getId() + " " + user.getName() + " " + user.getPassword() + " " + user.getRank());

        user.setPassword("admin");
        dao.updateUser(user);
        User user2 = dao.getUserById(1);
        System.out.println(user2.getName() + " " + user2.getPassword());


    }

    @Test
    public void getUser() throws Exception {
        UserDao dao = new UserDao(RuntimeEnvironment.application);
        User user = dao.getUserById(1);
        System.out.println(user.getName() + " " + user.getPassword());

        User user1 = dao.getUserById(2);
        System.out.println(user1.getName() + " " + user1.getPassword());


    }

    @Test
    public void  getUserById() throws Exception {

    }

}