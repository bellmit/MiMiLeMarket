package com.huadi.shoppingmall.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.activity.LogisticsActivity;
import com.huadi.shoppingmall.activity.Pay;
import com.huadi.shoppingmall.activity.PublishComment;
import com.huadi.shoppingmall.model.Order;
import com.huadi.shoppingmall.model.Product;
import com.huadi.shoppingmall.util.CommonAdapter;
import com.huadi.shoppingmall.util.CommonViewHolder;
import com.huadi.shoppingmall.util.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by smartershining on 16-7-19.
 */

public class OrderAdapter extends CommonAdapter<Order> {

    public boolean[] isChecked = new boolean[data.size()];
    private int status;

    private Callback mCallback;


    public interface Callback {
        public void click(View v);
    }


    public OrderAdapter(Context context, List<Order> data, int layoutId, int status,Callback callback) {
        super(context, data, layoutId);
        this.status = status;
        mCallback = callback;
    }


    @Override
    public void setConverView(final CommonViewHolder myViewHolder, final Order order) {

        String product_id = order.getProduct_id();

        BmobQuery<Product> query = new BmobQuery<>();
        query.getObject(product_id, new QueryListener<Product>() {
            @Override
            public void done(Product product, BmobException e) {
                if (e == null) {
                    ((TextView) myViewHolder.getView(R.id.order_item_name)).setText(product.getName());
                    TextView colorSize = (TextView) myViewHolder.getView(R.id.order_item_colorSize);

                    ((TextView) myViewHolder.getView(R.id.order_item_brand_name)).setText(product.getBrand());
                    ((TextView) myViewHolder.getView(R.id.order_item_top_price)).setText("￥" + product.getPrice());

                        product.getImage().download(new DownloadFileListener() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                ((ImageView) myViewHolder.getView(R.id.order_item_image)).setImageBitmap(ImageUtil.getLoacalBitmap(s));
                            }
                        }

                        @Override
                        public void onProgress(Integer integer, long l) {

                        }
                    });
                }
            }
        });





        ((TextView) myViewHolder.getView(R.id.order_item_top_number)).setText("×" + order.getNumber());
        double price = order.getPrice();
        ((TextView) myViewHolder.getView(R.id.order_item_middle)).setText("共" + order.getNumber() + "件商品 合计￥" + String.format("%.2f", price));


        final Button left = (Button) myViewHolder.getView(R.id.order_item_bottom_left);
        Button middle = (Button) myViewHolder.getView(R.id.order_item_bottom_middle);
        Button right = (Button) myViewHolder.getView(R.id.order_item_bottom_right);
        RadioButton radio = (RadioButton) myViewHolder.getView(R.id.order_item_check);


        if (status == 1) {

            ((TextView) myViewHolder.getView(R.id.order_item_statue)).setText("买家已付款");
            radio.setVisibility(View.INVISIBLE);
            right.setText("提醒发货");
            left.setVisibility(View.INVISIBLE);
            middle.setVisibility(View.INVISIBLE);


        }
        if (status == 2) {
            ((TextView) myViewHolder.getView(R.id.order_item_statue)).setText("卖家已发货");
            radio.setVisibility(View.INVISIBLE);
            left.setText("延长收货");


            middle.setText("查看物流");
            middle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, LogisticsActivity.class);
                    intent.putExtra("order_id", order.getObjectId());
                    context.startActivity(intent);
                }
            });

            right.setText("确认收货");
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    order.setStatus(3);
                    order.update(order.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {

                        }
                    });

                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getObjectId().equals(order.getObjectId())) {
                            data.remove(i);
                        }
                    }
                    notifyDataSetChanged();
                }
            });

        } else if (status == 3) {
            ((TextView) myViewHolder.getView(R.id.order_item_statue)).setText("交易成功");
            radio.setVisibility(View.INVISIBLE);

            ((TextView) myViewHolder.getView(R.id.order_item_statue)).setText("卖家已发货");
            radio.setVisibility(View.INVISIBLE);
            left.setText("删除订单");
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("确认删除此订单吗？").setMessage("删除之后可以从电脑端订单回收站恢复")
                            .setPositiveButton("确定",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            order.delete(order.getObjectId(), new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {

                                                }
                                            });
                                            for (int i = 0; i < data.size(); i++) {
                                                if (data.get(i).getObjectId().equals(order.getObjectId())) {
                                                    data.remove(i);
                                                }
                                            }
                                            Toast.makeText(context, "删除订单成功", Toast.LENGTH_SHORT).show();
                                            notifyDataSetChanged();

                                        }
                                    }).setNegativeButton("取消", null).create()
                            .show();
                }
            });

            middle.setText("查看物流");
            middle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, LogisticsActivity.class);
                    intent.putExtra("order_id", order.getObjectId());
                    context.startActivity(intent);
                }
            });

            right.setText("评价");
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PublishComment.class);
                    intent.putExtra("order_id", order.getObjectId());
                    Log.i("order_id", String.valueOf(order.getObjectId()));
                    context.startActivity(intent);

                }
            });
        } else if (status == 0) {
            ((TextView) myViewHolder.getView(R.id.order_item_statue)).setText("等待付款");
            left.setText("朋友代付");
            left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            middle.setText("取消订单");
            middle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(context)
                            .setTitle("请选择取消订单的理由")
                            .setItems(R.array.dialog_arrays,
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            Order order = new Order();
                                            order.delete(order.getObjectId(), new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {

                                                }
                                            });
                                            for (int i = 0; i < data.size(); i++) {
                                                if (data.get(i).getObjectId().equals(order.getObjectId())) {
                                                    data.remove(i);
                                                }
                                            }
                                            Toast.makeText(context, "取消订单成功", Toast.LENGTH_SHORT).show();
                                            notifyDataSetChanged();

                                        }
                                    }).create().show();

                }
            });

            right.setText("付款");
            right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<Order> list = new ArrayList<Order>();
                    list.add(order);

                    Intent intent = new Intent(context, Pay.class);
                    intent.putExtra("order", list);
                    intent.putExtra("from", "unpay");
                    context.startActivity(intent);

                }
            });

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getObjectId().equals(order.getObjectId()) && isChecked[i]) {
                    radio.toggle();
                }
            }

            radio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getObjectId().equals(order.getObjectId())) {
                            isChecked[i] = !isChecked[i];
                            ((RadioButton) myViewHolder.getView(R.id.order_item_check)).setChecked(isChecked[i]);
                            Log.i("position", String.valueOf(i));
                            Log.i("isSelected", String.valueOf(isChecked[i]));
                        }
                    }
                    mCallback.click(view);
                }
            });


        }
    }

    public boolean[] getIsChecked() {
        return isChecked;
    }
}

