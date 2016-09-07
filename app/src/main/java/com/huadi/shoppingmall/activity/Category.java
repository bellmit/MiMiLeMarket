package com.huadi.shoppingmall.activity;

import java.util.ArrayList;
import java.util.List;

import com.huadi.shoppingmall.Adapter.ViewPagerAdapter;
import com.huadi.shoppingmall.MainActivity;
import com.huadi.shoppingmall.R;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Category extends Activity implements OnClickListener, OnPageChangeListener {

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView back;


    private ImageView[] dots;

    ImageView test;
    private ImageView[] image_category_two;

    private int currentIndex;

    private  String cateOne[] = new String[]{"生活服务", "女装","男装", "食品"};
    private  String cateTwo[][] = new String[][] {
            {"家庭保洁", "保姆月嫂","汽车服务", "健康服务", "搬家搬运", "维修服务","维修服务"},
            {"裙装","上装","裤装","妈妈装","运动装","职业套装"},
            {"内搭","外套","下装","大码","中老年","情侣装"},
            {"当季推荐", "休闲零食", "水产生鲜", "茶酒冲饮", "粮油干货","保健滋补"}
    };

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        views = new ArrayList<View>();
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        initViews();

        initDots();

    }

    private void initViews() {
        final LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        View v1 = getLayoutInflater().inflate(R.layout.lifeservice, null);
        back = (ImageView) findViewById(R.id.category_title_image);
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Category.this, MainActivity.class);
                intent.putExtra("choice", 0);
                startActivity(intent);
            }
        });


        v1.findViewById(R.id.category_level_11).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Log.i("v1", "onClicked");
                helper(cateOne[0], cateTwo[0][0]);

            }

        });

        v1.findViewById(R.id.category_level_12).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                helper(cateOne[0], cateTwo[0][1]);

            }

        });

        v1.findViewById(R.id.category_level_13).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[0], cateTwo[0][2]);
            }

        });

        v1.findViewById(R.id.category_level_14).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                helper(cateOne[0], cateTwo[0][3]);
            }

        });

        v1.findViewById(R.id.category_level_15).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                helper(cateOne[0], cateTwo[0][4]);
            }

        });

        v1.findViewById(R.id.category_level_16).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                helper(cateOne[0], cateTwo[0][5]);
            }

        });

        View v2 = getLayoutInflater().inflate(R.layout.womenclothes, null);

        v2.findViewById(R.id.category_level_21).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[1], cateTwo[1][0]);
            }

        });

        v2.findViewById(R.id.category_level_22).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[1], cateTwo[1][1]);
            }

        });

        v2.findViewById(R.id.category_level_23).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[1], cateTwo[1][2]);
            }

        });

        v2.findViewById(R.id.category_level_24).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[1], cateTwo[1][3]);
            }

        });

        v2.findViewById(R.id.category_level_25).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[1], cateTwo[1][4]);
            }

        });

        v2.findViewById(R.id.category_level_26).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[1], cateTwo[1][5]);
            }

        });
        View v3 = getLayoutInflater().inflate(R.layout.manclothes, null);

        v3.findViewById(R.id.category_level_31).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[2], cateTwo[2][0]);
            }

        });

        v3.findViewById(R.id.category_level_32).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[2], cateTwo[2][1]);

            }

        });

        v3.findViewById(R.id.category_level_33).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[2], cateTwo[2][2]);

            }

        });

        v3.findViewById(R.id.category_level_34).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[2], cateTwo[2][3]);

            }

        });

        v3.findViewById(R.id.category_level_35).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[2], cateTwo[2][4]);

            }

        });

        v3.findViewById(R.id.category_level_36).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[2], cateTwo[2][5]);

            }

        });
        View v4 = getLayoutInflater().inflate(R.layout.food, null);

        v4.findViewById(R.id.category_level_41).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[3], cateTwo[3][0]);

            }

        });

        v4.findViewById(R.id.category_level_42).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[3], cateTwo[3][1]);

            }

        });

        v4.findViewById(R.id.category_level_43).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[3], cateTwo[3][2]);

            }

        });

        v4.findViewById(R.id.category_level_44).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[3], cateTwo[3][3]);

            }

        });

        v4.findViewById(R.id.category_level_45).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[3], cateTwo[3][4]);

            }

        });

        v4.findViewById(R.id.category_level_46).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                helper(cateOne[3], cateTwo[3][5]);

            }

        });


        views.add(v1);
        views.add(v2);
        views.add(v3);
        views.add(v4);


        vpAdapter = new ViewPagerAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.viewpager_category);
        vp.setAdapter(vpAdapter);


        vp.setOnPageChangeListener(this);

    }

    //*****************ttoooooooo*************************
    private void initDots() {
        RelativeLayout category_relativelayout = (RelativeLayout) findViewById(R.id.category_relativelayout);

        dots = new ImageView[views.size()];
        ImageView[] image_category_two = new ImageView[24];


        dots[0] = (ImageView) findViewById(R.id.category_level_1);
        dots[0].setOnClickListener(this);
        dots[0].setTag(0);

        dots[1] = (ImageView) findViewById(R.id.category_level_2);
        dots[1].setOnClickListener(this);
        dots[1].setTag(1);

        dots[2] = (ImageView) findViewById(R.id.category_level_3);
        dots[2].setOnClickListener(this);
        dots[2].setTag(2);

        dots[3] = (ImageView) findViewById(R.id.category_level_4);
        dots[3].setOnClickListener(this);
        dots[3].setTag(3);


        currentIndex = 0;
        //dots[currentIndex].setEnabled(false);


        //test=(ImageView)findViewById(R.id.category_level_11);
        //test.setOnClickListener(this);

    }


    private void setCurView(int position) {
        if (position < 0 || position >= views.size()) {
            return;
        }

        vp.setCurrentItem(position);

//        test=(ImageView)findViewById(R.id.category_level_11);
//        test.setOnClickListener(new OnClickListener(){
//        	public void onClick(View v){
//        		Log.v("LogDemo", "tototototootototootottootot");
//        	}
//        });
    }


    private void setCurDot(int positon) {
        if (positon < 0 || positon > views.size() - 1 || currentIndex == positon) {
            return;
        }

        //dots[positon].setEnabled(false);  
        //dots[currentIndex].setEnabled(true);  

        currentIndex = positon;
    }


    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub  

    }


    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub  

    }


    @Override
    public void onPageSelected(int arg0) {

        setCurDot(arg0);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);

    }

    public void helper(String cateOne, String cateTwo) {
        Intent intent = new Intent(Category.this, ProductActivity.class);
        intent.putExtra("cateOne", cateOne);
        intent.putExtra("cateTwo", cateTwo);
        startActivity(intent);
    }



}