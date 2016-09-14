package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.db.DataBaseOpenHelper;

import com.huadi.shoppingmall.model.User;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 用户管理类
 */
public class UserDao {

    private DataBaseOpenHelper dbOpenHelper;

    public UserDao(Context context) {
            dbOpenHelper = new DataBaseOpenHelper(context);
    }

    /**
     * 用户保存
     * @param user
     * @return
     */
    public boolean saveUser(User user) {

       if (!findUserbyName(user.getName())) {
           SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
           ContentValues values = new ContentValues();

           values.put("name", user.getName());
           values.put("password", user.getPassword());
           values.put("phone", user.getPhone());
           values.put("email", user.getEmail());
           values.put("rank", user.getRank());
           values.put("remind", user.getRemind());
           values.put("point", user.getPoint());

           boolean createSuccessful = db.insert("USER", null, values) > 0;
           db.close();
           return createSuccessful;
       }
        return false;
    }

    /**
     * 处理用户登陆
     * @param name 用户名
     * @param password 密码
     * @return
     */

    public boolean findUserByNameAndPassword(String name, String password) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM USER WHERE NAME=?  AND PASSWORD=?";
        Cursor cursor = db.rawQuery(sql, new String[]{name, password});
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

    /**
     * 检测用户名是否已注册过
     * @param name
     * @return
     */

    public boolean findUserbyName(String name) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String sql = "SELECT * FROM USER WHERE NAME=? ";
        Cursor cursor = db.rawQuery(sql, new String[]{name});
        if (cursor.moveToFirst() == false) {
            cursor.close();
            db.close();
            return false;
        } else {
            cursor.close();
            db.close();
            return true;
        }
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void updateUser(User user) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String sql = "UPDATE USER SET PASSWORD=?,PHONE=?, EMAIL=?, RANK=?, REMIND=?, POINT=? WHERE ID=?";
        String[] args = new String[]{user.getPassword(), user.getPhone(), user.getEmail(),
                String.valueOf(user.getRank()), String.valueOf(user.getRemind()),
                String.valueOf(user.getPoint()), String.valueOf(user.getId())};

        db.execSQL(sql,args) ;
        db.close();

    }

    /**
     * 根据用户ID获取用户
     * @param userId
     * @return
     */
    public User getUserById(Integer userId) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        User user = new User();
        String sql = "SELECT * FROM USER WHERE ID=?";
        String[] args = new String[]{String.valueOf(userId)};
        Cursor cursor = db.rawQuery(sql, args);

        if ( cursor.moveToNext() ) {
            user.setName(cursor.getString(cursor.getColumnIndex("name")));
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            user.setPoint(cursor.getInt(cursor.getColumnIndex("point")));
            user.setRank(cursor.getInt(cursor.getColumnIndex("rank")));
            user.setRemind(cursor.getInt(cursor.getColumnIndex("remind")));
            user.setId(userId);
        }
        cursor.close();
        db.close();
        return user;
    }


    /**
     * 根据用户名获取ID
     * @param userName
     * @return
     */
    public User getUserByName(String userName) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        User user = new User();
        String sql = "SELECT * FROM USER WHERE NAME=?";
        String[] args = new String[]{userName};
        Cursor cursor = db.rawQuery(sql, args);

        if (cursor.moveToNext()) {
            user.setName(userName);
            user.setPassword(cursor.getString(cursor.getColumnIndex("password")));
            user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            user.setPoint(cursor.getInt(cursor.getColumnIndex("point")));
            user.setRank(cursor.getInt(cursor.getColumnIndex("rank")));
            user.setRemind(cursor.getInt(cursor.getColumnIndex("remind")));
            user.setId(cursor.getInt(cursor.getColumnIndex("id")));
        }
        cursor.close();
        db.close();
        return user;
    }
}
