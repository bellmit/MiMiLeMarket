package com.huadi.shoppingmall.util;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewHolder集合类
 *
 *
 */
public class CommonViewHolder {
    private SparseArray<View> sparseArray;
    private View convertView;
    private int position;

    // 构造方法，完成传统Adapter里的创建convertView对象
    public CommonViewHolder(Context context, View convertView, int layoutId, ViewGroup parent, int position) {
        this.position = position;
        this.sparseArray = new SparseArray<View>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.convertView.setTag(this);

    }

    // 入口方法，完成传统Adapter里面实例化ViewHolder对象工作
    public static CommonViewHolder getCommonViewHolder(Context context, View convertView, int layoutId, ViewGroup parent, int position) {
        if (convertView == null) {
            return new CommonViewHolder(context, convertView, layoutId, parent, position);
        } else {
            CommonViewHolder commonViewHolder = (CommonViewHolder) convertView.getTag();
            //特别需要注意的一点，由于ListView的复用,比如屏幕只显示5个Item,那么当下拉到第6个时会复用第1个的Item，所以这边需要更新position
            commonViewHolder.position = position;
            return commonViewHolder;
        }
    }

    //根据控件Id获取对应View对象
    public <T extends View> T getView(int viewId) {
        View view = sparseArray.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            sparseArray.put(viewId, view);
        }
        return (T) view;
    }

    //用于返回设置好的ConvertView对象
    public View getConvertView(){
        return convertView;
    }

}