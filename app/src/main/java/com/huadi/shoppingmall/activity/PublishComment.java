package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.CommentDao;
import com.huadi.shoppingmall.db.dao.OrderDao;
import com.huadi.shoppingmall.db.dao.ProductDao;
import com.huadi.shoppingmall.model.*;
import com.huadi.shoppingmall.util.DateUtil;


import android.view.View.OnClickListener;
import android.widget.*;
import android.content.Intent;
import android.content.SharedPreferences;


import android.view.View;

import java.util.Date;

public class PublishComment extends Activity {

    RatingBar starLevel;      //星级
    EditText commentText;     //评价内容
    ImageView product_image;  //商品图片
    ImageButton Bback;  //返回按钮
    Button publish;  //发表评价按钮

    int orderID;  //订单ID
    int productID;  //商品ID
    int log_user;
    Product product;  //评价的商品
    ProductDao product_OP;  //商品操作对象
    OrderDao order_OP;  //订单操作对象
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
        this.log_user = settings.getInt("user_id", 0);

        //获取从商品详情界面传递下来的数据
        Intent intent = getIntent();
        this.orderID = intent.getIntExtra("order_id", 0);

        Log.i("order_id from publish", String.valueOf(orderID));

        starLevel = (RatingBar) findViewById(R.id.publish_evaluation_RatingBar_star);
        commentText = (EditText) findViewById(R.id.publish_evaluation_EditText_comment);

        product_image = (ImageView) findViewById(R.id.publish_evaluation_ImageView_goods);

        Bback = (ImageButton) findViewById(R.id.publish_evaluation_ImageButton_back);
        publish = (Button) findViewById(R.id.publish_evaluation_Button_publish);

        //获取到待评价的商品
        product_OP = new ProductDao(PublishComment.this);
        order_OP = new OrderDao(PublishComment.this);

        this.productID = order_OP.findProductIDbyOrderID(this.orderID);

        Log.i("product_id",String.valueOf(productID));
        this.product = product_OP.getProductById(this.productID);

        //设置图片


        Log.i("productImae",product.getImage());
        int resId = getResources().getIdentifier(product.getImage(), "drawable", getPackageName());
        product_image.setImageResource(resId);

        Log.i("resId",String.valueOf(resId));

       // product_image.setImageResource(resId);


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

                    CommentDao comment_OP = new CommentDao(PublishComment.this);
                    //Date now=new Date();

                    comment.setContent(commentText.getText().toString());
                    //comment.setCreate_time(now);

                    comment.setCreate_time(new Date());
                    comment.setOrder_id(orderID);
                    comment.setProduct_id(productID);
                    comment.setSatrt_level(starNum);
                    comment.setUser_id(log_user);


                    Log.i("User_id",String.valueOf(log_user));
                    Log.i("startNum", String.valueOf(starNum));
                    Log.i("orderId",String.valueOf(orderID));


                    comment_OP.saveComment(comment);

                    Intent intent3 = new Intent(PublishComment.this, MainActivity.class);
                    intent3.putExtra("choice", 0);
                    startActivity(intent3);

                    order_OP.updateOrder(orderID, 4);
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
