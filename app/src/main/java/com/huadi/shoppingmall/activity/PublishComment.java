package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.*;
import com.huadi.shoppingmall.util.ImageUtil;


import android.view.View.OnClickListener;
import android.widget.*;
import android.content.Intent;
import android.content.SharedPreferences;


import android.view.View;

import java.util.Date;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class PublishComment extends Activity {

    RatingBar starLevel;      //星级
    EditText commentText;     //评价内容
    ImageView product_image;  //商品图片
    ImageButton Bback;  //返回按钮
    Button publish;  //发表评价按钮

    String orderID;  //订单ID
    String productID;  //商品ID
    String log_user;
    Product product;  //评价的商品
    int starNum;  //获取到的星星数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_publish_evaluation);
        starNum = 0;

        //获取全局的用户ID

        final SharedPreferences settings = getSharedPreferences("setting", 0);
        this.log_user = settings.getString("user_id", "0");

        //获取从商品详情界面传递下来的数据
        Intent intent = getIntent();
        this.orderID = intent.getStringExtra("order_id");

        Log.i("order_id from publish", String.valueOf(orderID));

        starLevel = (RatingBar) findViewById(R.id.publish_evaluation_RatingBar_star);
        commentText = (EditText) findViewById(R.id.publish_evaluation_EditText_comment);

        product_image = (ImageView) findViewById(R.id.publish_evaluation_ImageView_goods);

        Bback = (ImageButton) findViewById(R.id.publish_evaluation_ImageButton_back);
        publish = (Button) findViewById(R.id.publish_evaluation_Button_publish);



        BmobQuery<Order> query = new BmobQuery<>();
        query.getObject(orderID, new QueryListener<Order>() {
            @Override
            public void done(Order order, BmobException e) {
                if (e != null) {
                  BmobQuery<Product> query = new BmobQuery<Product>();
                    query.getObject(order.getProduct_id(), new QueryListener<Product>() {
                        @Override
                        public void done(Product product, BmobException e) {
                            if (e == null) {
                                BmobFile file = product.getImage();
                                file.download(new DownloadFileListener() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            Bitmap bitmap =  ImageUtil.getLoacalBitmap(s);
                                            product_image.setImageBitmap(bitmap);
                                        }
                                    }

                                    @Override
                                    public void onProgress(Integer integer, long l) {

                                    }
                                });
                            }
                        }
                    });

                }
            }
        });



        Bback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(PublishComment.this, MainActivity.class);
                intent3.putExtra("choice", 2);
                startActivity(intent3);
            }
        });

        //注册监听

        publish.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (commentText.getText().toString().length() == 0 || starNum == 0)
                    Toast.makeText(PublishComment.this, "请确认是否已经完成评价",
                            Toast.LENGTH_SHORT).show();
                    //添加评价记录
                else {
                    Comment comment = new Comment();



                    comment.setContent(commentText.getText().toString());
                    comment.setOrder_id(orderID);
                    comment.setProduct_id(productID);
                    comment.setStar_level(starNum);
                    comment.setUser_id(log_user);

                    comment.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                Toast.makeText(PublishComment.this, "保存成功",Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                    Intent intent3 = new Intent(PublishComment.this, MainActivity.class);
                    intent3.putExtra("choice", 0);
                    startActivity(intent3);

                    Order order = new Order();
                    order.setStatus(4);
                    order.update(orderID, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });
                }
            }
        });

        starLevel.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                starNum = (int) starLevel.getRating();
            }
        });

    }
}
