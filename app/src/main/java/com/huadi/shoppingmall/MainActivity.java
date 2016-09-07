package com.huadi.shoppingmall;


import com.facebook.stetho.Stetho;
import com.huadi.shoppingmall.fragment.MallRecommendFragment;
import com.huadi.shoppingmall.fragment.MemberCenterFragment;

import com.huadi.shoppingmall.fragment.ShopcarContentFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bmob.sms.BmobSMS;
import cn.bmob.v3.Bmob;

public class MainActivity extends FragmentActivity implements OnClickListener{


    private MallRecommendFragment fg_reco;
    private ShopcarContentFragment fg_sopca;
    private MemberCenterFragment fg_me;
    private FrameLayout flayout;

    private RelativeLayout reco_layout;
    private RelativeLayout shopcar_layout;
    private RelativeLayout me_layout;

    private ImageView reco_image;
    private ImageView sopca_image;
    private ImageView me_image;
    private TextView reco_text;
    private TextView me_text;
    private TextView sopca_text;

    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int blue =0xFF0AB2FB;


    FragmentManager fManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        initViews();
        Intent intent = getIntent();
        int choice = intent.getIntExtra("choice", 0);
        setChioceItem(choice);
        Log.i("choice", String.valueOf(choice));

        Stetho.newInitializerBuilder(this)
                .enableDumpapp(
                        Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(
                        Stetho.defaultInspectorModulesProvider(this))
                .build();

        Bmob.initialize(this, "cb1504fa6b4d3dad70bf8a24bf58d5ad");
        BmobSMS.initialize(this, "cb1504fa6b4d3dad70bf8a24bf58d5ad");
    }



    public void initViews()
    {
        reco_image = (ImageView) findViewById(R.id.tab_recommend_image);
        sopca_image = (ImageView) findViewById(R.id.tab_shopcar_image);
        me_image = (ImageView) findViewById(R.id.tab_membercenter_image);
        reco_text = (TextView) findViewById(R.id.tab_recommend_text);
        sopca_text = (TextView) findViewById(R.id.tab_shopcar_text);
        me_text = (TextView) findViewById(R.id.tab_membercenter_text);
        reco_layout = (RelativeLayout) findViewById(R.id.tab_recommend_layout);
        shopcar_layout = (RelativeLayout) findViewById(R.id.tab_shopcar_layout);
        me_layout = (RelativeLayout) findViewById(R.id.tab_membercenter_layout);
        reco_layout.setOnClickListener(this);
        shopcar_layout.setOnClickListener(this);
        me_layout.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_recommend_layout:
                setChioceItem(0);
                break;
            case R.id.tab_shopcar_layout:
                setChioceItem(1);
                break;
            case R.id.tab_membercenter_layout:
                setChioceItem(2);
                break;
            default:
                break;
        }

    }



    public void setChioceItem(int index)
    {

        FragmentTransaction transaction = fManager.beginTransaction();
        clearChioce();
        hideFragments(transaction);
        switch (index) {
            case 0:
                reco_text.setTextColor(blue);
                if (fg_reco == null) {
                    fg_reco = new MallRecommendFragment();

                    transaction.add(R.id.content, fg_reco);
                } else {

                    transaction.show(fg_reco);
                }
                break;

            case 1:
                sopca_text.setTextColor(blue);


                if (fg_sopca == null) {
                    fg_sopca = new ShopcarContentFragment();

                    transaction.add(R.id.content, fg_sopca);
                } else {

                    transaction.show(fg_sopca);
                }
                break;

            case 2:

                me_text.setTextColor(blue);

                if (fg_me == null) {
                    fg_me = new MemberCenterFragment();

                    transaction.add(R.id.content, fg_me);
                } else {

                    transaction.show(fg_me);
                }
                break;
        }
        transaction.commit();
    }


    private void hideFragments(FragmentTransaction transaction) {
        if (fg_reco != null) {
            transaction.hide(fg_reco);
        }
        if (fg_sopca != null) {
            transaction.hide(fg_sopca);
        }
        if (fg_me != null) {
            transaction.hide(fg_me);
        }
    }



    public void clearChioce()
    {

        reco_layout.setBackgroundColor(whirt);
        reco_text.setTextColor(gray);

        shopcar_layout.setBackgroundColor(whirt);
        sopca_text.setTextColor(gray);

        me_layout.setBackgroundColor(whirt);
        me_text.setTextColor(gray);
    }

}
