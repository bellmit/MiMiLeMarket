package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Coupon;
import com.huadi.shoppingmall.model.User;
import com.huadi.shoppingmall.util.DateUtil;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 优惠券管理类
 */
public class CouponDao {

    private DataBaseOpenHelper dbOpenHelper;

    public CouponDao(Context context) {

        dbOpenHelper = new DataBaseOpenHelper(context);

    }

    /**
     * 插入优惠券
     * @param coupon
     * @return
     */
    public boolean saveCoupon(Coupon coupon) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("create_time", DateUtil.dateToString(coupon.getCreate_time()));
        values.put("last",coupon.getLast());
        values.put("user_id",coupon.getUser_id());
        values.put("coupon_info",coupon.getCoupon_info());
        values.put("coupon_sum", coupon.getCoupon_sum());

        boolean createSuccessful = db.insert("COUPON", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    /**
     * 取出一个用户的全部优惠券（按时间先后排序）
     * @param userId
     * @return
     */
    public List<Coupon> getCoupon(Integer userId) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM COUPON WHERE USER_ID=? ORDER BY DATETIME(CREATE_TIME) DESC";
        String[] args = new String[]{String.valueOf(userId)};
        Cursor cursor = db.rawQuery(sql, args);
        List<Coupon> list = getCouponList(cursor);
        cursor.close();
        db.close();
        return list;
    }

    public List<Coupon> getCouponList(Cursor cursor) {
        List<Coupon> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Coupon coupon = new Coupon();
                coupon.setId(cursor.getInt(cursor.getColumnIndex("id")));
                coupon.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                coupon.setCreate_time(DateUtil.stringToDate((cursor.getString(cursor.getColumnIndex("create_time")))));
                coupon.setLast(cursor.getDouble(cursor.getColumnIndex("last")));
                coupon.setCoupon_info(cursor.getString(cursor.getColumnIndex("coupon_info")));
                coupon.setCoupon_sum(cursor.getDouble(cursor.getColumnIndex("coupon_sum")));
                list.add(coupon);
            }while (cursor.moveToNext());
        }
        return list;
    }

    public void deleteCoupon(Integer id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "DELETE FROM COUPON WHERE ID=?";
        String[] args = new String[]{String.valueOf(id)};
        db.execSQL(sql,args);
        db.close();
    }

}
