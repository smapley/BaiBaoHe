package com.smapley.baibaohe.holder;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Activity.Goods;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.SetItem;
import com.smapley.baibaohe.newAdapter.SetNewAdapter;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smapley on 2015/6/24.
 */
public class SetHolder extends RecyclerView.ViewHolder {

    private ImageView pic;
    private EditText name;
    private EditText shuliang;
    private EditText jilv;
    private TextView yizhong;
    private ImageView dele;
    private View countView;
    private SetItem item;
    private final int DELECT = 1;
    private ProgressDialog progressDialog;
    private Context context;
    private SetNewAdapter adapter;
    private int nowposition;

    public SetHolder(View view) {
        super(view);
        countView = view;
        pic = (ImageView) view.findViewById(R.id.set_item_pic);
        name = (EditText) view.findViewById(R.id.set_item_name);
        shuliang = (EditText) view.findViewById(R.id.set_item_shuliang);
        jilv = (EditText) view.findViewById(R.id.set_item_jilv);
        yizhong = (TextView) view.findViewById(R.id.set_item_yz);
        dele = (ImageView) view.findViewById(R.id.set_item_delect);
    }

    public void setData(SetItem item, final boolean edit, final boolean delect, final Context context, GetBitmap getBitmap, int nowposition, SetNewAdapter adapter) {
        this.context = context;
        this.item = item;
        this.nowposition = nowposition;
        this.adapter = adapter;
        final String picid = item.picid;
        getBitmap.getBitmap(item.pic, pic);
        name.setText(item.ming);
        shuliang.setText(item.sl);
        jilv.setText(item.jilv);
        yizhong.setText(item.yz);

        name.setEnabled(edit);
        shuliang.setEnabled(edit);
        jilv.setEnabled(edit);

        if (edit) {
            name.setBackgroundResource(R.drawable.textview_edge);
            shuliang.setBackgroundResource(R.drawable.textview_edge);
            jilv.setBackgroundResource(R.drawable.textview_edge);

            name.addTextChangedListener(new MyTextWatcher(0, item));
            shuliang.addTextChangedListener(new MyTextWatcher(1, item));
            jilv.addTextChangedListener(new MyTextWatcher(2, item));

        } else {
            name.setBackgroundDrawable(null);
            shuliang.setBackgroundDrawable(null);
            jilv.setBackgroundDrawable(null);
        }

        if (delect) {
            dele.setVisibility(View.VISIBLE);
        } else {
            dele.setVisibility(View.GONE);

        }

        dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.tips);
                builder.setMessage(R.string.delect);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HashMap map = new HashMap();
                                map.put("picid", picid);
                                mhandler.obtainMessage(DELECT, HttpUtils.updata(map, MyData.URL_DELPIC)).sendToTarget();
                            }
                        }).start();
                    }
                });
                builder.create().show();

            }
        });

        countView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edit && !delect) {
                    Intent intent = new Intent(context, Goods.class);
                    intent.putExtra("picid", picid);
                    context.startActivity(intent);
                }
            }
        });
    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case DELECT:
                        progressDialog.dismiss();
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("count").toString()) > 0) {
                            Toast.makeText(context, R.string.delected, Toast.LENGTH_SHORT).show();
                            adapter.listitem.remove(nowposition);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, R.string.delectfaile, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            } catch (Exception e) {
                try {
                    Toast.makeText(context, R.string.connectfile, Toast.LENGTH_SHORT).show();
                } catch (Exception e1) {

                }
            }
        }
    };

    public class MyTextWatcher implements TextWatcher {
        private SetItem item;
        private int position;

        public MyTextWatcher(int position, SetItem item) {
            this.item = item;
            this.position = position;
        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            switch (position) {
                case 0:
                    item.ming = s.toString();
                    break;
                case 1:
                    item.sl = s.toString();
                    break;
                case 2:
                    item.jilv = s.toString();
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
