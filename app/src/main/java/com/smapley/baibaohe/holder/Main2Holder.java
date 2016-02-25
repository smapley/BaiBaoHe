package com.smapley.baibaohe.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.baibaohe.Activity.Store;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.Main2Item;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

/**
 * Created by smapley on 2015/6/24.
 */
public class Main2Holder extends RecyclerView.ViewHolder {

    private ImageView pic;
    private TextView name;
    private TextView des;
    private View countView;
    private TextView addr;
    private TextView zmin;

    public Main2Holder(View view) {
        super(view);
        countView = view;
        pic = (ImageView) view.findViewById(R.id.main2_pic);
        name = (TextView) view.findViewById(R.id.main2_nm);
        des = (TextView) view.findViewById(R.id.main2_des);
        addr = (TextView) view.findViewById(R.id.main2_addr);
        zmin = (TextView) view.findViewById(R.id.main2_min);
    }

    public void setData(final Main2Item item, final Context context, GetBitmap getBitmap) {
        getBitmap.getBitmap(item.pic, pic);
        name.setText(item.nm);
        zmin.setText(item.zmin);
        des.setText(MyData.ToDBC(item.des));
        addr.setText(MyData.ToDBC(item.dpdz));
        countView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Store.class);
                intent.putExtra("user2", item.user2);
                intent.putExtra("type", 0);
                intent.putExtra("bian", MyData.BIAN);
                context.startActivity(intent);
            }
        });
    }

}