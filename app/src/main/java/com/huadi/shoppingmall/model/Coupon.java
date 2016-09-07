package com.huadi.shoppingmall.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by smartershining on 16-7-14.
 */

public class Coupon extends BmobObject {


    private Double last;
    private String user_id;
    private String coupon_info;

    private Double coupon_sum;

    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCoupon_info() {
        return coupon_info;
    }

    public void setCoupon_info(String coupon_info) {
        this.coupon_info = coupon_info;
    }

    public Double getCoupon_sum() {
        return coupon_sum;
    }

    public void setCoupon_sum(Double coupon_sum) {
        this.coupon_sum = coupon_sum;
    }
}
