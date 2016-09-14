package com.huadi.shoppingmall.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.BuildConfig;
import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Discount;
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

public class DiscountDaoTest {

    DiscountDao dao = null;

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
        dao = new DiscountDao(RuntimeEnvironment.application);

        Discount discount = new Discount();
        discount.setName("ad");
        discount.setDiscount_info(7.6);
        discount.setCreate_time(DateUtil.stringToDate("2012-12-30 12:10:08"));
        discount.setLast(600.0);
        discount.setProduct_id(1);
        assertTrue(dao.saveDiscount(discount));

        discount.setName("ad");
        discount.setDiscount_info(7.8);
        discount.setCreate_time(DateUtil.stringToDate("2012-12-30 12:10:08"));
        discount.setLast(600.0);
        discount.setProduct_id(2);
        assertTrue(dao.saveDiscount(discount));
    }

    @Test
    public void saveDiscount() throws Exception {



    }

    @Test
    public void getDiscount() throws Exception {
        List<Discount> list = dao.getDiscount(2);
        assertEquals(1, list.size());
        System.out.println(list.get(0).getDiscount_info());


    }

    @Test
    public void deleteDiscount() throws Exception {

    }

    @Test
    public void  LoadDiscount() throws Exception {
        List<Discount> list = dao.LoadDiscount(2);
        assertEquals(2,list.size());
        System.out.println(list.get(0).getDiscount_info() +" " +  list.get(1).getDiscount_info());
    }

}