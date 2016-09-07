package com.huadi.shoppingmall.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.huadi.shoppingmall.Adapter.ShopCarAdapter;
import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.activity.Login;
import com.huadi.shoppingmall.activity.Pay;

import com.huadi.shoppingmall.model.Car;
import com.huadi.shoppingmall.model.Order;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.model.ShopCar;

import java.util.ArrayList;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;


public class ShopcarContentFragment extends Fragment {

    private ListView listView;
    private ShopCarAdapter adapter;                          //数据显示适配器
    private int checkNum;                             //设置选中
    private boolean[] isChecked;                       //记录哪些产品被选中
    private Double price;                             //记录商品的单价
    private View footView;
    private String user_id;
    List<ShopCar> shopCars;
    List<Car> cars;
    private Button select_all;
    private Button balance;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_shopcar_content, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        final SharedPreferences settings = getActivity().getSharedPreferences("setting", 0);
        user_id = settings.getString("user_id", "0");
        Log.i("onActivity", "is Called");
        Log.i("user_id", String.valueOf(user_id));
        init();
        if (user_id.equals("0")) {
            Log.i("Login ", "is Called");
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
        }

        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_all.setSelected(!select_all.isSelected());

                if (select_all.isSelected()) {
                    for (int i = 0; i < cars.size(); i++) {
                        adapter.isChecked[i] = true;
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    for (int i = 0; i < cars.size(); i++) {
                        adapter.isChecked[i] = false;
                        adapter.notifyDataSetChanged();
                    }
                }

            }
        });


        balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "balance is OnClicked", Toast.LENGTH_LONG).show();
                ArrayList<Integer> list = new ArrayList<Integer>();
                Log.i("cars.Id", String.valueOf(cars.get(0).getProduct_id()));
                Log.i("cars.price", String.valueOf(cars.get(0).getPrice()));
                double price = 0;
                ArrayList<Order> orders = new ArrayList<Order>();
                for (int i = 0; i < cars.size(); i++) {
                    if (adapter.isChecked[i] == true) {
                        Order order = new Order();
                        order.setObjectId(cars.get(i).getId());
                        order.setObjectId(cars.get(i).getId());
                        order.setNumber(cars.get(i).getNumber());

                        Log.i("ShopCarNumber", String.valueOf(cars.get(i).getNumber()));
                        order.setProduct_id(cars.get(i).getProduct_id());
                        orders.add(order);
                    }

                    Intent intent = new Intent(getActivity(), Pay.class);
                    intent.putExtra("order", orders);
                    intent.putExtra("from", "shop_car");
                    startActivity(intent);
                }
            }
        });



        listView.setDividerHeight(20);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.isChecked[i] = true;
                Log.i("ONItemClickListener", "Is clicked");
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void init() {
        listView = (ListView) getActivity().findViewById(R.id.shopCar_content_listView);
        select_all = (RadioButton) getActivity().findViewById(R.id.shopCar_content_footer_checkbox);
        balance = (Button) getActivity().findViewById(R.id.shopCar_content_footer_balance);

        cars = new ArrayList<>();
        BmobQuery<ShopCar> query = new BmobQuery<>();
        query.addWhereEqualTo("user_id", user_id);
        query.findObjects(new FindListener<ShopCar>() {
            @Override
            public void done(List<ShopCar> list, BmobException e) {
                if (e == null) {
                    adapter = new ShopCarAdapter(getActivity(), list, R.layout.activity_shopcar_content_item);
                    listView.setAdapter(adapter);
                }
            }
        });

    }

}

