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

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.Address;
import com.huadi.shoppingmall.model.Order;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.model.ShopCar;
import com.huadi.shoppingmall.model.User;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
    private String user_id;
    private Address address;
    private ArrayList<Order> orders;
    private List<Product> products;


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


        products = new ArrayList<>();

        orders = (ArrayList<Order>) getIntent().getSerializableExtra("order");
        Log.i("orders", String.valueOf(orders.size()));

        SharedPreferences settings = getSharedPreferences("setting", 0);
        user_id = settings.getString("user_id", "0");

        Log.i("O", "OK");
        BmobQuery<Address> query = new BmobQuery<>();
        query.addWhereEqualTo("user_id", user_id);
        query.findObjects(new FindListener<Address>() {
            @Override
            public void done(List<Address> list, BmobException e) {
                if (e == null) {
                    Address address = list.get(0);
                    receive_name.setText(address.getName());
                    receive_address.setText(address.getAddress_info());
                    receive_phone.setText(address.getPhone());
                }
            }
        });


        for (final Order order : orders) {
            String product_id = order.getProduct_id();
            Log.i("pay_product_id", String.valueOf(product_id));
            BmobQuery<Product> query1 = new BmobQuery<>();
            query1.getObject(product_id, new QueryListener<Product>() {
                @Override
                public void done(Product product, BmobException e) {
                    if (e == null) {
                        product.setSalNum(order.getNumber());
                        Log.i("py_product_number", String.valueOf(order.getNumber()));
                        totalPrice += product.getPrice() * order.getNumber();
                        products.add(product);
                    }
                }
            });
        }

        final User user = (User) new User().getCurrentUser();

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
                    user.update(user.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });


                    //来自未付款订单
                    if (from.equals("unpay")) {
                        for (int i = 0; i < orders.size(); i++) {
                            Order order = new Order();
                            order.setStatus(1);
                            order.update(orders.get(i).getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
                        }

                    }
                    //直接付款
                    else if (from.equals("pay")) {
                        for (int i = 0; i < orders.size(); i++) {
                            Order order = new Order();
                            order.setNumber(orders.get(i).getNumber());
                            order.setProduct_id(products.get(i).getObjectId());
                            order.setAddress_id(address.getObjectId());
                            order.setStatus(1);
                            order.setPrice(totalPrice);
                            order.setOrder_time(new Date());
                            order.setUser_id(user_id);
                            order.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {

                                }
                            });
                        }
                    }

                    //来自购物车
                    else if (from.equals("shop_car")) {

                        for (int i = 0; i < orders.size(); i++) {

                            Order order = new Order();
                            order.setNumber(orders.get(i).getNumber());
                            Log.i("PayNumber", String.valueOf(orders.get(i).getNumber()));
                            order.setProduct_id(products.get(i).getObjectId());
                            order.setAddress_id(address.getObjectId());
                            order.setStatus(1);
                            order.setPrice(totalPrice);
                            order.setOrder_time(new Date());
                            order.setUser_id(user_id);
                            order.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {

                                }
                            });
                        }
                        ShopCar shopCar = new ShopCar();
                        for (int i = 0; i < orders.size(); i++) {
                            shopCar.delete(orders.get(i).getObjectId(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {

                                }
                            });
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
                    order.setProduct_id(products.get(i).getObjectId());
                    order.setAddress_id(address.getObjectId());
                    order.setStatus(0);
                    order.setPrice(totalPrice);
                    order.setOrder_time(new Date());
                    order.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                        }
                    });

                }

                Intent intent = new Intent(Pay.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
