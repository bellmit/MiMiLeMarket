package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.huadi.shoppingmall.Adapter.CouponAdapter;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.Coupon;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by smartershining on 16-7-19.
 */

public class CouponActivity extends Activity {

    private ListView listView;
    private CouponAdapter adapter;
    private List<com.huadi.shoppingmall.model.Coupon> list;
    private String user_id;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupon);
        init();



    }

    public void init() {
        listView = (ListView) findViewById(R.id.my_coupon_listView);
        SharedPreferences settings = getSharedPreferences("setting", 0);
        user_id = settings.getString("user_id", "0");
        Log.i("CouponUser_id", String.valueOf(user_id));

        back = (ImageView) findViewById(R.id.my_coupon_title_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("choice", 2);
                startActivity(intent);
            }
        });

        BmobQuery<Coupon> query = new BmobQuery<>();
        query.addWhereEqualTo("user_id",user_id);
        query.findObjects(new FindListener<Coupon>() {
            @Override
            public void done(List<Coupon> list, BmobException e) {
                if (e == null) {
                    adapter = new CouponAdapter(CouponActivity.this, list, R.layout.activity_my_coupon_item);
                    listView.setAdapter(adapter);
                    listView.setDividerHeight(20);
                }
            }
        });
    }
}
