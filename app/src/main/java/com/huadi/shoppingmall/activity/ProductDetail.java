package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.ProductDao;
import com.huadi.shoppingmall.db.dao.ShopCarDao;
import com.huadi.shoppingmall.model.Order;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.model.ShopCar;

import java.util.ArrayList;
import java.util.List;

public class ProductDetail extends Activity {

    private int productID;  //从商品列表页面传过来的商品ID
    private int log_user;  //从商品列表页面传过来的用户ID
    private Product product;

    private ImageButton ThisActivity_back;
    private ImageView product_image;  //商品图片
    private TextView Product_name;  //名称
    private TextView Product_price;  //价格
    private TextView Product_salenum;  //销量
    private TextView Product_brand;  //商家
    private TextView Product_size;  //尺寸
    private TextView Product_color;  //颜色

    private Button B_addcar;   //加入购物车按钮
    private Button B_buy;   //立即购买按钮
    private Button B_comment;   //查看评价按钮

    //立即购买和加入购物车商品选择对话框
    private ImageView Dialog_image;   //对话框里的商品图片
    private TextView Dialog_price;  //对话框里的价格
    private TextView Dialog_stock;  //对话框里的库存
    private EditText Dialog_num;   //对话框里输入的数量


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);

        //获取从商品列表界面传递下来的商品ID
        final Intent intent = getIntent();
        this.productID = intent.getIntExtra("product_id", 0);
        Log.i("product_id", String.valueOf(productID));

        ProductDao Product_OP = new ProductDao(ProductDetail.this);
        this.product = Product_OP.getProductById(this.productID);


        final SharedPreferences settings = getSharedPreferences("setting", 0);
        log_user = settings.getInt("user_id", 0);


        ThisActivity_back = (ImageButton) findViewById(R.id.goods_detail_ImageButton_back);
        product_image = (ImageView) findViewById(R.id.goods_detail_ImageView_goods);
        Product_name = (TextView) findViewById(R.id.goods_detail_TextView_goodsname);
        Product_price = (TextView) findViewById(R.id.goods_detail_TextView_price);
        Product_salenum = (TextView) findViewById(R.id.goods_detail_TextView_sales);
        Product_brand = (TextView) findViewById(R.id.goods_detail_TextView_store);
        Product_size = (TextView) findViewById(R.id.goods_detail_TextView_size);
        Product_color = (TextView) findViewById(R.id.goods_detail_TextView_color);

        B_addcar = (Button) findViewById(R.id.belowtitle_button_joinin);
        B_buy = (Button) findViewById(R.id.belowtitle_button_buy1);
        B_comment = (Button) findViewById(R.id.goods_detail_Button_comment);


        //立即购买和加入购物车商品选择对话框各控件初始化


        //取出商品名字进行展示
        Product_name.setText(this.product.getName().toString());

        //取出商品价格进行展示
        Product_price.append(String.valueOf(this.product.getPrice()).toString());

        //取出商品销量进行展示
        Product_salenum.append(String.valueOf(this.product.getSalNum()).toString());

        //取出品牌进行设置
        Product_brand.append(this.product.getBrand().toString());

        //取出品牌进行设置
        Product_size.append("S" + "     " + "M" + "     " + "L");


        //取出颜色进行展示
        String str = this.product.getColor().toString();
        String[] strs = str.split(",");
        for (int i = 0; i < str.length(); i++) {
            Product_color.append(strs[i] + "     ");
        }

        //设置图片
        int resId = ProductDetail.this.getResources().getIdentifier(product.getImage(), "drawable", getPackageName());
        product_image.setImageResource(resId);




        //注册监听



        B_addcar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("addcar", "is Clicked");
                //若用户还未登录则提示
                if (log_user == 0) {
                    Toast.makeText(ProductDetail.this, "您还未登录",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductDetail.this, Login.class);
                    startActivity(intent);
                }

                //用户登录后的判断
                else {

                    final EditText et = new EditText(ProductDetail.this);
                    new AlertDialog.Builder(ProductDetail.this).setTitle("请输入购买数量")
                            .setIcon(android.R.drawable.ic_dialog_info).setView(et)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    String edit = et.getText().toString();
                                    Log.i("edit", edit);

                                    int number = Integer.parseInt(edit);
                                    Log.i("number", String.valueOf(number));
                                    ShopCar shopCar = new ShopCar();

                                    shopCar.setProduct_id(productID);
                                    shopCar.setProduct_num(number);

                                    shopCar.setUser_id(log_user);
                                    Log.i("shopCar product id", String.valueOf(shopCar.getProduct_num()));

                                    ShopCarDao dao = new ShopCarDao(ProductDetail.this);
                                    dao.saveShopCar(shopCar);

                                    Intent intent = new Intent(ProductDetail.this, MainActivity.class);
                                  //  intent.putExtra("choice", 1);
                                    startActivity(intent);

                                }
                            }).setNegativeButton("取消", null).show();

                }
            }
        });

        B_buy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("buy", "isClicked");
                //若用户还未登录则提示
                if (log_user == 0) {
                    Toast.makeText(ProductDetail.this, "您还未登录",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ProductDetail.this, Login.class);
                    intent.putExtra("where", "Product_detail");
                    intent.putExtra("product_id", productID);
                    startActivity(intent);
                }
                //用户登录后的判断
                else {

                    final EditText et = new EditText(ProductDetail.this);
                    new AlertDialog.Builder(ProductDetail.this).setTitle("请输入购买数量")
                            .setIcon(android.R.drawable.ic_dialog_info).setView(et)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    int number = Integer.parseInt(et.getText().toString());
                                    Intent intent = new Intent(ProductDetail.this, Pay.class);
                                    intent.putExtra("from", "buy");

                                    ArrayList<Order> list = new ArrayList<Order>();
                                    Order order = new Order();
                                    order.setNumber(number);
                                    order.setProduct_id(productID);
                                    list.add(order);
                                    intent.putExtra("order",list);
                                    intent.putExtra("from","pay");
                                    ProductDetail.this.startActivity(intent);


                                }
                            }).setNegativeButton("取消", null).show();
                }
            }
        });
        B_comment.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this, ProductCommentActivity.class);
                intent.putExtra("product_id", productID);
                startActivity(intent);
            }
        });

        ThisActivity_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductDetail.this, Category.class);
                startActivity(intent);
            }
        });

    }
}
