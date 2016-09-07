package com.huadi.shoppingmall.Adapter;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.huadi.shoppingmall.R;
import com.huadi.shoppingmall.model.Comment;
import com.huadi.shoppingmall.model.User;
import com.huadi.shoppingmall.util.CommonAdapter;
import com.huadi.shoppingmall.util.CommonViewHolder;
import com.huadi.shoppingmall.util.DateUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by smartershining on 16-7-21.
 */

public class ProductCommentAdapter extends CommonAdapter<Comment> {

    public ProductCommentAdapter(Context context, List<Comment> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void setConverView(final CommonViewHolder myViewHolder, Comment comment) {
        Log.i("comment", String.valueOf(comment.getUser_id()));
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(comment.getUser_id(), new QueryListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                Log.i("user.Rank", String.valueOf(user.getRank()));
                Log.i("user.name", String.valueOf(user.getUsername()));

                Log.i("rank", String.valueOf(user.getRank()));

                ((TextView) myViewHolder.getView(R.id.product_comment_item_rank)).setText("T" + user.getRank());
                Log.i("rank", String.valueOf(user.getRank()));

                ((TextView) myViewHolder.getView(R.id.product_comment_item_name)).setText(user.getUsername().charAt(0) + "***");
            }
        });

        ((TextView) myViewHolder.getView(R.id.product_comment_item_comment)).setText(comment.getContent());
        TextView bottom  = ((TextView) myViewHolder.getView(R.id.product_comment_item_bottom));
        bottom.setText(comment.getCreatedAt());

    }
}
