package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


import android.content.Intent;


import android.view.View;

import android.view.View.OnClickListener;
import android.widget.*;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.*;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


public class PasswordModify extends Activity implements OnClickListener {

    EditText pwdmodify_username;  //用户名输入框
    EditText pwdmodify_Newpwd;  //新密码输入框
    EditText pwdmodify_ReNewpwd;  //新密码确认输入框

    ImageButton pwdmodify_back;  //返回按钮
    ImageButton pwdmodify_Confirm;   //确认修改按钮
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_password_modify);

        pwdmodify_username = (EditText) findViewById(R.id.password_modify_EditText_username);
        pwdmodify_Newpwd = (EditText) findViewById(R.id.password_modify_EditText_newpwd);
        pwdmodify_ReNewpwd = (EditText) findViewById(R.id.password_modify_EditText_checkpwd);
        pwdmodify_back = (ImageButton) findViewById(R.id.password_modify_ImageButton_back);
        pwdmodify_Confirm = (ImageButton) findViewById(R.id.password_modify_ImageButton_sue);


        pwdmodify_back.setOnClickListener(this);
        pwdmodify_Confirm.setOnClickListener(this);

        user = (User) new User().getCurrentUser();
    }

    @Override
    public void onClick(View v) {
        String UserName = pwdmodify_username.getText().toString().trim();    //用户名
        String NewPwd = pwdmodify_Newpwd.getText().toString().trim();        //新密码
        String ReNewPwd = pwdmodify_ReNewpwd.getText().toString().trim();    //确认的新密码
        User pwdmf_user = new User();

        if (v == pwdmodify_Confirm) {
            //如果输入为空则提示用户
            if (UserName.length() == 0 || NewPwd.length() == 0 || ReNewPwd.length() == 0) {
                Toast.makeText(PasswordModify.this, "输入不能为空",
                        Toast.LENGTH_SHORT).show();
            }
            //如果两次输入的密码不一致则提示用户
            else if (!NewPwd.equals(ReNewPwd)) {
                Toast.makeText(PasswordModify.this, "请输入统一的密码",
                        Toast.LENGTH_SHORT).show();
            }
            //能否修改判断
            else {
                user.setPassword(NewPwd);
                user.update(user.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(PasswordModify.this, "修改成功",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent1 = new Intent(PasswordModify.this, Login.class);
                            startActivity(intent1);
                        }
                    }
                });
            }
        }
        else if (v == pwdmodify_back) {
            Intent intent2 = new Intent(PasswordModify.this, Login.class);
            startActivity(intent2);
        }
    }

}
