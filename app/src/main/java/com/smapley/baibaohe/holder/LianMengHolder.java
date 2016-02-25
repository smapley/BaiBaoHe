package com.smapley.baibaohe.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smapley.baibaohe.Activity.Store;
import com.smapley.baibaohe.Adapter.ViewPageAdapter;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.LianMengItem;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 2015/6/24.
 */
public class LianMengHolder extends RecyclerView.ViewHolder {

    private ImageView pic;
    private TextView name;
    private TextView des;
    private TextView addr;
    private View countView;
    private TextView yinke;
    private ViewPager viewPager;

    public LianMengHolder(View view) {
        super(view);
        countView = view;
        viewPager = (ViewPager) view.findViewById(R.id.lianmeng_item_pager);

    }

    public void setData(final LianMengItem item, final Context context, GetBitmap getBitmap) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_main2_item, null,false);
        List list = new ArrayList();
        list.add(view);
        ViewPageAdapter adapter = new ViewPageAdapter(list);
        viewPager.setAdapter(adapter);


        pic = (ImageView) view.findViewById(R.id.main2_pic);
        name = (TextView) view.findViewById(R.id.main2_nm);
        des = (TextView) view.findViewById(R.id.main2_des);
        addr = (TextView) view.findViewById(R.id.main2_addr);
        yinke = (TextView) view.findViewById(R.id.main2_yinke);
        getBitmap.getBitmap(item.pic, pic);
        name.setText(item.nm);
        yinke.setVisibility(View.VISIBLE);
        yinke.setText(context.getString(R.string.yinke) + item.cs);
        des.setText(MyData.ToDBC(item.des));
        addr.setText(item.dpdz);
        countView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Store.class);
                intent.putExtra("user2", item.user2);
                intent.putExtra("type", 1);
                intent.putExtra("bian", MyData.BIAN);
                context.startActivity(intent);
            }
        });
    }

}
