package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Express;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 物流管理类
 */
public class ExpressDao {

    private DataBaseOpenHelper dbOpenHelper;
    public ExpressDao(Context context) {
        dbOpenHelper = new DataBaseOpenHelper(context);
    }

    /**
     * 插入一条物流信息
     * @param express
     * @return
     */
    public boolean saveExpress(Express express) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("order_id", express.getOrder_id());
        values.put("create_time", DateUtil.dateToString(express.getCreate_time()));
        values.put("location",express.getLocation());

        boolean createSuccessful = db.insert("EXPRESS", null, values) > 0;
        db.close();
        return createSuccessful;
    }


    public void updateExpress(Express express) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "UPDATE EXPRESS SET LOCATION=? WHERE ID=?";
        String[] args = new String[]{express.getLocation(), String.valueOf(express.getId())};
        db.execSQL(sql,args);
        db.close();
    }


    public List<Express> getExpressList(Cursor cursor) {
        List<Express> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Express express = new Express();
                express.setId(cursor.getInt(cursor.getColumnIndex("id")));
                express.setOrder_id(cursor.getInt(cursor.getColumnIndex("order_id")));
                express.setCreate_time(DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex("create_time"))));
                express.setLocation(cursor.getString(cursor.getColumnIndex("location")));
                list.add(express);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 根据订单取出物流表全部信息
     * @param order_id
     * @return
     */
    public List<Express> getExpressByOrder_id(Integer order_id) {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String sql = "SELECT * FROM EXPRESS WHERE ORDER_ID=? ORDER BY DATETIME(CREATE_TIME) DESC";
        String[] args = new String[]{String.valueOf(order_id)};
        Cursor cursor = db.rawQuery(sql,args);
        List<Express> list = getExpressList(cursor);
        cursor.close();
        db.close();
        return list;
    }


}
