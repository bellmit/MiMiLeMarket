package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.User;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends Activity implements OnClickListener {
    private ImageButton back;
    private EditText phone;
    private EditText code;
    private Button getCode;
    private EditText password;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        initView();
    }


    private void initView() {
        back = (ImageButton) findViewById(R.id.register_ImageButton_back);
        phone = (EditText) findViewById(R.id.register_phone);
        code = (EditText) findViewById(R.id.register_code);
        getCode = (Button) findViewById(R.id.register_get_code);
        password = (EditText) findViewById(R.id.register_password);
        signUp = (Button) findViewById(R.id.register_sign_up);

        back.setOnClickListener(this);
        getCode.setOnClickListener(this);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_ImageButton_back:
                Intent intent = new Intent(RegisterActivity.this, Login.class);
                startActivity(intent);
                break;
            case R.id.register_get_code:
                getCode();
                break;
            case R.id.register_sign_up:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        final String phoneString = phone.getText().toString().trim();
        if (TextUtils.isEmpty(phoneString)) {
            Toast.makeText(this, "phone不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String codeString = code.getText().toString().trim();
        if (TextUtils.isEmpty(codeString)) {
            Toast.makeText(this, "code不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        final String passwordString = password.getText().toString().trim();
        if (TextUtils.isEmpty(passwordString)) {
            Toast.makeText(this, "password不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something

        BmobSMS.verifySmsCode(RegisterActivity.this, phoneString, codeString, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    User user = new User();
                    user.setPassword(passwordString);
                    user.setMobilePhoneNumber(phoneString);
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(User user, cn.bmob.v3.exception.BmobException e) {
                            if (e == null) {
                                Toast.makeText(RegisterActivity.this, "注册成功",Toast.LENGTH_LONG).show();
                                
                            }
                        }
                    });
                }
            }
        });




    }

    public void getCode() {

        Log.i("getCode", "is Called");
        BmobSMS.requestSMSCode(this, phone.getText().toString(), "mimile", new RequestSMSCodeListener(){
            @Override
            public void done(Integer integer, BmobException e) {

                if (e == null) {
                    Log.i("SMS", "OK");
                } else {
                    Log.i("error", String.valueOf(integer) + e.getMessage() + e.getErrorCode());
                }
            }
        });



    }






}
