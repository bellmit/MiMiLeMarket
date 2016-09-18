package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huadi.shoppingmall.Adapter.PayAdapter;
import com.huadi.shoppingmall.Adapter.ProductAdapter;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.AddressDao;
import com.huadi.shoppingmall.db.dao.OrderDao;
import com.huadi.shoppingmall.db.dao.ProductDao;
import com.huadi.shoppingmall.db.dao.ShopCarDao;
import com.huadi.shoppingmall.db.dao.UserDao;
import com.huadi.shoppingmall.model.Address;
import com.huadi.shoppingmall.model.Order;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.model.User;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by smartershining on 16-7-23.
 */

public class Pay extends Activity {


    private PayAdapter adapter;

    private TextView price;
    private TextView receive_name;
    private TextView receive_phone;
    private TextView receive_address;
    private ListView listView;
    private Button pay;
    private Button cancel;
    private ImageView back;


    private int totalPrice;
    private int user_id;
    private Address address;
    private ArrayList<Order> orders;
    private List<Product> products;
    private OrderDao dao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        initView();
        initData();
    }

    public void initView() {

        price = (TextView) findViewById(R.id.pay_price);
        receive_name = (TextView) findViewById(R.id.pay_name);
        receive_phone = (TextView) findViewById(R.id.pay_phone);
        receive_address = (TextView) findViewById(R.id.pay_address);
        pay = (Button) findViewById(R.id.pay_ack);
        listView = (ListView) findViewById(R.id.pay_listView);
      //  cancel = (Button) findViewById(R.id.pay_cancel);
        back = (ImageView) findViewById(R.id.pay_back);
    }

    public void initData() {

        dao = new OrderDao(Pay.this);
        products = new ArrayList<>();

        orders = (ArrayList<Order>) getIntent().getSerializableExtra("order");
        Log.i("orders", String.valueOf(orders.size()));

        SharedPreferences settings = getSharedPreferences("setting", 0);
        user_id = settings.getInt("user_id", 0);

        Log.i("O", "OK");
        List<Address> addresses = new AddressDao(Pay.this).loadAddress(user_id);
        address = addresses.get(0);

        receive_name.setText(address.getName());
        receive_address.setText(address.getAddress_info());
        receive_phone.setText(address.getPhone());


        for (int i = 0; i < orders.size(); i++) {
            int product_id = orders.get(i).getProduct_id();
            Log.i("pay_product_id", String.valueOf(product_id));
            Product product = new ProductDao(Pay.this).getProductById(product_id);

            product.setSalNum(orders.get(i).getNumber());
            Log.i("py_product_number", String.valueOf(orders.get(i).getNumber()));
            totalPrice += product.getPrice() * orders.get(i).getNumber();
            products.add(product);
        }

        final User user = new UserDao(Pay.this).getUserById(user_id);

        price.setText("总金额:  " + String.valueOf(totalPrice));
        adapter = new PayAdapter(this, products, R.layout.activity_pay_item);
        listView.setAdapter(adapter);
        listView.setDividerHeight(20);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = getIntent().getStringExtra("from");

                if (user.getRemind() > totalPrice) {
                    user.setRemind(user.getRemind() - (int) totalPrice);
                    new UserDao(Pay.this).updateUser(user);


                    //来自未付款订单
                    if (from.equals("unpay")) {
                        for (int i = 0; i < orders.size(); i++) {
                            dao.updateOrder(orders.get(i).getId(), 1);
                        }

                    }
                    //直接付款
                    else if (from.equals("pay")) {
                        for (int i = 0; i < orders.size(); i++) {
                            Order order = new Order();
                            order.setNumber(orders.get(i).getNumber());
                            order.setProduct_id(products.get(i).getId());
                            order.setAddress_id(address.getId());
                            order.setStatus(1);
                            order.setPrice(totalPrice);
                            order.setOrder_time(new Date());
                            order.setUser_id(user_id);
                            dao.saveOrder(order);
                        }
                    }

                    //来自购物车
                    else if (from.equals("shop_car")) {

                        for (int i = 0; i < orders.size(); i++) {

                            Order order = new Order();
                            order.setNumber(orders.get(i).getNumber());
                            Log.i("PayNumber", String.valueOf(orders.get(i).getNumber()));
                            order.setProduct_id(products.get(i).getId());
                            order.setAddress_id(address.getId());
                            order.setStatus(1);
                            order.setPrice(totalPrice);
                            order.setOrder_time(new Date());
                            order.setUser_id(user_id);
                            dao.saveOrder(order);
                        }
                        for (int i = 0; i < orders.size(); i++) {
                            ShopCarDao dao = new ShopCarDao(Pay.this);
                            dao.deleteShopCarById(orders.get(i).getId());
                        }
                    }
                    Toast.makeText(Pay.this, "支付成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Pay.this, MainActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(Pay.this, "余额不足", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Pay.this, "付款失败", Toast.LENGTH_LONG).show();
                String from = getIntent().getStringExtra("from");
                if (from.equals("pay")) {
                    for (int i = 0; i < orders.size(); i++) {
                        Order order = new Order();
                        order.setNumber(orders.get(i).getNumber());
                        order.setUser_id(user_id);
                        order.setProduct_id(products.get(i).getId());
                        order.setAddress_id(address.getId());
                        order.setStatus(0);
                        order.setPrice(totalPrice);
                        order.setOrder_time(new Date());
                        dao.saveOrder(order);
                    }

                } else if (from.equals("shop_car")) {
                    for (int i = 0; i < orders.size(); i++) {
                        Order order = new Order();
                        order.setNumber(orders.get(i).getNumber());
                        Log.i("PayNumber", String.valueOf(orders.get(i).getNumber()));
                        order.setProduct_id(products.get(i).getId());
                        order.setAddress_id(address.getId());
                        order.setStatus(1);
                        order.setPrice(totalPrice);
                        order.setOrder_time(new Date());
                        order.setUser_id(user_id);
                        dao.saveOrder(order);
                    }
                    for (int i = 0; i < orders.size(); i++) {
                        ShopCarDao dao = new ShopCarDao(Pay.this);
                        dao.deleteShopCarById(orders.get(i).getId());
                    }
                }
                Intent intent = new Intent(Pay.this, MainActivity.class);
                startActivity(intent);
            }
        }); */

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Pay.this, "付款失败", Toast.LENGTH_LONG).show();
                String from = getIntent().getStringExtra("from");

                for (int i = 0; i < orders.size(); i++) {
                    Order order = new Order();
                    order.setNumber(orders.get(i).getNumber());
                    order.setUser_id(user_id);
                    order.setProduct_id(products.get(i).getId());
                    order.setAddress_id(address.getId());
                    order.setStatus(0);
                    order.setPrice(totalPrice);
                    order.setOrder_time(new Date());
                    dao.saveOrder(order);

                }

                Intent intent = new Intent(Pay.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
