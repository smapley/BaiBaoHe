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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Activity.DuiJiang;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/5/17.
 */
public class JiluAdapter extends BaseAdapter {
    /**
     * 得到一个LayoutInfalter对象用来导入布局
     */
    private LayoutInflater mInflater;
    private Context context;
    /**
     * list的数据
     */
    private List<Map> listitem;
    private GetBitmap getBitmap;
    private final int UPDATA = 1;
    private ProgressDialog progressDialog;
    private int num;

    /**
     * 构造函数
     */
    public JiluAdapter(Context context,
                       List listitem, int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listitem = listitem;
        progressDialog = new ProgressDialog(context);
        getBitmap = new GetBitmap(context);
        this.num = num;
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
            convertView = mInflater.inflate(R.layout.layout_jilu_item, parent, false);
            holder.pic = (ImageView) convertView.findViewById(R.id.jilu_pic);
            holder.name = (TextView) convertView.findViewById(R.id.jilu_name);
            holder.names = (TextView) convertView.findViewById(R.id.jilu_names);
            holder.goods = (TextView) convertView.findViewById(R.id.jilu_goods);
            holder.quan = (TextView) convertView.findViewById(R.id.jilu_quan);
            holder.quans = (TextView) convertView.findViewById(R.id.jilu_quans);
            holder.zhuangtai = (TextView) convertView.findViewById(R.id.jilu_zhuangtai);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        int status = Integer.parseInt(listitem.get(position).get("zt").toString());
        switch (status) {
            case 8:
                if (num == 9) {
                    holder.zhuangtai.setText(R.string.jilu1);
                    holder.zhuangtai.setTextColor(context.getResources().getColor(R.color.white));
                    holder.zhuangtai.setBackgroundResource(R.drawable.textview_circles);
                    holder.zhuangtai.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle(R.string.tips);
                            builder.setMessage(R.string.duijiang1);
                            builder.setNegativeButton(R.string.cancel, null);
                            builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    progressDialog.show();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HashMap map = new HashMap();
                                            map.put("bian", listitem.get(position).get("bian").toString());
                                            map.put("phone", MyData.PHONE);
                                            mhandler.obtainMessage(position, HttpUtils.updata(map, MyData.URL_UPDATEZT9)).sendToTarget();
                                        }
                                    }).start();
                                }
                            });
                            builder.create().show();

                        }
                    });
                } else {
                    holder.zhuangtai.setText(R.string.jilu3);
                    holder.zhuangtai.setTextColor(context.getResources().getColor(R.color.weiduihuan));

                }
                break;
            case 9:
                holder.zhuangtai.setText(R.string.jilu2);
                holder.zhuangtai.setTextColor(context.getResources().getColor(R.color.yiduihuan));
                holder.zhuangtai.setBackgroundDrawable(null);
                break;
        }
        getBitmap.getBitmap(listitem.get(position).get("pic").toString(), holder.pic);
        holder.goods.setText(listitem.get(position).get("ming").toString());
        if (num == 9) {
            holder.names.setText(context.getString(R.string.yonghu2));
            holder.quans.setText(context.getString(R.string.bianhao));
            holder.name.setText(listitem.get(position).get("nm").toString());
            holder.quan.setText(listitem.get(position).get("bian").toString());
        } else {
            holder.names.setText(context.getString(R.string.yonghu));
            holder.quans.setText(context.getString(R.string.mima));
            String data = listitem.get(position).get("djmima").toString();
            holder.name.setText(listitem.get(position).get("snm").toString());
            if (data.length() > 4) {
                data = data.substring(0, 4) + " " + data.substring(4, data.length());
            }
            holder.quan.setText(data);
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
                    listitem.get(msg.what).put("zt", 9);
                    notifyDataSetChanged();
                    ((DuiJiang) context).getData();
                } else {
                    Toast.makeText(context, R.string.duijiangfile, Toast.LENGTH_SHORT).show();

                }
            } catch (Exception e) {
                Toast.makeText(context, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }

        }
    };

    class ViewHolder {
        ImageView pic;
        TextView name;
        TextView names;
        TextView goods;
        TextView quan;
        TextView quans;
        TextView zhuangtai;
    }


}

