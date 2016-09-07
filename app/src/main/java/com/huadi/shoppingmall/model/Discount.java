package com.huadi.shoppingmall.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by smartershining on 16-7-14.
 */

public class Discount extends BmobObject {

    private String name;
    private double discount_info;
    private Double last;
    private String    product_id;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount_info() {
        return discount_info;
    }

    public void setDiscount_info(double discount_info) {
        this.discount_info = discount_info;
    }


    public Double getLast() {
        return last;
    }

    public void setLast(Double last) {
        this.last = last;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
