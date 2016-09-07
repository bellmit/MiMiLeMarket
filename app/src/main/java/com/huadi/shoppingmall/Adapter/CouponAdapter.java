package com.huadi.shoppingmall.Adapter;

import android.content.Context;
import android.widget.TextView;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.Coupon;
import com.huadi.shoppingmall.util.CommonAdapter;
import com.huadi.shoppingmall.util.CommonViewHolder;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by smartershining on 16-7-19.
 */

public class CouponAdapter  extends CommonAdapter<Coupon> {

    public CouponAdapter(Context context, List<Coupon> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void setConverView(CommonViewHolder myViewHolder, Coupon coupon) {
        ((TextView) myViewHolder.getView(R.id.my_coupon_item_name)).setText(coupon.getCoupon_info());

        Date date = DateUtil.add(DateUtil.stringToDate(coupon.getCreatedAt()) , coupon.getLast().intValue());
        ((TextView)myViewHolder.getView(R.id.my_coupon_item_date)).setText("有效期至" + DateUtil.dateToString(date));
        ((TextView) myViewHolder.getView(R.id.my_coupon_item_money)).setText("￥" + String.valueOf(coupon.getCoupon_sum()));

    }
}
