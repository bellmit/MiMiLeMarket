package com.huadi.shoppingmall.activity;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.AddressDao;
import com.huadi.shoppingmall.db.dao.OrderDao;
import com.huadi.shoppingmall.db.dao.ProductDao;
import com.huadi.shoppingmall.model.*;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.*;
import android.widget.RadioGroup.OnCheckedChangeListener;

import java.util.Date;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import android.app.Fragment.SavedState;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.db.*;

//点击立即购买后进入的结算页面
public class OrderConfirm extends Activity {


    private int  productId;       //从购买页面传过来的商品ID
    private int  userId;

    private Product product;
    private Order order;      //提交时生成的订单
    private int order_num;    //购买数量
    private int address_id;      //存储获取到的地址ID


    private ImageView back;  //返回按钮


    private ImageView  product_image;  //商品图片
    private TextView   product_name;  //商品名称
    private TextView   product_price;  //商品价格
    private TextView   product_num;  //购买数量
    private TextView   total_price;  //总价
    private Button     submit;   //提交订单按钮

    private TextView name;
    private TextView phone;
    private TextView address_info;
    private TextView stock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_order_confirm);

        //获取从购买选择界面传递下来的商品ID和购买数量

        Log.i("orderConfirm","isCreate");
        Intent intent = getIntent();

        productId = intent.getIntExtra("product_id", 0);
        this.order_num = intent.getIntExtra("order_number", 0);


        Log.i("product_id", String.valueOf(productId));
        Log.i("order_number", String.valueOf(order_num));

        product = new ProductDao(OrderConfirm.this).getProductById(this.productId);

        //获取从地址选择界面传递下来的地址
        //获取全局的用户ID
        final SharedPreferences settings = getSharedPreferences("settings", 0);
        this.userId = settings.getInt("user_id", 0);

        userId = 1;
        AddressDao dao = new AddressDao(this);
        final List<Address> list = dao.loadAddress(userId);

        back = (ImageView) findViewById(R.id.order_confirm_back_butn);
        product_image = (ImageView) findViewById(R.id.order_confirm_goods_image);
        product_name = (TextView) findViewById(R.id.order_confirm_goods_name);
        product_price = (TextView) findViewById(R.id.order_confirm_goods_price);
        product_num = (TextView) findViewById(R.id.order_confirm_goods_num);

        total_price = (TextView) findViewById(R.id.order_confirm_cost_sum);
        submit = (Button) findViewById(R.id.order_confirm_butn_confr);

        name = (TextView) findViewById(R.id.order_confirm_name);
        phone = (TextView) findViewById(R.id.order_confirm_phone);
        address_info = (TextView) findViewById(R.id.order_confirm_address_info);
        stock = (TextView) findViewById(R.id.purchase_confirm_TextView_save);


        name.setText(list.get(0).getName());
        phone.setText(list.get(0).getPhone());
        address_info.setText(list.get(0).getAddress_info());



        //设置商品图片

        int resId = getResources().getIdentifier(product.getImage(), "drawable", getPackageName());
        product_image.setImageResource(resId);

        //设置商品名
        product_name.setText(this.product.getName());

        //设置价格
        product_price.setText(" " + String.valueOf(product.getPrice()));

        //设置订单数量


       product_num.setText(" " + String.valueOf(order_num));

        //设置总价
        total_price.setText(String.valueOf((this.product.getPrice()) * this.order_num));


        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date();
                Order order = new Order();

                order.setOrder_time(date);
                order.setStatus(0);
                order.setPrice(product.getPrice() * order_num);
                order.setNumber(order_num);
                order.setAddress_id(list.get(0).getId());
                order.setUser_id(userId);
                order.setProduct_id(productId);

                new OrderDao(OrderConfirm.this).saveOrder(order);

                Intent intent = new Intent(OrderConfirm.this, Pay.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("order", order);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
    }
}
