package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.huadi.shoppingmall.Adapter.ProductCommentAdapter;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.Comment;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

import static com.huadi.shoppingmall.R.id.product_comment_Button_detail;

public class ProductCommentActivity extends Activity implements OnClickListener {

    private Button butn_detail;
    private ImageView iamge_back;
    private List<Comment> list;
    private ListView listView;
    private ProductCommentAdapter adapter;
    private String product_id;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_product_comment);
        initViews();

        Intent intent = getIntent();
        product_id = intent.getStringExtra("product_id");


        BmobQuery<Comment> query = new BmobQuery<>();
        query.addWhereEqualTo("product_id", product_id);
        query.findObjects(new FindListener<Comment>() {
            @Override
            public void done(List<Comment> objects, BmobException e) {
                if (e == null) {
                    list = objects;
                }
            }
        });

        Log.i("list,size", String.valueOf(list.size()));


        adapter = new ProductCommentAdapter(this, list, R.layout.activity_product_comment_item);
        listView.setAdapter(adapter);
        listView.setDividerHeight(20);
    }

    private void initViews() {

        butn_detail = (Button) findViewById(R.id.product_comment_Button_detail);
        iamge_back = (ImageView) findViewById(R.id.product_comment_ImageButton_back);

        butn_detail.setOnClickListener(this);
        iamge_back.setOnClickListener(this);

        listView = (ListView) findViewById(R.id.product_comment_listView);
    }

    public void onClick(View view) {
        switch (view.getId()) {

            case product_comment_Button_detail:
                Intent intent = new Intent(ProductCommentActivity.this, ProductDetail.class);
                intent.putExtra("product_id", product_id);
                startActivity(intent);
                ProductCommentActivity.this.finish();
                break;

            case R.id.product_comment_ImageButton_back:
                Intent intent1 = new Intent(ProductCommentActivity.this, Category.class);
                startActivity(intent1);
                ProductCommentActivity.this.finish();
                break;
            default:
                break;
        }
    }
}
