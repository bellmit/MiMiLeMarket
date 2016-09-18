package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.huadi.shoppingmall.Adapter.AddressAdapter;
import com.huadi.shoppingmall.Adapter.CouponAdapter;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.AddressDao;
import com.huadi.shoppingmall.model.Address;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smartershining on 16-7-19.
 */

public class AddressActivity extends Activity {

    private List<Address>  list;
    private ListView       listView;
    private Button         addAddress;
    private ImageView      back;
    private AddressAdapter adapter;
    private int            user_id;
    private AddressDao     dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_browse);
        init();

        adapter = new AddressAdapter(this,list, R.layout.activity_address_browse_item);
        listView.setAdapter(adapter);
        listView.setDividerHeight(20);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("choice", 2);
                startActivity(intent);

            }
        });

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddAddressActivity.class);
                intent.putExtra("isAdd", true);
                startActivity(intent);
            }
        });
    }

    public void init() {
        final SharedPreferences settings = getSharedPreferences("setting", 0);
        user_id = settings.getInt("user_id", 0);
        Log.i("user_id", String.valueOf(user_id));

        listView = (ListView) findViewById(R.id.address_browse_listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("Click","click");

            }
        });
        addAddress = (Button) findViewById(R.id.address_browse_title_add);
        back = (ImageView) findViewById(R.id.address_browse_title_back);
        addAddress = (Button) findViewById(R.id.address_browse_title_add);

        dao = new AddressDao(getApplicationContext());
        list = dao.loadAddress(user_id);


    }

}
