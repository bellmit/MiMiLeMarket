package com.huadi.shoppingmall.activity;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.DataBaseOpenHelper;
import com.huadi.shoppingmall.db.dao.UserDao;
import com.huadi.shoppingmall.model.User;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Login extends Activity implements OnClickListener {

    static String LogName;      //登录的用户名

    Button signin_button;      //登录按钮
    TextView register_link;    //注册链接按钮
    TextView modifypwd_link;   //修改密码按钮
    EditText UserName;         //用户名输入框
    EditText pwd;              //密码输入框

    public void onCreate(Bundle savedInstanceState) {
        initdata();
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        //Login.this.finish();

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
                UserDao UserOP = new UserDao(this);
                //若用户未注册过则提示
                if (!UserOP.findUserbyName(login_Uname))
                    Toast.makeText(Login.this, "该用户不存在",
                            Toast.LENGTH_SHORT).show();
                    //若用户名或密码错误则提示

                else if (!UserOP.findUserByNameAndPassword(login_Uname, login_Upass))
                    Toast.makeText(Login.this, "登录失败，用户名或密码错误",
                            Toast.LENGTH_SHORT).show();

                    //成功登录
                else {
                    this.LogName = login_Uname;
                    User user = UserOP.getUserByName(login_Uname);

                    SharedPreferences settings = getSharedPreferences("setting", 0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putInt("user_id", user.getId());
                    Log.i("user_id", String.valueOf(user.getId()));
                    editor.commit();

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        }

        //注册链接
        else if (v == register_link) {
            Intent intent2 = new Intent(Login.this, RegisterActivity.class);
            startActivity(intent2);
        } else if (v == modifypwd_link) {
            Intent intent3 = new Intent(Login.this, PasswordModify.class);
            startActivity(intent3);
        }
    }

    public void initdata() {
        DataBaseOpenHelper helper = new DataBaseOpenHelper(getApplicationContext());
        SQLiteDatabase db = helper.getWritableDatabase();


    }

}


