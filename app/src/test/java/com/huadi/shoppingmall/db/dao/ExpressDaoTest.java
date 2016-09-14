package com.huadi.shoppingmall.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.BuildConfig;
import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Address;
import com.huadi.shoppingmall.model.Express;
import com.huadi.shoppingmall.model.Order;
import com.huadi.shoppingmall.model.User;
import com.huadi.shoppingmall.util.DateUtil;

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

public class ExpressDaoTest {

    ExpressDao expressDao = null;

    @Before
    public void setUp() throws Exception {

        DataBaseOpenHelper helper = new DataBaseOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = helper.getWritableDatabase();

        String sql = "insert into PRODUCT(name,price,title,image,detail,salNum, stock,city,brand," +
                "cateOne,cateTwo,color,size,create_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String[] args = new String[]{"cloth", String.valueOf(21.8), "qq", "/usr/cai","cdddd", String.valueOf(50),
                String.valueOf(20), "cd","ad","1","2","red","M", "2016-12-30 12:10:04"};

        db.execSQL(sql,args);

        String sql2 = "insert into PRODUCT(name,price,title,image,detail,salNum, stock,city,brand, " +
                "cateOne,cateTwo,color,size,create_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        String[] args2 = new String[]{"cloth", String.valueOf(27.8), "qq", "/usr/cai","cdddd", String.valueOf(10),
                String.valueOf(20), "cd","ad","1","2","red","M", "2010-12-30 12:10:08"};

        db.execSQL(sql2,args2);


        UserDao userDao  = new UserDao(RuntimeEnvironment.application);
        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        assertTrue(userDao.saveUser(user));

        AddressDao addressDao = new AddressDao(RuntimeEnvironment.application);
        Address address = new Address();
        address.setName("smartershining");
        address.setAddress_info("xinanjiaotongdaxue");
        address.setPostCode("408300");
        address.setPhone("18482188024");
        address.setUser_id(1);
        assertTrue(addressDao.saveAddress(address));
        OrderDao dao = new OrderDao(RuntimeEnvironment.application);

        Order order = new Order();
        order.setUser_id(1);
        order.setProduct_id(1);
        order.setAddress_id(1);
        order.setPrice(100);
        order.setNumber(2);
        order.setStatus(1);
        order.setOrder_time(DateUtil.stringToDate("2010-12-30 12:10:08"));
        assertTrue(dao.saveOrder(order));

        order = new Order();
        order.setUser_id(1);
        order.setProduct_id(2);
        order.setAddress_id(1);
        order.setPrice(100);
        order.setNumber(2);
        order.setStatus(1);
        order.setOrder_time(DateUtil.stringToDate("2010-12-30 12:10:08"));
        assertTrue(dao.saveOrder(order));
        expressDao = new ExpressDao(RuntimeEnvironment.application);

        Express express = new Express();
        express.setOrder_id(1);
        express.setCreate_time(DateUtil.stringToDate("2010-12-30 12:10:08"));
        express.setLocation("成都");
        assertTrue(expressDao.saveExpress(express));

        express = new Express();
        express.setOrder_id(1);
        express.setCreate_time(DateUtil.stringToDate("2011-12-30 12:10:08"));
        express.setLocation("武汉");
        assertTrue(expressDao.saveExpress(express));

    }

    @Test
    public void saveExpress() throws Exception {


    }

    @Test
    public void getExpressByOrder_id() throws Exception {

        List<Express> list = expressDao.getExpressByOrder_id(1);
        assertEquals(2,list.size());
        System.out.println(list.get(0).getLocation() +" " + list.get(1).getLocation());

    }

}