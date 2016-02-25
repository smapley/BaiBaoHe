package com.smapley.baibaohe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.smapley.baibaohe.R;

import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/5/17.
 */
public class PhoneAdapter extends BaseAdapter {
    /**
     * 得到一个LayoutInfalter对象用来导入布局
     */
    private LayoutInflater mInflater;
    private Context context;
    /**
     * list的数据
     */
    private List<Map> listitem;

    /**
     * 构造函数
     */
    public PhoneAdapter(Context context,
                        List listitem) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listitem = listitem;
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
            convertView = mInflater.inflate(R.layout.layout_phone_item, parent, false);
            holder.phone = (TextView) convertView.findViewById(R.id.phone_phone);
            holder.dyid = (TextView) convertView.findViewById(R.id.phone_dyid);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.phone.setText(listitem.get(position).get("dy").toString());
        holder.dyid.setText(listitem.get(position).get("dyid").toString());

        return convertView;

    }


    class ViewHolder {
        TextView phone;
        TextView dyid;
    }


}

