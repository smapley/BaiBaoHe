package com.smapley.baibaohe.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Activity.FuKuan;
import com.smapley.baibaohe.Activity.SouSuo;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/5/17.
 */
public class FuKuanJiLuAdapter extends BaseAdapter {
    /**
     * 得到一个LayoutInfalter对象用来导入布局
     */
    private LayoutInflater mInflater;
    private Context context;
    /**
     * list的数据
     */
    private List<Map> listitem;
    private final int UPDATA = 1;
    private ProgressDialog progressDialog;
    private final int PINGJIA = 2;

    /**
     * 构造函数
     */
    public FuKuanJiLuAdapter(Context context,
                             List listitem) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listitem = listitem;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public int getCount() {
        return listitem.size();// 返回数组的长度

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;


        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.layout_fukuan_item, parent, false);
            holder.jine = (TextView) convertView.findViewById(R.id.fukuan_item1);
            holder.zhekou = (TextView) convertView.findViewById(R.id.fukuan_item2);
            holder.shishou = (TextView) convertView.findViewById(R.id.fukuan_item3);
            holder.name = (TextView) convertView.findViewById(R.id.fukuan_name);
            holder.riqi = (TextView) convertView.findViewById(R.id.fukuan_riqi);
            holder.item1 = (TextView) convertView.findViewById(R.id.fukuan_fukuanmima);
            holder.item2 = (TextView) convertView.findViewById(R.id.fukuan_choujiangquan);
            holder.zhuangtai = (TextView) convertView.findViewById(R.id.fukuan_zhuangtai);
            holder.qzt = (TextView) convertView.findViewById(R.id.fukuan_qzt);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Log.i("asdf", "_____________________________________+" + listitem.get(position).get("qzt"));
        if (listitem.get(position).get("qzt") != null) {
            holder.qzt.setVisibility(View.VISIBLE);
            holder.qzt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SouSuo.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("bian", listitem.get(position).get("quan").toString());
                    context.startActivity(intent);
                }
            });

        } else {
            holder.qzt.setVisibility(View.GONE);

        }


        holder.jine.setText(listitem.get(position).get("gold").toString());
        holder.zhekou.setText(listitem.get(position).get("zk").toString());
        holder.shishou.setText(listitem.get(position).get("sgold").toString());
        holder.name.setText(listitem.get(position).get("snm").toString());
        holder.riqi.setText(listitem.get(position).get("tm").toString());
        String mima = listitem.get(position).get("fumima").toString();
        holder.item1.setText(mima.substring(0, 4) + " " + mima.substring(4, 8));
        holder.item2.setText(listitem.get(position).get("quan").toString());

        holder.item2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cbm = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                cbm.setText(holder.item2.getText().toString());
                Toast.makeText(context, R.string.fuzhied, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        int zt = Integer.parseInt(listitem.get(position).get("zt").toString());
        switch (zt) {
            case 0:
                holder.zhuangtai.setText(R.string.fukuan_zhuangtai1);
                holder.zhuangtai.setTextColor(Color.WHITE);
                holder.zhuangtai.setBackgroundResource(R.drawable.textview_circle_chengse);
                holder.zhuangtai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(R.string.tips);
                        builder.setMessage(R.string.tuikuan);
                        builder.setNegativeButton(R.string.cancel, null);
                        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HashMap map = new HashMap();
                                        map.put("fuid", listitem.get(position).get("fuid").toString());
                                        map.put("phone", MyData.PHONE);
                                        map.put("fumima", listitem.get(position).get("fumima").toString());
                                        mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_UPDATETUIKUAN)).sendToTarget();
                                    }
                                }).start();
                            }
                        });
                        builder.create().show();
                    }
                });
                break;
            case 1:
                holder.zhuangtai.setText(R.string.fukuan_zhuangtai2);
                holder.zhuangtai.setTextColor(Color.WHITE);
                holder.zhuangtai.setBackgroundResource(R.drawable.textview_circle_chengse);
                holder.zhuangtai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View view = inflater.inflate(R.layout.layout_pingjia, null, false);
                        final EditText editText = (EditText) view.findViewById(R.id.editext);
                        builder.setView(view);
                        builder.setNegativeButton(R.string.chaping, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String data = editText.getText().toString();
                                    int num = Integer.parseInt(data);
                                    if (num > 0) {
                                        pingJia(num, 2,Integer.parseInt(listitem.get(position).get("fuid").toString()));
                                    }
                                } catch (Exception e) {

                                }
                            }
                        });
                        builder.setPositiveButton(R.string.haoping, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    String data = editText.getText().toString();
                                    int num = Integer.parseInt(data);
                                    if (num > 0) {
                                        pingJia(num, 1,Integer.parseInt(listitem.get(position).get("fuid").toString()));
                                    }
                                } catch (Exception e) {

                                }
                            }
                        });
                        builder.create().show();
                    }
                });
                break;
            case 2:
                holder.zhuangtai.setText(R.string.fukuan_zhuangtai3);
                holder.zhuangtai.setTextColor(Color.RED);
                holder.zhuangtai.setBackgroundDrawable(null);
                break;
            default:
                holder.zhuangtai.setText(R.string.fukuan_zhuangtai4);
                holder.zhuangtai.setTextColor(Color.RED);
                holder.zhuangtai.setBackgroundDrawable(null);
                break;

        }

        return convertView;

    }

    private void pingJia(final int num, final int pingjia, final int fuid) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("lx", pingjia);
                map.put("dyid", num);
                map.put("fuid", fuid);
                mhandler.obtainMessage(PINGJIA,HttpUtils.updata(map,MyData.URL_PING)).sendToTarget();
            }
        }).start();
    }

    class ViewHolder {
        TextView jine;
        TextView zhekou;
        TextView shishou;
        TextView name;
        TextView riqi;
        TextView item1;
        TextView item2;
        TextView zhuangtai;
        TextView qzt;
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                progressDialog.dismiss();
                switch (msg.what) {
                    case UPDATA:
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("count").toString()) > 0) {
                            ((FuKuan) context).getData();
                        } else {
                            Toast.makeText(context, R.string.tuikuanfile, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case PINGJIA:
                        ((FuKuan) context).getData();
                        break;
                }

            } catch (Exception e) {
                Toast.makeText(context, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }

        }

    };
}

