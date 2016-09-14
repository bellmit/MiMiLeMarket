package com.huadi.shoppingmall.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.BuildConfig;
import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.ShopCar;
import com.huadi.shoppingmall.model.User;

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

public class ShopCarDaoTest {
    ShopCarDao dao = null;

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setName("admin");
        user.setPassword("admin");
        UserDao userdao = new UserDao(RuntimeEnvironment.application);
        assertTrue(userdao.saveUser(user));


        DataBaseOpenHelper helper = new DataBaseOpenHelper(RuntimeEnvironment.application);
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into PRODUCT(name,price,title,image,detail,salNum, stock,city,brand," +
                "cateOne,cateTwo,color,size,create_time) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        String[] args = new String[]{"cloth", String.valueOf(21.8), "qq", "/usr/cai","cdddd", String.valueOf(50),
                String.valueOf(20), "cd","ad","1","2","red","M", "2016-12-30 12:10:04"};
        db.execSQL(sql,args);

        dao = new ShopCarDao(RuntimeEnvironment.application);
        ShopCar shopCar = new ShopCar();
        shopCar.setUser_id(1);
        shopCar.setProduct_id(1);
        shopCar.setProduct_num(20);
        assertTrue(dao.saveShopCar(shopCar));
    }

    @Test
    public void saveShopCar() throws Exception {
        ShopCar shopCar = new ShopCar();
        shopCar.setUser_id(1);
        shopCar.setProduct_id(1);
        shopCar.setProduct_num(30);
        assertTrue(dao.saveShopCar(shopCar));
    }

    @Test
    public void loadShopCarByUser() throws Exception {
        List<ShopCar> list = dao.loadShopCarByUser(1);
        assertEquals(1,list.size());
        System.out.println(list.get(0).getProduct_num() + " " + list.get(0).getProduct_id());

    }

    @Test
    public void deleteShopCarById() throws Exception {
        List<ShopCar> list = dao.loadShopCarByUser(1);
        assertEquals(1,list.size());
        dao.deleteShopCarById(1);
        list = dao.loadShopCarByUser(1);
        assertEquals(0,list.size());
    }

    @Test
    public void deleteShopCarByUserId() throws Exception {
        List<ShopCar> list = dao.loadShopCarByUser(1);
        assertEquals(1,list.size());
        dao.deleteShopCarByUserId(1);
        list = dao.loadShopCarByUser(1);
        assertEquals(0,list.size());

    }

    @Test
    public void getShopCarList() throws Exception {

    }
    @Test
    public void findShopCarByUserIdAndProductId() throws Exception {
        assertTrue(dao.findShopCarByUserIdAndProductId(1,1));
    }

}