package com.huadi.shoppingmall.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import java.util.HashMap;
import java.util.List;

/**
 * Created by smartershining on 16-7-17.
 */

public abstract class Common<T> extends BaseAdapter implements View.OnClickListener
{
    public LayoutInflater mInflater;
    public Context mContext;
    public List<T> mDatas;
    public final int mItemLayoutId;

    public boolean[] isSelected ;
    private Call mCallback;

    public interface Call {
        public void click(View v);
    }


    public Common(Context context, List<T> mDatas, int itemLayoutId, Call callback)
    {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
        isSelected = new boolean[mDatas.size()];
        this.mCallback = callback;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public T getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();

    }

    public abstract void convert(ViewHolder helper, T item);

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent)
    {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }

    public  boolean[] getIsSelected() {

        return isSelected;
    }

    public void setIsSelected(boolean[]  isSelected) {
        this.isSelected = isSelected;
    }

    public void initDate() {
        for (int i = 0; i < mDatas.size(); i++) {
            isSelected[i] = false;
        }
    }

    public void onClick(View v) {

        mCallback.click(v);
    }

}