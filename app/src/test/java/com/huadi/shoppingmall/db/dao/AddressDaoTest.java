package com.huadi.shoppingmall.db.dao;

import android.content.Context;

import com.huadi.shoppingmall.BuildConfig;
import com.huadi.shoppingmall.model.Address;
import com.huadi.shoppingmall.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by smartershining on 16-7-16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)

public class AddressDaoTest {



    @Before
    public void setUp() throws Exception {
        UserDao dao  = new UserDao(RuntimeEnvironment.application);
        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        dao.saveUser(user);

        User user2 = new User();
        user2.setName("smart");
        user2.setPassword("20132269");
        dao.saveUser(user2);


        Address address = new Address();
        address.setName("smartershining");
        address.setAddress_info("xinanjiaotongdaxue");
        address.setPostCode("408300");
        address.setPhone("18482188024");
        address.setUser_id(1);

        Address address1 = new Address();
        address1.setName("smarter");
        address1.setAddress_info("xinanjiaotong");
        address1.setPostCode("00");
        address1.setPhone("12345");
        address1.setUser_id(2);

        AddressDao dao2 = new AddressDao(RuntimeEnvironment.application);
        assertTrue(dao2.saveAddress(address));
        assertTrue(dao2.saveAddress(address1));

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void saveAddress() throws Exception {

    }

    @Test
    public void updateAddress() throws Exception {
        Address address = new Address();
        AddressDao dao = new AddressDao(RuntimeEnvironment.application);


        List<Address> list = dao.loadAddress(1);
        for (Address a : list) {
            System.out.println(a.getName() + " " + a.getPhone() + " " + a.getPostCode() + " " +
                    a.getAddress_info());
        }

        Address address1 = list.get(0);
        address1.setName("cjx");

        dao.updateAddress(address1);
        list = dao.loadAddress(1);
        for (Address a : list) {
            System.out.println(a.getName() + " " + a.getPhone() + " " + a.getPostCode() + " " +
                    a.getAddress_info());
        }




    }

    @Test
    public void deleteAddress() throws Exception {
        AddressDao dao = new AddressDao(RuntimeEnvironment.application);
        List<Address> list = dao.loadAddress(1);
        assertEquals(1, list.size());
        dao.deleteAddress(1);
        List<Address> list2 = dao.loadAddress(1);
        assertEquals(0,list2.size());

    }

    @Test
    public void loadAddress() throws Exception {

        AddressDao dao = new AddressDao(RuntimeEnvironment.application);
        List<Address> list = dao.loadAddress(2);
        for (Address a : list) {
            System.out.println(a.getName() + " " + a.getPhone() + " " + a.getPostCode() + " " +
                    a.getAddress_info());
        }
        assertEquals(1, list.size());

    }

}