package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huadi.shoppingmall.Adapter.OrderAdapter;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.OrderDao;
import com.huadi.shoppingmall.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-20.
 */

public class OrderActivity extends Activity  implements OrderAdapter.Callback {
    private ListView listView;
    private ImageView back;
    private List<Order> list;
    private int user_id;
    private int statue;
    private OrderAdapter adapter;

    private Button totalPay;
    private int totalPrice = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Log.i("OrderActivity", "is Called");
        init();
        adapter = new OrderAdapter(OrderActivity.this, list, R.layout.activity_order_item, statue, this);
        listView.setAdapter(adapter);
        listView.setDividerHeight(20);


    }

    public void init() {
        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        statue = intent.getIntExtra("statue", 0);

        Log.i("user_id", String.valueOf(user_id));
        Log.i("statue", String.valueOf(statue));

        listView = (ListView) findViewById(R.id.order_listView);
        back = (ImageView) findViewById(R.id.order_title_image);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("choice",2);
                startActivity(intent);

            }
        });
        OrderDao dao = new OrderDao(this.getApplicationContext());
        list = dao.getOrderListByUserIdAndStatus(user_id, statue);
        TextView title = (TextView)findViewById(R.id.order_title_text);
        if (statue == 0) {
            title.setText("待付款");
        } else if (statue == 1) {
            title.setText("待发货");
        }else if (statue == 2) {
            title.setText("待收货");
        } else if (statue == 3) {
            title.setText("待评价");
        }
    }


    @Override
    public void click(View v) {
        View footerView = ((LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.activity_order_footer, null, false);

        View img1 = findViewById(R.id.order_footer_pay);
        ViewGroup.LayoutParams  lp = img1.getLayoutParams();
        Boolean isSomeChecked = false;

        for (int i = 0; i < list.size(); i ++) {
            if (adapter.isChecked[i]) {
               Log.i("position", String.valueOf(i));
               isSomeChecked = true;
                break;
            }
        }

        if (isSomeChecked) {
            lp.height = 100;
        }
        else {
            lp.height = 0;
        }
        img1.setLayoutParams(lp);

        Button allPay = (Button) findViewById(R.id.order_footer_pay);

        allPay.setOnClickListener(new View.OnClickListener() {
            double price = 0 ;
            @Override
            public void onClick(View view) {
                ArrayList<Order> orders = new ArrayList<Order>();
                for (int i = 0; i < list.size(); i++) {
                    if (adapter.isChecked[i]) {
                        orders.add(list.get(i));
                    }
                }
                Intent intent = new Intent(OrderActivity.this, Pay.class);
                intent.putExtra("order", orders);
                intent.putExtra("from","unpay");
                startActivity(intent);
                Log.i("price",String.valueOf(price));
            }
        });
    }
}