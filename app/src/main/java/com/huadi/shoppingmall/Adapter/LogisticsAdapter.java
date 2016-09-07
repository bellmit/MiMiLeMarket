package com.huadi.shoppingmall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.Express;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.List;

/**
 * Created by smartershining on 16-7-22.
 */

public class LogisticsAdapter extends ArrayAdapter<Express> {
    private int resourceId;

    public LogisticsAdapter(Context context, int textViewResourceId,
                            List<Express> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override

    public View getView(int position, View convertView, ViewGroup parent)
    {

        Express logistic = getItem(position); // 获取当前项的Logistics实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);

        ImageView logistic_Image = (ImageView) view.findViewById(R.id.logistics_info_item_image);
        TextView logistic_Name = (TextView) view.findViewById(R.id.logistics_info_item_location);
        TextView logistic_Time = (TextView) view.findViewById(R.id.logistics_info_item_time);



        int resId = getContext().getResources().getIdentifier("logistics_bigpoint", "drawable", getContext().getPackageName());
        logistic_Image.setImageResource(resId);


        logistic_Name.setText("你的包裹已到达" + logistic.getLocation());
        logistic_Time.setText(DateUtil.dateToString(logistic.getCreate_time()));


        return view;
    }

}
