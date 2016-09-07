package com.huadi.shoppingmall.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by smartershining on 16-7-14.
 */

public class Express extends BmobObject {

    private String order_id;
    private Date create_time;
    private String location;


    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
