package com.smapley.baibaohe.holder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.smapley.baibaohe.Activity.ZheKou;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.SetHead;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

/**
 * Created by smapley on 2015/6/25.
 */
public class SetHeadHolder extends RecyclerView.ViewHolder {

    private EditText dis;
    private EditText min;
    private EditText max;
    private EditText min2;
    private EditText max2;
    private SetHead setHead;
    private LinearLayout layout_jilv;

    public SetHeadHolder(View view) {
        super(view);
        min = (EditText) view.findViewById(R.id.set_min);
        max = (EditText) view.findViewById(R.id.set_max);
        min2 = (EditText) view.findViewById(R.id.set_min2);
        max2 = (EditText) view.findViewById(R.id.set_max2);
        dis = (EditText) view.findViewById(R.id.set_dis);
        layout_jilv = (LinearLayout) view.findViewById(R.id.jilv);
    }

    public void setData(SetHead setHeadHead, final Context context, final boolean edit, GetBitmap getBitmap) {
        this.setHead = setHeadHead;
        layout_jilv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edit) {
                    Intent intent = new Intent(context, ZheKou.class);
                    intent.putExtra("user2", MyData.PHONE);
                    context.startActivity(intent);
                }
            }
        });
        min.setText(setHeadHead.min);
        max.setText(setHeadHead.max);
        min2.setText(setHeadHead.min);
        max2.setText(setHeadHead.max2);
        dis.setText(setHeadHead.des);
//        min.setEnabled(edit);
//        max.setEnabled(edit);
        dis.setEnabled(edit);
        if (edit) {
            dis.setBackgroundResource(R.drawable.textview_edge);
//            min.setBackgroundResource(R.drawable.textview_edge);
//            max.setBackgroundResource(R.drawable.textview_edge);
            dis.addTextChangedListener(new MyTextWatcher(0, setHeadHead));
//            min.addTextChangedListener(new MyTextWatcher(1, setHeadHead));
//            max.addTextChangedListener(new MyTextWatcher(2, setHeadHead));
        } else {
//            min.setBackgroundDrawable(null);
//            max.setBackgroundDrawable(null);
            dis.setBackgroundResource(R.drawable.textview_circle2);
        }
    }


    public class MyTextWatcher implements TextWatcher {
        private SetHead item;
        private int position;

        public MyTextWatcher(int position, SetHead item) {
            this.item = item;
            this.position = position;
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            switch (position) {
                case 0:
                    item.des = s.toString();
                    break;
                case 1:
                    item.min = s.toString();
                    min2.setText(item.min);
                    break;
                case 2:
                    item.max = s.toString();
                    break;
            }


        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
        }
    }
}

