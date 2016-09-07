package com.huadi.shoppingmall.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.ShopCar;
import com.huadi.shoppingmall.util.CommonAdapter;
import com.huadi.shoppingmall.util.CommonViewHolder;
import com.huadi.shoppingmall.util.ImageUtil;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

/**
 * Created by smartershining on 16-7-19.
 */

public class ShopCarAdapter extends CommonAdapter<ShopCar> {

    public boolean[] isChecked = new boolean[data.size()];


    public ShopCarAdapter(Context context, List<ShopCar> data, int layoutId) {
        super(context, data, layoutId);
        for (int i = 0; i < data.size(); i++) {
            isChecked[i] = false;
        }
    }

    public void setConverView(final CommonViewHolder myViewHolder, final ShopCar car) {
        ((TextView) myViewHolder.getView(R.id.shopCar_content_item_brand)).setText(car.getBrand());
        ((TextView) myViewHolder.getView(R.id.shopCar_content_item_name)).setText(car.getName());
        ((TextView) myViewHolder.getView(R.id.shopCar_content_item_top_price)).setText(String.valueOf(car.getPrice()));
        ((TextView) myViewHolder.getView(R.id.shopCar_content_item_top_number)).setText("×" + String.valueOf(car.getNumber()));

        RadioButton radio = myViewHolder.getView(R.id.shopCar_content_item_check);

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getObjectId() == car.getObjectId() && isChecked[i]) {
                radio.toggle();
            }
        }

        radio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getObjectId() == car.getObjectId()) {
                        Log.i("data.get(i).getId",String .valueOf(data.get(i).getObjectId()));
                        Log.i("car.getId", String.valueOf(car.getObjectId()));
                        isChecked[i] = !isChecked[i];
                        ((RadioButton) myViewHolder.getView(R.id.shopCar_content_item_check)).setChecked(isChecked[i]);
                        Log.i("position", String.valueOf(i));
                        Log.i("isSelected", String.valueOf(isChecked[i]));
                    }
                }

            }
        });

        car.getImage().download(new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ((ImageView) myViewHolder.getView(R.id.shopCar_content_item_image)).setImageBitmap(ImageUtil.getLoacalBitmap(s));
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
    }
}
