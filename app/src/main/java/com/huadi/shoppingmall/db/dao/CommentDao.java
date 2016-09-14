package com.huadi.shoppingmall.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Comment;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 评价管理类
 */
public class CommentDao {
    private DataBaseOpenHelper dbOpenHelper;

    public CommentDao(Context context) {
        dbOpenHelper = new DataBaseOpenHelper(context);
    }

    /**
     * 插入一条评价
     * @param comment
     * @return
     */
    public boolean saveComment(Comment comment) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id",comment.getUser_id());
        values.put("order_id",comment.getOrder_id());
        values.put("create_time", DateUtil.dateToString(comment.getCreate_time()));
        values.put("content", comment.getContent());
        values.put("product_id",comment.getProduct_id());
        values.put("start_level",comment.getSatrt_level());

        boolean createSuccessful = db.insert("COMMENT", null, values) > 0;
        db.close();
        return createSuccessful;
    }

    public List<Comment> getCommentList(Cursor cursor) {
        List<Comment> list=  new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Comment comment = new Comment();
                comment.setId(cursor.getInt(cursor.getColumnIndex("id")));
                comment.setUser_id(cursor.getInt(cursor.getColumnIndex("user_id")));
                comment.setCreate_time(DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex("create_time"))));
                comment.setProduct_id(cursor.getInt(cursor.getColumnIndex("product_id")));
                comment.setOrder_id(cursor.getInt(cursor.getColumnIndex("order_id")));
                comment.setContent(cursor.getString(cursor.getColumnIndex("content")));
                comment.setSatrt_level(cursor.getInt(cursor.getColumnIndex("start_level")));
                list.add(comment);

            } while (cursor.moveToNext()) ;
        }
        return list;
    }

    /**
     * 获取一个商品的全部评价信息
     * @param product_id
     * @return
     */
    public List<Comment> getCommentByProductId(Integer product_id) {
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        String sql = "SELECT * FROM COMMENT WHERE PRODUCT_ID=?";
        String[] args = new String[]{String.valueOf(product_id)};
        Cursor cursor = db.rawQuery(sql,args);
        List<Comment> list = getCommentList(cursor);
        cursor.close();
        db.close();
        return list;
    }

    public void deleteComment(Integer id) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "DELETE FROM COMMENT WHERE ID=?";
        String[] args = new String[]{String.valueOf(id)};
        db.execSQL(sql,args);
        db.close();
    }

    /**
     * 更新一条评价
     * @param comment
     */
    public void updateComment(Comment comment) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "UPDATE COMMENT SET START_LEVEL=?, CONTENT=? WHERE ID=?";
        String[] args = new String[]{String.valueOf(comment.getSatrt_level()), comment.getContent(), String.valueOf(comment.getId())};
        db.execSQL(sql,args);
        db.close();
    }


}
