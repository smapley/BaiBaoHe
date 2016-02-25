package com.smapley.baibaohe.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.smapley.baibaohe.R;

import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/5/17.
 */
public class ShouKuanJiLuAdapter extends BaseAdapter {
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

    /**
     * 构造函数
     */
    public ShouKuanJiLuAdapter(Context context,
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
            convertView = mInflater.inflate(R.layout.layout_shoukuanjilu_item, parent, false);
            holder.jine = (TextView) convertView.findViewById(R.id.shoukuan_item1);
            holder.zhekou = (TextView) convertView.findViewById(R.id.shoukuan_item2);
            holder.shishou = (TextView) convertView.findViewById(R.id.shoukuan_item3);
            holder.name = (TextView) convertView.findViewById(R.id.shoukuan_name);
            holder.riqi = (TextView) convertView.findViewById(R.id.shoukuan_riqi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.jine.setText(listitem.get(position).get("gold").toString());
        holder.zhekou.setText(listitem.get(position).get("zk").toString());
        holder.shishou.setText(listitem.get(position).get("sgold").toString());
        holder.name.setText(listitem.get(position).get("nm").toString());
        holder.riqi.setText(listitem.get(position).get("tm").toString());

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
        TextView name;
        TextView riqi;
    }


}

