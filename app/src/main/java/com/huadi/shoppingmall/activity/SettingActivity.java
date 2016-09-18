package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.UserDao;
import com.huadi.shoppingmall.model.*;
import com.huadi.shoppingmall.db.*;

import android.view.View.OnClickListener;
import android.widget.*;
import android.content.Intent;
import android.content.SharedPreferences;

import com.huadi.shoppingmall.MainActivity;


import android.view.View;

public class SettingActivity extends Activity implements OnClickListener {

    int log_user;
    TextView Uname; //用户名
    EditText E_mail;  //邮箱输入框
    EditText Telephone;  //电话输入框
    ImageButton Bback;  //返回按钮
    Button Bsave; //保存按钮
    Button Bexit;  //退出按钮

    User user;
    UserDao user_OP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_member_settings);

        //获取全局的用户ID


        SharedPreferences settings = getSharedPreferences("setting", 0);

        log_user = settings.getInt("user_id", 0);
        Log.i("user_id", String.valueOf(log_user));


        //初始化用户变量和用户操作变量
        user_OP = new UserDao(SettingActivity.this);

        user = user_OP.getUserById(log_user);

        Uname = (TextView) findViewById(R.id.member_settings_TextView_username);
        E_mail = (EditText) findViewById(R.id.member_settings_EditText_eMail);
        Telephone = (EditText) findViewById(R.id.member_settings_EditText_phone);
        Bback = (ImageButton) findViewById(R.id.member_settings_ImageButton_back);
        Bsave = (Button) findViewById(R.id.member_settings_Button_save);
        Bexit = (Button) findViewById(R.id.member_settings_Button_exit);

        //初始化用户名、邮箱、电话
        Uname.append("     " + user.getName());

        Log.i("userName", user.getName());
        E_mail.setText(user.getEmail());
        E_mail.setText(user.getPhone());

        //为确认支付设置监听器
        Bback.setOnClickListener(this);
        Bsave.setOnClickListener(this);
        Bexit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //保存按钮
        if (v == Bsave) {
            if (E_mail.getText().toString().length() == 0 || Telephone.getText().toString().length() == 0)
                Toast.makeText(SettingActivity.this, "请输入邮箱或电话", Toast.LENGTH_SHORT).show();
            else {
                user.setEmail(E_mail.getText().toString());
                user.setPhone(Telephone.getText().toString());
                user_OP.updateUser(user);

                Toast.makeText(SettingActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("choice", 2);
                startActivity(intent);

            }
        } else if (v == Bback) {

            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            intent.putExtra("choice", 2);
            startActivity(intent);

        } else if (v == Bexit) {
            System.exit(0);
        }
    }
}
