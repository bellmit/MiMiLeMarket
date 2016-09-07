package com.huadi.shoppingmall.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.activity.AddAddressActivity;
import com.huadi.shoppingmall.model.Address;
import com.huadi.shoppingmall.util.CommonAdapter;
import com.huadi.shoppingmall.util.CommonViewHolder;

import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by smartershining on 16-7-19.
 */

public class AddressAdapter extends CommonAdapter<Address> {


    public AddressAdapter(Context context, List<Address> data, int layoutId) {
        super(context, data, layoutId);
    }


    @Override
    public void setConverView(final CommonViewHolder myViewHolder, final Address address) {
        ((TextView) myViewHolder.getView(R.id.address_browse_item_name)).setText(address.getName());
        ((TextView) myViewHolder.getView(R.id.address_browse_item_phone)).setText(address.getPhone());
        ((TextView) myViewHolder.getView(R.id.address_browse_item_address)).setText(address.getAddress_info());
        ((Button) myViewHolder.getView(R.id.address_browse_item_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("确认删除此收货地址吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        address.delete(address.getObjectId(), new UpdateListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    for (int i = 0; i < data.size(); i++) {
                                                        if (data.get(i).getObjectId().equals(address.getObjectId())) {
                                                            data.remove(i);
                                                        }
                                                    }
                                                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();

                                                    notifyDataSetChanged();
                                                }
                                            }
                                        });


                                    }
                                }).setNegativeButton("取消", null).create()
                        .show();
            }


        });
        ((Button) myViewHolder.getView(R.id.address_browse_item_update)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddAddressActivity.class);

                intent.putExtra("isAdd", false);
                intent.putExtra("address", address);
                context.startActivity(intent);
            }
        });
    }


}
