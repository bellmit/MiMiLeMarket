package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by smartershining on 16-7-22.
 */


public class CheckBalance extends Activity implements View.OnClickListener {

    private String user_id;           //从全局变量获取用户ID
    TextView MyBalance;                //余额显示
    TextView LoadNum;             //充值金额标签
    EditText Load_money;          //充值输入框
    Button Bload;                 //充值按钮按钮
    Button BLoadConfirm;          //确认充值按钮按钮
    ImageButton Bback;            //返回按钮




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_account_balance);

        MyBalance = (TextView) findViewById(R.id.account_balance_TextView_balance);
        LoadNum = (TextView) findViewById(R.id.account_balance_TextView_loadmoney);
        Load_money = (EditText) findViewById(R.id.account_balance_EditText_loadmoney);
        Bload = (Button) findViewById(R.id.account_balance_Button_publish);
        BLoadConfirm = (Button) findViewById(R.id.account_balance_Button_doload);
        Bback = (ImageButton) findViewById(R.id.account_balance_ImageButton_back);


        SharedPreferences settings = getSharedPreferences("setting", 0);
        user_id = settings.getString("user_id", "0");


        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(user_id, new QueryListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                if (e == null) {
                    MyBalance.append(String.valueOf(user.getRemind()));
                }
            }
        });

        //展示用户余额

        Bload.setOnClickListener(this);

        BLoadConfirm.setOnClickListener(this);
        Bback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //用户点击充值
        if (v == Bload) {
            //将隐藏掉的充值余额的有关控件显示
            Load_money.setVisibility(View.VISIBLE);
            LoadNum.setVisibility(View.VISIBLE);
            BLoadConfirm.setVisibility(View.VISIBLE);
            Load_money.setText(null);
        }
        else if (v == BLoadConfirm) {
            if (Load_money.getText().toString().length() == 0)
                Toast.makeText(CheckBalance.this, "请输入充值金额",
                        Toast.LENGTH_SHORT).show();

            else if (Load_money.getText().toString().length() != 0) {
                //更新用户余额

                User user = new User();
                int myMoney = Integer.parseInt(Load_money.getText().toString());
                myMoney += Integer.parseInt(MyBalance.getText().toString().substring(4));
                Log.i("money", String.valueOf(myMoney));

                user.setValue("remind",myMoney);

                Log.i("user_objectId", user_id);

                user.update(user_id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(CheckBalance.this, "充值成功",
                                    Toast.LENGTH_SHORT).show();
                            Log.i("update","sucess");
                            Intent intent1 = new Intent(CheckBalance.this, MainActivity.class);
                            intent1.putExtra("choice", 2);
                            //跳转到商品详情界面
                            startActivity(intent1);
                        } else {
                            Toast.makeText(CheckBalance.this, "充值失败" + e.getErrorCode() + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
        //返回按钮
        else if (v == Bback) {
            Intent intent1 = new Intent(CheckBalance.this, MainActivity.class);

            //跳转到商品详情界面
            intent1.putExtra("choice", 2);
            startActivity(intent1);
        }
    }
}