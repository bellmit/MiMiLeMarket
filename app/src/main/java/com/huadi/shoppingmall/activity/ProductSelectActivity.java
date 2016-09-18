package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.ProductDao;
import com.huadi.shoppingmall.db.dao.ShopCarDao;
import com.huadi.shoppingmall.model.Order;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.model.ShopCar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-23.
 */

public class ProductSelectActivity extends Activity {

    private ImageView image;
    private TextView price;
    private TextView stock;
    private EditText number;
    private int product_id;
    private Button ack;
    private ProductDao dao;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_select);
        init();
    }

    public void init() {
        image = (ImageView) findViewById(R.id.product_select_image);
        number = (EditText) findViewById(R.id.product_select_number);
        price = (TextView) findViewById(R.id.product_select_price);
        stock = (TextView) findViewById(R.id.product_select_stock);
        ack = (Button) findViewById(R.id.product_select_ack);

        Intent intent = getIntent();
        product_id = intent.getIntExtra("product_id", 0);
        dao = new ProductDao(this);
        product = dao.getProductById(product_id);

        int resId = this.getResources().getIdentifier(product.getImage(), "drawable", getPackageName());
        image.setImageResource(resId);

        price.setText(String.valueOf(product.getPrice()));
        stock.setText(String.valueOf(product.getStock()));

        ack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String action = getIntent().getStringExtra("action");
                Log.i("action", action);

                if (number.getText().length() == 0) {
                    Toast.makeText(ProductSelectActivity.this, "请输入数量", Toast.LENGTH_SHORT).show();
                } else if (Integer.parseInt(number.getText().toString()) > product.getStock()) {
                    Toast.makeText(ProductSelectActivity.this, "请输入小于库存的数量", Toast.LENGTH_SHORT).show();
                } else if (action.equals("add_car")) {
                    int orderNum = Integer.parseInt(number.getText().toString());
                    Log.i("orderNum", String.valueOf(orderNum));
                    ShopCar car = new ShopCar();
                    car.setProduct_num(product_id);
                    car.setProduct_num(Integer.parseInt(number.getText().toString()));
                    final SharedPreferences settings = getSharedPreferences("setting", 0);
                    int user_id = settings.getInt("user_id", 0);
                    car.setUser_id(user_id);
                    ShopCarDao dao = new ShopCarDao(ProductSelectActivity.this);
                    dao.saveShopCar(car);
                    Toast.makeText(ProductSelectActivity.this, "成功加入购物车", Toast.LENGTH_SHORT).show();
                } else if (action.equals("buy")) {
                    int orderNum = Integer.parseInt(number.getText().toString());


                    Intent intent = new Intent( getApplicationContext(), Pay.class);

                    ArrayList<Order> list = new ArrayList<Order>();
                    Order order = new Order();

                    order.setProduct_id(product_id);
                    order.setNumber(orderNum);
                    list.add(order);
                    intent.putExtra("order", list);
                    intent.putExtra("from","pay");

                    Log.i("Selectorder",String.valueOf(order.getNumber()));
                    Log.i("Selectorder", String.valueOf(order.getProduct_id()));

                    startActivity(intent);
                }

            }
        });
    }
}