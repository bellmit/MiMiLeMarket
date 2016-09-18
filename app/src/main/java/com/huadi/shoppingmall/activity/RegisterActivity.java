package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.UserDao;
import com.huadi.shoppingmall.model.User;

import android.view.View.OnClickListener;
import android.widget.*;
import android.content.Intent;
import android.view.View;

public class RegisterActivity extends Activity implements OnClickListener {

    EditText regist_Uname;    //用户名输入框
    EditText regist_Upwd;     //密码输入框
    EditText regist_Repwd;    //确认密码输入框
    Button regist_register;   //注册按钮
    ImageButton regist_back;  //返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        regist_Uname = (EditText) findViewById(R.id.register_EditText_uesrname);
        regist_Upwd = (EditText) findViewById(R.id.register_EditText_pwd);
        regist_Repwd = (EditText) findViewById(R.id.register_EditText_checkpwd);
        regist_register = (Button) findViewById(R.id.register_ImageView_register);
        regist_back = (ImageButton) findViewById(R.id.register_ImageButton_back);
        regist_register.setOnClickListener(RegisterActivity.this);
        regist_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        User regist_user = new User();
        UserDao regist_UserOP = new UserDao(this);
        String UserName = regist_Uname.getText().toString().trim();
        String Pwd = regist_Upwd.getText().toString().trim();
        String Repwd = regist_Repwd.getText().toString().trim();
        if (v == regist_register) {
            //输入为空时的判断
            if ((UserName.length() == 0) || (Pwd.length() == 0) || (Repwd.length() == 0))
                Toast.makeText(RegisterActivity.this, "输入不能为空",
                        Toast.LENGTH_SHORT).show();

                //输入不为空时的判断
            else if ((UserName.length() != 0) && (Pwd.length() != 0) && (Repwd.length() != 0)) {

                //密码输入不统一的提示
                if (!Pwd.equals(Repwd))
                    Toast.makeText(RegisterActivity.this, "输入的密码不一致",
                            Toast.LENGTH_SHORT).show();

                    //用户名已被注册过的提示
                else if (regist_UserOP.findUserbyName(UserName))
                    Toast.makeText(RegisterActivity.this, "该用户已被注册过",
                            Toast.LENGTH_SHORT).show();
                    //用户未被注册时的判断判断
                else {
                    //regist_user.setEmail(null);
                    regist_user.setName(UserName);
                    regist_user.setPassword(Pwd);
                    //注册成功
                    if (regist_UserOP.saveUser(regist_user)) {
                        Toast.makeText(RegisterActivity.this, "注册成功",
                                Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(RegisterActivity.this, Login.class);
                        startActivity(intent1);
                    }

                    //输注册失败
                    else {
                        Toast.makeText(RegisterActivity.this, "注册失败",
                                Toast.LENGTH_SHORT).show();
                        regist_Uname.setText(null);
                        regist_Upwd.setText(null);
                        regist_Repwd.setText(null);
                    }

                }
            }
        }
        //返回按钮
        else if (v == regist_back) {
            Intent intent2 = new Intent(RegisterActivity.this, Login.class);
            startActivity(intent2);
        }
    }
}
