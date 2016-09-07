package com.huadi.shoppingmall.activity;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.User;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;


public class Login extends Activity implements OnClickListener {

    static String LogName;      //登录的用户名

    Button signin_button;      //登录按钮
    TextView register_link;    //注册链接按钮
    TextView modifypwd_link;   //修改密码按钮
    EditText UserName;         //用户名输入框
    EditText pwd;              //密码输入框

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);



        UserName = (EditText) findViewById(R.id.username_edit);
        pwd = (EditText) findViewById(R.id.password_edit);
        modifypwd_link = (TextView) findViewById(R.id.modifypassword_link);
        register_link = (TextView) findViewById(R.id.register_link);
        signin_button = (Button) findViewById(R.id.signin_button);

        register_link.setOnClickListener(this);
        modifypwd_link.setOnClickListener(this);
        signin_button.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String login_Uname, login_Upass;
        login_Uname = UserName.getText().toString().trim();
        login_Upass = pwd.getText().toString().trim();
        Log.i("user_name", login_Uname);
        Log.i("password", login_Upass);

        if (v == signin_button) {
            //若输入为空则提示
            if ((UserName.getText().length() == 0) || (pwd.getText().length() == 0))
                Toast.makeText(Login.this, "输入不能为空",
                        Toast.LENGTH_SHORT).show();
                //若输入不为空的判断
            else if ((UserName.getText().length() != 0) && (pwd.getText().length() != 0)) {

                this.LogName = login_Uname;
                User user = new User();
                user.setUsername(login_Uname);
                user.setPassword(login_Upass);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null) {
                            Toast.makeText(Login.this, "登录成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences settings = getSharedPreferences("setting", 0);
                            SharedPreferences.Editor editor = settings.edit();
                            Log.i("user_id", user.getObjectId());
                            editor.putString("user_id", user.getObjectId());
                            editor.commit();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Login.this, "登录失败，用户名或密码错误",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        }
    }

    //注册链接
    else if(v==register_link)
        {
        Intent intent2 = new Intent(Login.this, RegisterActivity.class);
        startActivity(intent2);
    }

    else if(v==modifypwd_link)

    {
        Intent intent3 = new Intent(Login.this, PasswordModify.class);
        startActivity(intent3);
    }
}

}


