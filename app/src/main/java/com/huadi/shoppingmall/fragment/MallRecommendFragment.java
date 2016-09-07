package com.huadi.shoppingmall.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.huadi.shoppingmall.Adapter.ViewPagerAdapter;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.activity.Category;
import com.huadi.shoppingmall.activity.ProductActivity;
import com.huadi.shoppingmall.activity.ProductDetail;
import com.huadi.shoppingmall.model.Product;

import java.util.ArrayList;

public class MallRecommendFragment extends Fragment implements OnClickListener, OnPageChangeListener {
    Button category;
    private ViewPager viewPager;

    private View view1;

    private ImageView[] dots;


    private ViewPagerAdapter vpAdapter;


    private ArrayList<View> views;

    private static final int[] pics = {R.drawable.goods_new1, R.drawable.goods_new2, R.drawable.goods_new3};

    private int currentIndex;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recommend, container, false);
        view1 = view;
        initView();
        initData();
        initDots();
        return view;
    }

    private void initView() {

        views = new ArrayList<View>();


        viewPager = (ViewPager) view1.findViewById(R.id.viewPager_NewGoods);

        vpAdapter = new ViewPagerAdapter(views, getActivity());

        view1.findViewById(R.id.recommend_category_button).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Log.v("LogDemo", "category");
                Intent intent = new Intent(getActivity(), Category.class);
                startActivity(intent);
            }

        });

        view1.findViewById(R.id.recommend_search_button).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("LogDemo", "search");
            }

        });

        view1.findViewById(R.id.image_hot_sell).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("LogDemo", "hot sell");
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("cateOne", "女装");
                intent.putExtra("cateTwo", "裙装");
                startActivity(intent);

            }

        });
        view1.findViewById(R.id.image_discount).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("LogDemo", "discount");
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("cateOne", "女装");
                intent.putExtra("cateTwo", "裙装");
                startActivity(intent);
            }

        });
        view1.findViewById(R.id.image_buy_quick).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("LogDemo", "buy quick");
                Intent intent = new Intent(getActivity(), ProductActivity.class);
                intent.putExtra("cateOne", "女装");
                intent.putExtra("cateTwo", "裙装");
                startActivity(intent);
            }

        });
    }

    @SuppressLint("NewApi")
    private void initData() {

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        ImageView iv0 = new ImageView(getActivity());
        iv0.setLayoutParams(mParams);
        iv0.setImageResource(pics[0]);
        iv0.setScaleType(ImageView.ScaleType.FIT_XY);
        iv0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("LogDemo", "first image");
                Intent intent = new Intent(getActivity(), ProductDetail.class);
                intent.putExtra("product_id", 20);
                startActivity(intent);

            }

        });

        ImageView iv1 = new ImageView(getActivity());
        iv1.setLayoutParams(mParams);
        iv1.setImageResource(pics[1]);
        iv1.setScaleType(ImageView.ScaleType.FIT_XY);
        iv1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("LogDemo", "second image");
                Intent intent = new Intent(getActivity(), ProductDetail.class);
                intent.putExtra("product_id", 21);
                startActivity(intent);
            }

        });

        ImageView iv2 = new ImageView(getActivity());
        iv2.setLayoutParams(mParams);
        iv2.setImageResource(pics[2]);
        iv2.setScaleType(ImageView.ScaleType.FIT_XY);
        iv2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.v("LogDemo", "third image");
                Intent intent = new Intent(getActivity(), ProductDetail.class);
                intent.putExtra("product_id", 21);
                startActivity(intent);
            }

        });
        views.add(iv0);
        views.add(iv1);
        views.add(iv2);

        viewPager.setAdapter(vpAdapter);

        viewPager.setOnPageChangeListener(this);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) view1.findViewById(R.id.ll_recommend);

        dots = new ImageView[views.size()];


        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);//����Ϊ��ɫ  
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//����Ϊ��ɫ����ѡ��״̬  
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    public void onPageSelected(int position) {

        setCurDot(position);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        viewPager.setCurrentItem(position);
    }

    private void setCurDot(int positon) {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }
        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = positon;
    }
}	

