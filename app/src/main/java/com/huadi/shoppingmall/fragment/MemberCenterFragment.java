package com.huadi.shoppingmall.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.activity.AddressActivity;

import com.huadi.shoppingmall.activity.CheckBalance;
import com.huadi.shoppingmall.activity.CouponActivity;

import com.huadi.shoppingmall.activity.Login;
import com.huadi.shoppingmall.activity.OrderActivity;
import com.huadi.shoppingmall.activity.SettingActivity;
import com.huadi.shoppingmall.model.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class MemberCenterFragment extends Fragment {


    private Button waitTopay;
    private Button waitToExpress;
    private Button waitToGet;
    private Button waitToComment;
    private Button coupon;
    private Button balance;         //余额
    private Button integration;     //积分
    private Button address;
    private Button setting;
    private String user_id;

    private TextView name;
    private TextView rank;
    private User user;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_member_center, container, false);
        Log.i("onCreateView", "is Called");
        return view;
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        SharedPreferences settings = getActivity().getSharedPreferences("setting", 0);

        user_id = settings.getString("user_id", "0");

        Log.i("onActivity", "is Called");
        init();
        if (user_id.equals("0")) {
            Log.i("Login ", "is Called");
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        } else {
            BmobQuery<User> query = new BmobQuery<>();
            query.getObject(user_id, new QueryListener<User>() {
                @Override
                public void done(User object, BmobException e) {
                    if (e == null) {
                        user = object;
                        name.setText(user.getUsername());
                        rank.setText("VIP" + user.getRank());

                    }
                }
            });
        }
        Log.i("user_id", String.valueOf(user_id));

    }

    public void init() {

        Button waitTopay = (Button) getActivity().findViewById(R.id.member_center_Button_waitToPay);

        waitToExpress = (Button) getActivity().findViewById(R.id.member_center_Button_waitToExpress);
        waitToGet = (Button) getActivity().findViewById(R.id.member_center_Button_waitToGet);
        waitToComment = (Button) getActivity().findViewById(R.id.member_center_Button_waitToComment);
        coupon = (Button) getActivity().findViewById(R.id.member_center_Button_coupon);
        balance = (Button) getActivity().findViewById(R.id.member_center_Button_balance);
        integration = (Button) getActivity().findViewById(R.id.member_center_Button_integration);
        address = (Button) getActivity().findViewById(R.id.member_center_Button_address);
        name = (TextView) getActivity().findViewById(R.id.member_center_TextView_username);
        rank = (TextView) getActivity().findViewById(R.id.member_center_TextView_level);
        setting = (Button) getActivity().findViewById(R.id.member_center_ImageButton_set);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("setting", "is Clicked");
                Intent intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });


        waitTopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderHelper(0);

            }
        });

        waitToExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderHelper(1);
                Log.i("waitToExpress", "is called");
            }
        });
        waitToGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderHelper(2);
                Log.i("waitToGet", "is called");
            }
        });
        waitToComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderHelper(3);
                Log.i("waitToComment", "is Called");
            }
        });

        coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CouponActivity.class);
                intent.putExtra("user_id", user_id);
                Log.i("coupon", "is Called");
                startActivity(intent);
            }
        });

        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), CheckBalance.class);
                startActivity(intent);
            }
        });

        integration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("积分");
                dialog.setMessage(String.valueOf(user.getPoint()));
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();


            }
        });
        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddressActivity.class);
                Log.i("memberCenter", String.valueOf(user_id));
                startActivity(intent);
            }
        });

    }

    public void orderHelper(int status) {

        Intent intent = new Intent();
        intent.putExtra("status", status);
        intent.setClass(getActivity(), OrderActivity.class);
        getActivity().startActivity(intent);
    }


}
