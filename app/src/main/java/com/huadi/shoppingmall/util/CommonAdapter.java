package com.huadi.shoppingmall.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 通用适配器Adapter写法
 *
 * @param <T>
 *
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    //为了使得子类可以访问，这里修改包访问级别
    protected Context context;
    protected LayoutInflater layoutInflater;
    protected List<T> data;
    protected int layoutId;

    public CommonAdapter(Context context, List<T> data, int layoutId) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取ViewHolder对象
        CommonViewHolder myViewHolder = new CommonViewHolder(context, convertView, layoutId, parent, position);
        //需要用户复写的方法，设置所对于的View所对应的数据
        setConverView(myViewHolder,data.get(position));
        return myViewHolder.getConvertView();
    }

    //用户需要实现的方法
    public abstract void setConverView(CommonViewHolder myViewHolder, T t);

}