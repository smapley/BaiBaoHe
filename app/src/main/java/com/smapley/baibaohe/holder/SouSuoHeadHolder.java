package com.smapley.baibaohe.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.SouSuoHead;

/**
 * Created by smapley on 2015/6/25.
 */
public class SouSuoHeadHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public SouSuoHeadHolder(View view) {
        super(view);
        textView = (TextView) view.findViewById(R.id.sousuo_text);
    }

    public void setData(final SouSuoHead mainHead) {
        textView.setText(mainHead.text);
    }


}

