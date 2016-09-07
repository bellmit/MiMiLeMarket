package com.huadi.shoppingmall.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by smartershining on 16-7-14.
 */

public class Product extends BmobObject{

    private String name;
    private Double price;
    private String title;
    private BmobFile image;
    private String detail;
    private Integer    salNum;
    private Integer    stock;
    private String city;
    private String brand;
    private String cateOne;
    private String cateTwo;
    private String color;
    private String Size;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getSalNum() {
        return salNum;
    }

    public void setSalNum(Integer salNum) {
        this.salNum = salNum;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCateOne() {
        return cateOne;
    }

    public void setCateOne(String cateOne) {
        this.cateOne = cateOne;
    }

    public String getCateTwo() {
        return cateTwo;
    }

    public void setCateTwo(String cateTwo) {
        this.cateTwo = cateTwo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

}
