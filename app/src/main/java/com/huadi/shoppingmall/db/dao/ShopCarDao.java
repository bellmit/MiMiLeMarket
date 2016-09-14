package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.ShopCar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 购物车管理类
 */
public class ShopCarDao {

    private DataBaseOpenHelper dbOpenHelper;

    public ShopCarDao(Context context) {
        dbOpenHelper = new DataBaseOpenHelper(context);
    }


    /**
     * 保存新插入的购物车
     * @param shopCar
     * @return
     */
    public boolean saveShopCar(ShopCar shopCar) {
            ContentValues values = new ContentValues();
            values.put("user_id", shopCar.getUser_id());
            values.put("product_id", shopCar.getProduct_id());
            values.put("product_num", shopCar.getProduct_num());
            SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
            boolean createSuccessful = db.insert("SHOP_CAR", null, values) > 0;
            db.close();
            return createSuccessful;
    }

    /**
     * 取出该用户购物车里面的全部信息
     * @param user_id
     * @return
     */

    public List<ShopCar> loadShopCarByUser(Integer user_id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM SHOP_CAR WHERE USER_ID=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(user_id)});
        List<ShopCar> list = getShopCarList(cursor);
        cursor.close();
        db.close();
        return list;
    }

    /**
     * 根据ID删除一条信息
     * @param id
     */
    public void deleteShopCarById(Integer id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "DELETE FROM SHOP_CAR WHERE ID=?";
        String[] args = new String[]{String.valueOf(id)};
        db.execSQL(sql,args);
        db.close();
    }

    /**
     * 删除一个用户购物车里面的全部信息
     * @param user_id
     */
    public void deleteShopCarByUserId(Integer user_id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "DELETE FROM SHOP_CAR WHERE USER_ID=?";
        String[] args = new String[]{String.valueOf(user_id)};
        db.execSQL(sql,args);
        db.close();
    }


    public List<ShopCar> getShopCarList(Cursor cursor) {
        List<ShopCar> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ShopCar car = new ShopCar();
                car.setId(cursor.getInt(cursor.getColumnIndex("id")));
                car.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                car.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
                car.setProduct_num(cursor.getInt(cursor.getColumnIndex("product_num")));
                list.add(car);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 检测购物车里面是否已经有该商品信息
     * @param user_id
     * @param product_id
     * @return
     */
    public boolean findShopCarByUserIdAndProductId(Integer user_id, Integer product_id) {

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String sql = "SELECT * FROM SHOP_CAR WHERE USER_ID=? AND PRODUCT_ID=?";
        String[] args = new String[]{String.valueOf(user_id), String.valueOf(product_id)};
        Cursor cursor = db.rawQuery(sql,args);
        if (cursor.moveToFirst() == true ) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }

    }



}
