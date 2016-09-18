package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.ProductDao;
import com.huadi.shoppingmall.db.dao.ShopCarDao;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.model.ShopCar;

/**
 * Created by smartershining on 16-7-22.
 */


public class AddShopCarActivity extends Activity implements View.OnClickListener {
    private int productID;  //从详情页面传过来的商品ID
    private int log_user;  //从详情页面传过来的用户ID
    private Product product;

    private ImageButton ThisActivity_back;  //返回按钮
    private ImageView product_image;  //返回按钮
    private TextView Product_price;  //价格
    private EditText Product_num;   //数量

    private RadioGroup product_size;  //尺寸选择按钮组
    private RadioButton size_s;   //S尺寸
    private RadioButton size_m;  //M尺寸
    private RadioButton size_l;   //L尺寸

    private RadioGroup product_color;   //颜色选择按钮组
    private RadioButton color1;  //颜色1选择按钮
    private RadioButton color2;  //颜色2选择按钮
    private RadioButton color3;   //颜色3选择按钮

    private Button Bok;   //确认按钮
    private Button Bcancel;   //取消按钮


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_addshopcar_confirm);

        //获取从商品详情界面传递下来的数据
        Intent intent = getIntent();
        this.productID = intent.getIntExtra("product_id", 0);
        this.product = new ProductDao(this).getProductById(this.productID);

        //获取全局的用户ID
        final SharedPreferences settings = getSharedPreferences("settings", 0);
        this.log_user = settings.getInt("user_id", 0);

        //ThisActivity_title=(TextView) findViewById(R.id.purchase_confirm_TextView_title);
        ThisActivity_back = (ImageButton) findViewById(R.id.addshopcar_confirm_ImageButton_back);
        product_image = (ImageView) findViewById(R.id.addshopcar_confirm_ImageView_goods);
        Product_price = (TextView) findViewById(R.id.addshopcar_confirm_TextView_price);
        Product_num = (EditText) findViewById(R.id.addshopcar_confirm_EditText_num);

        product_size = (RadioGroup) findViewById(R.id.addshopcar_confirm_RadioGroup_size);
        size_s = (RadioButton) findViewById(R.id.addshopcar_confirm_RadioButton_S);
        size_m = (RadioButton) findViewById(R.id.addshopcar_confirm_RadioButton_M);
        size_l = (RadioButton) findViewById(R.id.addshopcar_confirm_RadioButton_L);

        product_color = (RadioGroup) findViewById(R.id.addshopcar_confirm_RadioGroup_color);
        color1 = (RadioButton) findViewById(R.id.addshopcar_confirm_RadioButton_color1);
        color2 = (RadioButton) findViewById(R.id.addshopcar_confirm_RadioButton_color2);
        color3 = (RadioButton) findViewById(R.id.addshopcar_confirm_RadioButton_color3);

        Bok = (Button) findViewById(R.id.addshopcar_confirm_Button_bok);
        Bcancel = (Button) findViewById(R.id.addshopcar_confirm_Button_bcancle);

        //取出尺寸进行设置
        String str1 = this.product.getSize().toString();
        String[] strs1 = str1.split(",");
        size_s.setText(strs1[0]);
        size_m.setText(strs1[1]);
        size_l.setText(strs1[2]);

        //取出颜色进行展示
        String str = this.product.getColor().toString();
        String[] strs = str.split(",");
        color1.setText(strs[0]);
        color2.setText(strs[1]);
        color3.setText(strs[2]);

        //设置图片
        int resId = getResources().getIdentifier(this.product.getImage(), "drawable", "src/main/res/");
        product_image.setImageResource(resId);

        //设置价格和库存
        Product_price.append(" " + String.valueOf(this.product.getPrice()));
        Product_num.append(" " + String.valueOf(this.product.getSalNum()));

        //注册监听
        ThisActivity_back.setOnClickListener(this);
        Bok.setOnClickListener(this);
        Bcancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //确认按钮
        if (v == Bok) {
            //若输入数量为空则提示
            if ((Product_num.getText().length() == 0))
                Toast.makeText(this, "请输入数量",
                        Toast.LENGTH_SHORT).show();
                //若输入不为空的判断
            else if ((Product_num.getText().length()) != 0) {
                //输入数量大于库存的判断
                if ((Integer.parseInt(Product_num.getText().toString())) > this.product.getStock()) {
                    Toast.makeText(this, "请输入小于库存的数量",
                            Toast.LENGTH_SHORT).show();
                    Product_num.setText(null);
                } else {
                    ShopCarDao shopcar_op = new ShopCarDao(this);
                    int ShopCarNum = Integer.parseInt(Product_num.getText().toString());
                    //该商品已存在购物车中
                    if (shopcar_op.findShopCarByUserIdAndProductId(this.log_user, this.productID) == true) {
                        Toast.makeText(this, "该商品已经在购物车",
                                Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(this, ProductDetail.class);
                        //跳转到商品详情界面
                        startActivity(intent1);
                    }
                    //该商品不存在购物车中
                    else if (shopcar_op.findShopCarByUserIdAndProductId(this.log_user, this.productID) == false) {
                        //新建一个购物车记录并添加到数据库中

                        ShopCar shop_car = new ShopCar();
                        shop_car.setProduct_id(this.productID);
                        shop_car.setUser_id(this.log_user);
                        shop_car.setProduct_num(ShopCarNum);
                        shopcar_op.saveShopCar(shop_car);

                        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(this, ProductDetail.class);
                        intent1.putExtra("product_id", productID);
                        startActivity(intent1);
                    }
                }
            }
        } else if (v == Bcancel) {
            Intent intent2 = new Intent(this, ProductDetail.class);
            startActivity(intent2);
        } else if (v == ThisActivity_back) {
            Intent intent3 = new Intent(this, ProductDetail.class);
            startActivity(intent3);
        }
    }
}

