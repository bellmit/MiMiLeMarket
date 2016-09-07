package com.huadi.shoppingmall.model;

import java.io.Serializable;
import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by smartershining on 16-7-14.
 */

public class Comment extends BmobObject {

    private String user_id;
    private String product_id;
    private String order_id;
    private Integer star_level;
    private String content;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStar_level() {
        return star_level;
    }

    public void setStar_level(Integer star_level) {
        this.star_level = star_level;
    }
}
