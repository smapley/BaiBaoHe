package com.smapley.baibaohe.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.baibaohe.Activity.Store;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.MainItem;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

/**
 * Created by smapley on 2015/6/24.
 */
public class MainHolder extends RecyclerView.ViewHolder {

    ImageView pic;
    TextView name;
    TextView shuliang;
    TextView yizhong;
    TextView yonghu;
    TextView yonghu2;
    View countView;

    public MainHolder(View view) {
        super(view);
        countView = view;
        pic = (ImageView) view.findViewById(R.id.main_pic);
        name = (TextView) view.findViewById(R.id.main_goods);
        shuliang = (TextView) view.findViewById(R.id.main_shuliang);
        yizhong = (TextView) view.findViewById(R.id.main_yizhong);
        yonghu = (TextView) view.findViewById(R.id.main_yonghu);
        yonghu2 = (TextView) view.findViewById(R.id.main_yonghu2);
    }

    public void setData(final MainItem item, final Context context, GetBitmap getBitmap) {
        getBitmap.getBitmap(item.pic, pic);
        name.setText(item.ming);
        shuliang.setText(item.sl);
        yizhong.setText(item.yz);
        yonghu.setText(item.snm);
        yonghu2.setText(item.nm);
        countView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Store.class);
                intent.putExtra("user2", item.user2);
                intent.putExtra("type", 0);
                context.startActivity(intent);
            }
        });
    }

}
