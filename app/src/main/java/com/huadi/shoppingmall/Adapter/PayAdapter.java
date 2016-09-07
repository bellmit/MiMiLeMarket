package com.huadi.shoppingmall.Adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.util.CommonAdapter;
import com.huadi.shoppingmall.util.CommonViewHolder;
import com.huadi.shoppingmall.util.ImageUtil;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;

/**
 * Created by smartershining on 16-7-23.
 */

public class PayAdapter extends CommonAdapter<Product> {

    public PayAdapter(Context context, List<Product> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void setConverView(final CommonViewHolder myViewHolder, Product product) {

        product.getImage().download(new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ((ImageView) myViewHolder.getView(R.id.pay_item_image)).setImageBitmap(ImageUtil.getLoacalBitmap(s));
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });

        ((TextView) myViewHolder.getView(R.id.pay_item_name)).setText(product.getDetail());

        ((TextView) myViewHolder.getView(R.id.pay_item_num)).setText(String.valueOf(product.getSalNum()));

        ((TextView) myViewHolder.getView(R.id.pay_item_price)).setText("￥" + product.getPrice());
    }
}
