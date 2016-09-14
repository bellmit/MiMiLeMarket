package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 地址管理类
 */
public class AddressDao {
    private DataBaseOpenHelper dbOpenHelper;

    public AddressDao(Context context) {

        dbOpenHelper = new DataBaseOpenHelper(context);
    }

    /**
     * 添加地址
     * @param address
     * @return
     */
    public boolean saveAddress(Address address) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", address.getUser_id());
        values.put("address_info", address.getAddress_info());
        values.put("phone",address.getPhone());
        values.put("name",address.getName());
        values.put("postcode",address.getPostCode());
        boolean createSuccessful = db.insert("address", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    /**
     * 地址更新
     * @param address
     */
    public void updateAddress(Address address) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String sql = "UPDATE ADDRESS SET ADDRESS_INFO=?,PHONE=?, NAME=?, POSTCODE=? WHERE ID=?";
        String[] args = new String[]{address.getAddress_info(), address.getPhone(),address.getName(),
                address.getPostCode(),String.valueOf(address.getId())};


        db.execSQL(sql,args) ;
        db.close();

    }

    /**
     * 删除地址
     * @param id
     */
    public void deleteAddress(Integer id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "DELETE FROM ADDRESS WHERE ID=?";
        String[] args = new String[]{String.valueOf(id)};
        db.execSQL(sql,args);
        db.close();
    }

    /**
     * 根据用户ID加载地址
     * @param user_id
     * @return
     */
    public List<Address> loadAddress(Integer user_id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        List<Address> list = new ArrayList<>();
        String sql = "SELECT * FROM ADDRESS WHERE USER_ID=?";
        String[] args = new String[]{String.valueOf(user_id)};
        Cursor cursor = db.rawQuery(sql,args);
        if (cursor.moveToFirst()) {
            do {
                Address address = new Address();
                address.setId(cursor.getInt(cursor.getColumnIndex("id")));
                address.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                address.setAddress_info(cursor.getString(cursor.getColumnIndex("address_info")));
                address.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                address.setPostCode(cursor.getString(cursor.getColumnIndex("postcode")));
                address.setName(cursor.getString(cursor.getColumnIndex("name")));
                list.add(address);

            } while (cursor.moveToNext());
        }
        return list;
    }


}
