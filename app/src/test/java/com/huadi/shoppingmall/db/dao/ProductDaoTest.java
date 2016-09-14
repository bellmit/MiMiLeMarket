package com.huadi.shoppingmall.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.BuildConfig;
import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Express;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.util.DateUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by smartershining on 16-7-16.
 */


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21)

public class ProductDaoTest {
    DataBaseOpenHelper helper = new DataBaseOpenHelper(RuntimeEnvironment.application);
    SQLiteDatabase db = helper.getWritableDatabase();
    ProductDao dao = null;

    @Before
    public void setUp() throws Exception {
        String sql ="INSERT INTO PRODUCT(name,price,title,image,detail,salNum,stock,city,brand,CateOne,CateTwo,CREATE_TIME,Color,Size) values('衣贝洁洗衣',89.9,'【衣贝洁】羽绒服4件随心洗上门洗衣服务','img20160721111.png','羽绒服4件随心洗在线洗衣上门收件专业服务干洗水洗在线预约',850,5240,'成都','衣贝洁','生活服务','家庭保洁','2016-07-21 13:43:50','蓝','large')";
        db.execSQL(sql);
        dao = new ProductDao(RuntimeEnvironment.application);
    }

    @Test
    public void loadProductByTime() throws Exception {


        List<Product> list = dao.loadProductByTime(2);
        Product product = list.get(0);
        System.out.println(product.getCreate_time());
        System.out.println(list.get(1).getCreate_time());
        assertEquals(2,list.size());

    }

    @Test
    public void loadProductBySalNum() throws Exception {
        List<Product> list = dao.loadProductBySalNum(2,"1","2");
        Product product = list.get(0);
        System.out.println(product.getSalNum());
        System.out.println(list.get(1).getSalNum());
        assertEquals(2,list.size());


    }

    @Test
    public void updateProduct() throws Exception {
        List<Product> list = dao.loadProductBySalNum(2,"1","2");
        Product product = list.get(0);
        System.out.println(product.getSalNum());
        product.setSalNum(70);
        dao.updateProduct(product);

        list = dao.loadProductBySalNum(2,"1","2");
        Product product1 = list.get(0);
        System.out.println(product1.getSalNum());


    }

    @Test
    public void loadProductByPrice() throws Exception {
        List<Product> list = dao.loadProductByPrice("生活服务","家庭保洁", 1);
        assertEquals(1,list.size());
        System.out.println(list.get(0).getPrice() +" " + list.get(0).getId());


    }

    @Test
    public void getProductById() throws Exception {
        Product product = dao.getProductById(1);
        assertNotNull(product);
        System.out.println(product.getName());
    }
}