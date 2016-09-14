package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Discount;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 折扣信息类
 */
public class DiscountDao {

    private DataBaseOpenHelper dbOpenHelper;

    public DiscountDao(Context context) {
        dbOpenHelper = new DataBaseOpenHelper(context);
    }

    /**
     * 添加折扣信息
     * @param discount
     * @return
     */
    public boolean saveDiscount(Discount discount) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("create_time", DateUtil.dateToString(discount.getCreate_time()));
        values.put("last",discount.getLast());
        values.put("product_id", discount.getProduct_id());
        values.put("name",discount.getName());
        values.put("discount_info",discount.getDiscount_info());

        boolean createSuccessful = db.insert("DISCOUNT", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public List<Discount> getDiscountList(Cursor cursor) {
        List<Discount> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Discount discount = new Discount();
                discount.setId(cursor.getInt(cursor.getColumnIndex("id")));
                discount.setName(cursor.getString(cursor.getColumnIndex("name")));
                discount.setLast(cursor.getDouble(cursor.getColumnIndex("last")));
                discount.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
                discount.setCreate_time(DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex("create_time"))));
                discount.setDiscount_info(cursor.getDouble(cursor.getColumnIndex("discount_info")));
                list.add(discount);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<Discount> getDiscount(Integer product_id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM DISCOUNT WHERE PRODUCT_ID=?";
        String[] args = new String[]{String.valueOf(product_id)};
        Cursor cursor = db.rawQuery(sql, args);
        List<Discount> list = getDiscountList(cursor);
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 根据折扣取出制定的折扣信息
     * @param number
     * @return
     */
    public List<Discount> LoadDiscount(Integer number) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM DISCOUNT ORDER BY DISCOUNT_INFO ASC LIMIT ?";
        String[] args = new String[]{String.valueOf(number)};
        Cursor cursor = db.rawQuery(sql,args);
        List<Discount> list = getDiscountList(cursor);
        cursor.close();
        db.close();
        return list;

    }

    public void deleteDiscount(Integer id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "DELETE FROM DISCOUNT WHERE ID=?";
        String[] args = new String[]{String.valueOf(id)};
        db.execSQL(sql,args);
        db.close();
    }

    public boolean findDiscountByProductId(Integer product_id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM DISCOUNT WHERE PRODUCT_ID=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(product_id)});
        if (cursor.moveToFirst() == true ) {
            cursor.close();
            db.close();
            return true;
        }
        else {
            cursor.close();
            db.close();
            return false;
        }
    }

}
