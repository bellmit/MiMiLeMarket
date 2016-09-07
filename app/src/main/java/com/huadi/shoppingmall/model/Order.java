package com.huadi.shoppingmall.model;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by smartershining on 16-7-14.
 */

public class Order extends BmobObject {

    private String     user_id;
    private Date    order_time;
    private Integer     status;
    private double  price;
    private String     address_id;
    private String     product_id;
    private Integer     number;



    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public int  getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
