package com.smapley.baibaohe.holder;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.smapley.baibaohe.Activity.Store;
import com.smapley.baibaohe.Adapter.ViewPageAdapter;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.MainHead;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smapley on 2015/6/25.
 */
public class MainHeadHolder extends RecyclerView.ViewHolder {

    ViewPager viewpager;

    public MainHeadHolder(View view) {
        super(view);
        viewpager = (ViewPager) view.findViewById(R.id.headviewpager);
    }

    public void setData(final MainHead mainHead, final Context context,GetBitmap getBitmap) {
        List<ImageView> listtemp = new ArrayList<ImageView>();
        for (int i = 0; i < mainHead.list.size(); i++) {
            ImageView img = new ImageView(context);
            img.setLayoutParams(new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, 150));
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            img.setImageResource(R.drawable.empty_photo);
            listtemp.add(img);
            getBitmap.getBitmap(mainHead.list.get(i).pic.toString(), img);
            final int position = i;
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Store.class);
                    intent.putExtra("user2", mainHead.list.get(position).user2);
                    intent.putExtra("type", 0);
                    context.startActivity(intent);
                }
            });
        }
        ViewPageAdapter viewadapter = new ViewPageAdapter(listtemp);
        viewpager.setAdapter(viewadapter);
    }

}

