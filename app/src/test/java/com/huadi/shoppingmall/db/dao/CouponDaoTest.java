package com.huadi.shoppingmall.db.dao;

import com.huadi.shoppingmall.BuildConfig;
import com.huadi.shoppingmall.model.Coupon;
import com.huadi.shoppingmall.model.User;
import com.huadi.shoppingmall.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by smartershining on 16-7-16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)

public class CouponDaoTest {

    CouponDao dao = null;

    @Before
    public void setUp() throws Exception {
        UserDao userDao  = new UserDao(RuntimeEnvironment.application);
        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        userDao.saveUser(user);

        dao = new CouponDao(RuntimeEnvironment.application);

        Coupon coupon = new Coupon();
        coupon.setUser_id(1);
        coupon.setCreate_time(DateUtil.stringToDate("2010-12-30 12:10:08"));
        coupon.setLast(600.0);
        coupon.setCoupon_info("54");
        coupon.setCoupon_sum(23.0);

        assertTrue(dao.saveCoupon(coupon));

        coupon.setUser_id(1);
        coupon.setCreate_time(DateUtil.stringToDate("2014-12-30 12:10:08"));
        coupon.setLast(800.0);
        coupon.setCoupon_info("76");
        coupon.setCoupon_sum(78.0);

        assertTrue(dao.saveCoupon(coupon));


    }

    @Test
    public void saveCoupon() throws Exception {

    }

    @Test
    public void getCoupon() throws Exception {
        List<Coupon> list = dao.getCoupon(1);
        assertEquals(2, list.size());
        System.out.println(list.get(0).getCoupon_sum() +" " + list.get(1).getCoupon_sum());
    }

    @Test
    public void deleteCoupon() throws Exception {
        List<Coupon> list = dao.getCoupon(1);
        assertEquals(2, list.size());
        dao.deleteCoupon(1);
        list = dao.getCoupon(1);
        assertEquals(1,list.size());
    }

}