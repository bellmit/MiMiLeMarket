package com.huadi.shoppingmall.db.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by smartershining on 16-7-15.
 */

/**
 * 商品管理类
 */
public class ProductDao {

    private DataBaseOpenHelper dbOpenHelper;
    public ProductDao(Context context) {
        dbOpenHelper = new DataBaseOpenHelper(context);
    }

    /**
     * 根据商品时间取出给出的商品数量
     * @param number
     * @return
     */
    public List<Product> loadProductByTime(int number) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM PRODUCT  order by datetime(create_time) desc LIMIT ?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(number)});

        List<Product> list =  getProductList(cursor);
        cursor.close();
        db.close();
        return list;
    }

    public Product getProductById(int product_id) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "select * from product where id=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(product_id)});
        Product product = new Product();
        if ( cursor.moveToNext()) {
            product.setId(cursor.getInt(cursor.getColumnIndex("id")));
            product.setName(cursor.getString(cursor.getColumnIndex("name")));
            product.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
            product.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            product.setImage(cursor.getString(cursor.getColumnIndex("image")));
            product.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
            product.setSalNum(cursor.getInt(cursor.getColumnIndex("salNum")));
            product.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
            product.setCity(cursor.getString(cursor.getColumnIndex("city")));
            product.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
            product.setCateOne(cursor.getString(cursor.getColumnIndex("cateOne")));
            product.setCateTwo(cursor.getString(cursor.getColumnIndex("cateTwo")));
            product.setColor(cursor.getString(cursor.getColumnIndex("color")));
            product.setSize(cursor.getString(cursor.getColumnIndex("size")));
            product.setCreate_time(DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex("create_time"))));

        }
        return product;
    }


    /**
     * 根据商品销量取出商品
     * @param number
     * @param cateOne
     * @param cateTwo
     * @return
     */

    public List<Product> loadProductBySalNum(int number,String cateOne, String cateTwo) {

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        String sql = "select * from product where cateOne=? and cateTwo=? order by salNum desc limit ?";
        Cursor cursor = db.rawQuery(sql, new String[]{cateOne, cateTwo, String.valueOf(number)});
        List<Product> list  = getProductList(cursor);
        cursor.close();
        db.close();
        return list;

    }


    /**
     * 更新商品信息
     * @param product
     */
    public void updateProduct(Product product) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "UPDATE PRODUCT SET NAME=?, PRICE=?, TITLE=?,IMAGE=?,DETAIL=?,salNum=?,STOCK=?,CITY=?,BRAND=?," +
                "cateOne=?,cateTwo=?,COLOR=?,SIZE=?,CREATE_TIME=? WHERE ID=?";
        String[] args = new String[]{
                product.getName(), String.valueOf(product.getPrice()),product.getTitle(),product.getImage(),
                product.getDetail(), String.valueOf(product.getSalNum()), String.valueOf(product.getStock()),product.getCity(),
                product.getBrand(),
                product.getCateOne(),product.getCateTwo(), product.getColor(),product.getSize(),
                DateUtil.dateToString(product.getCreate_time()),String.valueOf(product.getId())};
        db.execSQL(sql,args) ;
        db.close();
        }


    /**
     * 根据分类按价钱取出商品
     * @param cateOne
     * @param cateTwo
     * @param number
     * @return
     */
    public List<Product> loadProductByPrice(String cateOne, String cateTwo, int number) {
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        String sql = "SELECT * FROM PRODUCT where cateOne=? and cateTwo=? order by price desc LIMIT ?";
        Cursor cursor = db.rawQuery(sql, new String[]{cateOne, cateTwo,String.valueOf(number)});
        List<Product> list = getProductList(cursor);
        cursor.close();
        db.close();
        return list;
    }


    public List<Product> getProductList(Cursor cursor) {
        List<Product> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(cursor.getColumnIndex("id")));
                product.setName(cursor.getString(cursor.getColumnIndex("name")));
                product.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                product.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                product.setImage(cursor.getString(cursor.getColumnIndex("image")));
                product.setDetail(cursor.getString(cursor.getColumnIndex("detail")));
                product.setSalNum(cursor.getInt(cursor.getColumnIndex("salNum")));
                product.setStock(cursor.getInt(cursor.getColumnIndex("stock")));
                product.setCity(cursor.getString(cursor.getColumnIndex("city")));
                product.setBrand(cursor.getString(cursor.getColumnIndex("brand")));
                product.setCateOne(cursor.getString(cursor.getColumnIndex("cateOne")));
                product.setCateTwo(cursor.getString(cursor.getColumnIndex("cateTwo")));
                product.setColor(cursor.getString(cursor.getColumnIndex("color")));
                product.setSize(cursor.getString(cursor.getColumnIndex("size")));
                product.setCreate_time(DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex("create_time"))));
                list.add(product);

            }while (cursor.moveToNext());
        }
        return list;

    }


}

