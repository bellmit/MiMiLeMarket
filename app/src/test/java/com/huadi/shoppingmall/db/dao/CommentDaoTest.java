package com.huadi.shoppingmall.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.BuildConfig;
import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Address;
import com.huadi.shoppingmall.model.Comment;
import com.huadi.shoppingmall.model.Order;
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

public class CommentDaoTest {

    CommentDao dao = null;
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
        OrderDao orderdao = new OrderDao(RuntimeEnvironment.application);

        Order order = new Order();
        order.setUser_id(1);
        order.setProduct_id(1);
        order.setAddress_id(1);
        order.setPrice(100);
        order.setNumber(2);
        order.setStatus(1);
        order.setOrder_time(DateUtil.stringToDate("2010-12-30 12:10:08"));
        assertTrue(orderdao.saveOrder(order));

        order = new Order();
        order.setUser_id(1);
        order.setProduct_id(2);
        order.setAddress_id(1);
        order.setPrice(100);
        order.setNumber(2);
        order.setStatus(1);
        order.setOrder_time(DateUtil.stringToDate("2010-12-30 12:10:08"));
        assertTrue(orderdao.saveOrder(order));

        dao = new CommentDao(RuntimeEnvironment.application);

        Comment comment = new Comment();
        comment.setSatrt_level(1);
        comment.setOrder_id(1);
        comment.setContent("good");
        comment.setCreate_time(DateUtil.stringToDate("2010-12-30 12:10:08"));
        comment.setProduct_id(1);
        comment.setUser_id(1);
        assertTrue(dao.saveComment(comment));


        comment.setSatrt_level(1);
        comment.setOrder_id(1);
        comment.setContent("good");
        comment.setCreate_time(DateUtil.stringToDate("2010-12-30 12:10:08"));
        comment.setProduct_id(2);
        comment.setUser_id(1);
        assertTrue(dao.saveComment(comment));



    }

    @Test
    public void saveComment() throws Exception {





    }

    @Test
    public void getCommentByProductId() throws Exception {
        List<Comment> list = dao.getCommentByProductId(1);
        assertEquals(2, list.size());
    }

    @Test
    public void deleteComment() throws Exception {
        List<Comment> list = dao.getCommentByProductId(1);
        assertEquals(2, list.size());

        dao.deleteComment(1);

        list = dao.getCommentByProductId(1);
        assertEquals(1, list.size());

    }

    @Test
    public void updateComment() throws Exception {
        List<Comment> list = dao.getCommentByProductId(1);
        Comment comment = list.get(0);
        comment.setSatrt_level(3);
        comment.setContent("ming");

        dao.updateComment(comment);
        list = dao.getCommentByProductId(1);
        System.out.println(list.get(0).getContent());


    }

}