package com.smapley.baibaohe.Adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Activity.DuiJiang;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/5/17.
 */
public class ShouKuanAdapter extends BaseAdapter {
    /**
     * �õ�һ��LayoutInfalter�����������벼��
     */
    private LayoutInflater mInflater;
    private Context context;
    /**
     * list�����
     */
    private List<Map> listitem;
    private final int UPDATA = 1;
    private ProgressDialog progressDialog;

    /**
     * ���캯��
     */
    public ShouKuanAdapter(Context context,
                           List listitem) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listitem = listitem;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public int getCount() {
        return listitem.size();

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
            convertView = mInflater.inflate(R.layout.layout_shoukuan_item, parent, false);
            holder.jine = (TextView) convertView.findViewById(R.id.shoukuan_item1);
            holder.zhekou = (TextView) convertView.findViewById(R.id.shoukuan_item2);
            holder.shishou = (TextView) convertView.findViewById(R.id.shoukuan_item3);
            holder.shoukuan = (TextView) convertView.findViewById(R.id.shoukuan_shoukuan);
            holder.name = (TextView) convertView.findViewById(R.id.shoukuan_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.jine.setText(listitem.get(position).get("gold").toString());
        holder.zhekou.setText(listitem.get(position).get("zk").toString());
        holder.shishou.setText(listitem.get(position).get("sgold").toString());
        holder.name.setText(listitem.get(position).get("snm").toString());

        int zt = Integer.parseInt(listitem.get(position).get("zt").toString());
        switch (zt) {
            case 0:
                holder.shoukuan.setText(R.string.shoukuan);
                holder.shoukuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(R.string.tips);
                        builder.setMessage(R.string.isshoukuan);
                        builder.setNegativeButton(R.string.cancel, null);
                        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                progressDialog.show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HashMap map = new HashMap();
                                        map.put("fuid", listitem.get(position).get("fuid").toString());
                                        map.put("seller", listitem.get(position).get("seller").toString());
                                        map.put("phone", MyData.PHONE);
                                        mhandler.obtainMessage(position, HttpUtils.updata(map, MyData.URL_UPDATESK)).sendToTarget();
                                    }
                                }).start();

                            }
                        });
                        builder.create().show();

                    }
                });
                break;
            case 1:
                holder.shoukuan.setText(R.string.shoukuans);
                break;
            case 2:
                holder.shoukuan.setText(R.string.shoukuans);
                break;
            default:
                holder.shoukuan.setText(R.string.tuikuans);
                break;
        }

        return convertView;

    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                progressDialog.dismiss();
                Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                });
                if (Integer.parseInt(map.get("count").toString()) > 0) {
                    listitem.get(msg.what).put("zt", 1);
                    notifyDataSetChanged();
                    ((DuiJiang) context).getData();
                } else {
                    Toast.makeText(context, R.string.shoukuanfile, Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Toast.makeText(context, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }

        }
    };

    class ViewHolder {
        TextView jine;
        TextView zhekou;
        TextView shishou;
        TextView shoukuan;
        TextView name;
    }


}

