package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.huadi.shoppingmall.Adapter.LogisticsAdapter;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.Express;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by smartershining on 16-7-22.
 */

public class LogisticsActivity extends Activity {
    private List<Express> list = new ArrayList<Express>();
    private String order_id;
    private TextView number;
    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logistics_info);
        initLogistics(); // 初始化物流数据

        number = (TextView) findViewById(R.id.logistics_info_tab_order_number);
        back = (Button) findViewById(R.id.logistics_info_ImageButton_back);
        number.setText("订单号 137472773020" + order_id);
        LogisticsAdapter adapter = new LogisticsAdapter(this,
                R.layout.activity_logistics_info_item, list);
        ListView listView = (ListView) findViewById(R.id.logistics_info_listview);
        listView.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogisticsActivity.this, MainActivity.class);
                intent.putExtra("choice", 2);
                startActivity(intent);
            }
        });
    }

    private void initLogistics() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra("order_id");

        BmobQuery<Express> query = new BmobQuery<>();
        query.addWhereEqualTo("order_id", order_id);
        query.findObjects(new FindListener<Express>() {
            @Override
            public void done(List<Express> objects, BmobException e) {
                if (e == null) {
                    list = objects;
                }
            }
        });

    }
}
