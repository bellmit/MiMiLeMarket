package com.huadi.shoppingmall.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.huadi.shoppingmall.db.dao.ShopCarDao;
import com.huadi.shoppingmall.db.dao.UserDao;
import com.huadi.shoppingmall.model.ShopCar;
import com.huadi.shoppingmall.model.User;

/**
 * Created by smartershining on 16-7-14.cd
 */

public class DataBaseOpenHelper extends SQLiteOpenHelper {

    private final String  CREATE_USER    = "CREATE TABLE USER( id integer primary key autoincrement, " +
            "name text unique not null, password text not null, phone text , email text , " +
            "rank integer not null default 0, remind integer not null default 0," +
            "point integer not null default 0);";

    private final String  CREATE_PRODUCT = "CREATE TABLE PRODUCT(id integer primary key autoincrement, " +
            "name text not null, price real not null, title text not null, image text, detail text,salNum " +
            "integer default 0, stock integer not null, city text, " +
            "brand text, cateOne text, cateTwo text, color text, size text, create_time text);";

    private final String  CREATE_SHOPCAR = "CREATE TABLE SHOP_CAR(id integer primary key autoincrement, " +
            "user_id integer not null, product_id integer not null, product_num integer not null," +
            "FOREIGN KEY(user_id) REFERENCES USER(id), FOREIGN KEY(product_id) REFERENCES PRODUCT(id));";

    private final String  CREATE_ADDRESS = "CREATE TABLE ADDRESS(id integer primary key autoincrement, " +
            "user_id integer not null, address_info text not null, phone text not null, postcode text not null, " +
            "name text not null, FOREIGN KEY(user_id) REFERENCES USER(id));";

    private final String  CREATE_COUPON = "CREATE TABLE COUPON(id integer primary key autoincrement, " +
            "create_time text not null, last real not null, user_id integer not null, coupon_sum real not null," +
            "coupon_info text not null, FOREIGN KEY(user_id) REFERENCES USER(id));";

    private final String  CREATE_DISCOUNT = "CREATE TABLE DISCOUNT(id integer primary key autoincrement, " +
            "name text not null, discount_info real not null, create_time text not null, last real not null, " +
            "product_id integer not null, FOREIGN KEY(product_id) REFERENCES PRODUCT(id));";

    private final String  CREATE_ORDER = "CREATE TABLE ORDER_TB(id integer primary key autoincrement, " +
            "user_id integer not null, order_time text not null, status integer not null,price real not null, " +
            "address_id integer not null, product_id integer not null, number integer not null, " +
            "FOREIGN KEY(user_id) REFERENCES USER(id), FOREIGN KEY(address_id) REFERENCES ADDRESS(id), " +
            "FOREIGN KEY(product_id) REFERENCES PRODUCT(id));";


    private final String  CREATE_COMMENT= "CREATE TABLE COMMENT(id integer primary key autoincrement, " +
            "user_id integer not null, order_id integer not null, create_time text not null, content text not null, start_level integer not null," +
            "product_id integer not null,  FOREIGN KEY(product_id) REFERENCES PRODUCT(id),FOREIGN KEY(user_id) REFERENCES USER(id)," +
            " FOREIGN KEY(order_id) REFERENCES ORDER_TB(id));";


    private final String  CREATE_EXPRESS = "CREATE TABLE EXPRESS(id integer primary key autoincrement, " +
            "order_id integer not null, create_time text not null, location text not null, " +
            "FOREIGN KEY(order_id) REFERENCES ORDER_TB(id)); ";




    /*
    数据库名
     */
    private static final String DB_NAME = "shopping_mall";

    /**
     * 数据库版本
     */
    private static final int version = 1;

    private Context context;

    public DataBaseOpenHelper(Context context) {

        super(context, DB_NAME,null, version);
        this.context = context;

    }


    /**
     * 数据库初始化
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_PRODUCT);
        db.execSQL(CREATE_SHOPCAR);
        db.execSQL(CREATE_ADDRESS);
        db.execSQL(CREATE_COUPON);
        db.execSQL(CREATE_DISCOUNT);
        db.execSQL(CREATE_ORDER);
        db.execSQL(CREATE_COMMENT);
        db.execSQL(CREATE_EXPRESS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
