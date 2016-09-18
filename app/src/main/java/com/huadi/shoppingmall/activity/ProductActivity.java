package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.huadi.shoppingmall.Adapter.ProductAdapter;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.db.dao.ProductDao;
import com.huadi.shoppingmall.model.Product;

import java.util.List;


/**
 * Created by smartershining on 16-7-22.
 */

public class ProductActivity extends Activity {
    private ListView listView;
    private ProductAdapter adapter;
    private List<Product> list;
    private int user_id;
    private ProductDao dao;
    private ImageView back;
    private String cateOne;
    private String cateTwo;
    private TextView search;
    private Button   query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_browse);
        init();

        Log.i("onCreate", "ProductActivity onCreate");
        adapter = new ProductAdapter(this,list, R.layout.activity_product_browse_item);
        listView.setAdapter(adapter);
        listView.setDividerHeight(20);

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("click", "query click");
                String content = search.getText().toString();
                ProductDao dao = new ProductDao(ProductActivity.this);
                DataBaseOpenHelper helper = new DataBaseOpenHelper(ProductActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.query("PRODUCT", null, "name like '%" + content + "%'", null, null, null, null);
                list = dao.getProductList(cursor);
                adapter.notifyDataSetChanged();

            }
        });

    }
    public void init() {
        listView = (ListView) findViewById(R.id.product_browse_listView);
        search  = (TextView) findViewById(R.id.goods_browse_edt_serach);
        query = (Button) findViewById(R.id.product_browse_title_Button_serach);

        SharedPreferences settings = getSharedPreferences("setting", 0);

        Intent intent = getIntent();
        cateOne = intent.getStringExtra("cateOne");
        cateTwo = intent.getStringExtra("cateTwo");

        //cateTwo = "家庭保洁";
        //cateOne = "生活服务";

        Log.i("cateOne", cateOne);
        Log.i("cateTwo", cateTwo);



        dao = new ProductDao(getApplicationContext());
        list = dao.loadProductByPrice(cateOne,cateTwo,10);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               int product_id = list.get(i).getId();
               Intent intent = new Intent(ProductActivity.this , ProductDetail.class);
               intent.putExtra("product_id", product_id);
               startActivity(intent);

           }
       });

        back = (ImageView) findViewById(R.id.product_browse_button_back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, Category.class);
                startActivity(intent);
            }
        });



    }
}
