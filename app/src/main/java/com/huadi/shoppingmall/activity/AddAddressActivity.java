package com.huadi.shoppingmall.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.db.dao.AddressDao;
import com.huadi.shoppingmall.model.Address;

/**
 * Created by smartershining on 16-7-21.
 */

public class AddAddressActivity extends Activity{

    private EditText name;
    private EditText phone;
    private EditText detail;
    private EditText postcode;

    private ImageView back;
    private Button    add;



    public void onCreate(Bundle savedInstanced) {
        super.onCreate(savedInstanced);
        setContentView(R.layout.activity_add_address);
        initView();
        Log.i("AddressActivity", "succeed");
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.add_address_ImageButton_back);
        add = (Button) findViewById(R.id.add_address_Button_save);
        name = (EditText) findViewById(R.id.add_address_EditText_getPerson);
        phone = (EditText) findViewById(R.id.add_address_EditText_phone);
        detail = (EditText)findViewById(R.id.add_address_EditText_detailAddress);
        postcode = (EditText) findViewById(R.id.add_address_EditText_postcode);



        Intent intent = getIntent();
        final boolean isAdd = intent.getBooleanExtra("isAdd", true);
        if (!isAdd) {
            Bundle bundle = intent.getExtras();
            Address address = (Address) bundle.getSerializable("address");
            name.setText(address.getName());
            phone.setText(address.getPhone());
            detail.setText(address.getAddress_info());
            postcode.setText(address.getPostCode());

        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddressActivity.class);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settings = getSharedPreferences("setting", 0);
                int user_id = settings.getInt("user_id", 0);
                Address address = new Address();


                AddressDao dao = new AddressDao(getApplicationContext());
                if(isAdd) {
                    address.setUser_id(user_id);
                    address.setName(name.getText().toString());
                    address.setPhone(phone.getText().toString());
                    address.setPostCode(postcode.getText().toString());
                    address.setAddress_info(detail.getText().toString());


                    dao.saveAddress(address);
                    Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddAddressActivity.this, MainActivity.class);
                    intent.putExtra("choice",2);
                    startActivity(intent);

                }else {

                    address = (Address) getIntent().getSerializableExtra("address");
                    Log.i("updateAddress",address.getName());
                    address.setName(name.getText().toString());
                    address.setPhone(phone.getText().toString());
                    address.setPostCode(postcode.getText().toString());
                    address.setAddress_info(detail.getText().toString());
                    dao.updateAddress(address);
                    Toast.makeText(getApplicationContext(), "更新成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AddAddressActivity.this, MainActivity.class);
                    intent.putExtra("choice",2);
                    startActivity(intent);
                }
            }
        });

    }
}
