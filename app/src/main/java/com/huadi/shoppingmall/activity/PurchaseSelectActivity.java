package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.ProductDao;
import com.huadi.shoppingmall.model.Product;

/**
 * Created by smartershining on 16-7-22.
 */

public class PurchaseSelectActivity  extends Activity {

    private int productId;      //从详情页面传过来的商品ID
    private Product product;


    private ImageButton back;                       //返回按钮
    private ImageView product_image;               //返回按钮
    private TextView Product_price;                //价格
    private EditText Product_num;                  //数量

    private RadioGroup product_size;               //尺寸选择按钮组
    private RadioButton size_s;                   //S尺寸
    private RadioButton size_m;                   //M尺寸
    private RadioButton size_l;                    //L尺寸

    private RadioGroup product_color;            //颜色选择按钮组
    private RadioButton color1;                  //颜色1选择按钮
    private RadioButton color2;                  //颜色2选择按钮
    private RadioButton color3;                //颜色3选择按钮

    private Button ack ;                            //确认按钮
    private Button cancel;                       //取消按钮

    private TextView stock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_purchase_confirm);

        //获取从商品详情界面传递下来的数据
        Intent intent = getIntent();
        productId = intent.getIntExtra("product_id", -1);
        Log.i("product_id", String.valueOf(productId));

        this.product = new ProductDao(this).getProductById(productId);


        back = (ImageButton) findViewById(R.id.purchase_confirm_ImageButton_back);

        product_image = (ImageView) findViewById(R.id.purchase_confirm_ImageView_goods);
        Product_price = (TextView) findViewById(R.id.purchase_confirm_TextView_price);
        Product_num = (EditText) findViewById(R.id.purchase_confirm_EditText_num);
        stock = (TextView) findViewById(R.id.purchase_confirm_TextView_save);

        product_size = (RadioGroup) findViewById(R.id.purchase_confirm_RadioGroup_size);
        size_s = (RadioButton) findViewById(R.id.purchase_confirm_RadioButton_S);
        size_m = (RadioButton) findViewById(R.id.purchase_confirm_RadioButton_M);
        size_l = (RadioButton) findViewById(R.id.purchase_confirm_RadioButton_L);

        product_color = (RadioGroup) findViewById(R.id.purchase_confirm_RadioGroup_color);
        color1 = (RadioButton) findViewById(R.id.purchase_confirm_RadioButton_color1);
        color2 = (RadioButton) findViewById(R.id.purchase_confirm_RadioButton_color2);
        color3 = (RadioButton) findViewById(R.id.purchase_confirm_RadioButton_color3);


        ack = (Button) findViewById(R.id.purchase_confirm_Button_ack);
        cancel = (Button) findViewById(R.id.purchase_confirm_Button_cancel);



        stock.setText(String.valueOf(product.getStock()));

        //取出颜色进行展示
        String str = product.getColor();



        //设置图片
        int resId = this.getResources().getIdentifier(product.getImage(), "drawable", getPackageName());
        product_image.setImageResource(resId);


        Log.i("price", String.valueOf(product.getPrice()));
        //设置价格和库存
        Product_price.append(String.valueOf(product.getPrice()));
        Log.i("stock", String.valueOf(product.getStock()));



        //注册监听
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurchaseSelectActivity.this, ProductDetail.class);
                startActivity(intent);
            }
        });
        ack.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       if (Product_num.getText().length() == 0)  {
                                           Toast.makeText(PurchaseSelectActivity.this, "请输入数量", Toast.LENGTH_SHORT).show();
                                       } else if (Integer.parseInt(Product_num.getText().toString()) > product.getStock()) {
                                           Toast.makeText(PurchaseSelectActivity.this, "请输入小于库存的数量", Toast.LENGTH_SHORT).show();
                                       } else {
                                           Log.i("ack", "is Clicked");
                                           int orderNum = Integer.parseInt(Product_num.getText().toString());
                                           Log.i("orderNum", String.valueOf(orderNum));

                                           Intent intent = new Intent(PurchaseSelectActivity.this, OrderConfirm.class);
                                           intent.putExtra("product_id", productId);
                                           intent.putExtra("order_number", orderNum);
                                           startActivity(intent);
                                       }
                                   }
                               });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PurchaseSelectActivity.this, ProductDetail.class);
                startActivity(intent);
            }
        });
    }
}
