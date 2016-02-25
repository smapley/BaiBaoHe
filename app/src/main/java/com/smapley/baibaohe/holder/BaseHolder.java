package com.smapley.baibaohe.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.smapley.baibaohe.mode.MainBase;

/**
 * Created by smapley on 2015/7/11.
 */
public abstract class BaseHolder extends RecyclerView.ViewHolder {

    public BaseHolder(View view) {
        super(view);
    }

    public abstract MainBase getData();
}
