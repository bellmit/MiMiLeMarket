package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Order;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 订单管理
 */
public class OrderDao {

    private DataBaseOpenHelper dbOpenHelper;

    public OrderDao(Context context) {
        dbOpenHelper = new DataBaseOpenHelper(context);
    }

    /**
     * 插入订单
     * @param order
     * @return
     */
    public boolean saveOrder(Order order) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id",order.getUser_id());
        values.put("order_time", DateUtil.dateToString(order.getOrder_time()));
        values.put("product_id",order.getProduct_id());
        values.put("address_id",order.getAddress_id());
        values.put("number",order.getNumber());
        values.put("price",order.getPrice());
        values.put("status",order.getStatus());

        boolean createSuccessful = db.insert("ORDER_TB", null, values) > 0;
        db.close();
        return createSuccessful;
    }




    public List<Order> getOrderList(Cursor cursor) {
        List<Order> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(cursor.getInt(cursor.getColumnIndex("id")));
                order.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
                order.setAddress_id(cursor.getInt(cursor.getColumnIndex("address_id")));
                order.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                order.setNumber(cursor.getInt(cursor.getColumnIndex("number")));
                order.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                order.setOrder_time(DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex("order_time"))));
                order.setStatus(cursor.getInt(cursor.getColumnIndex("status")));
                list.add(order);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 根据用户ID和订单状态取出订单
     * @param user_id
     * @param status
     * @return
     */
    public List<Order> getOrderListByUserIdAndStatus(Integer user_id, Integer status) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String sql = "SELECT * FROM ORDER_TB WHERE USER_ID=? AND STATUS=?";
        String[] args = new String[]{String.valueOf(user_id), String.valueOf(status)};
        Cursor cursor = db.rawQuery(sql,args);
        List<Order> list = getOrderList(cursor);
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 订单更新（只允许更新订单状态）
     * @param
     */
    public void updateOrder(int id, int statue) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "UPDATE ORDER_TB SET STATUS=? WHERE ID=?";
        String[] args = new String[]{String.valueOf(statue), String.valueOf(id)};
        db.execSQL(sql,args);
        db.close();

    }

    /**
     * 删除订单
     */
    public void deleteOrder(int order_id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "DELETE FROM ORDER_TB WHERE ID=?";
        String[] args = new String[]{String.valueOf(order_id)};
        db.execSQL(sql,args);
        db.close();
    }
}
