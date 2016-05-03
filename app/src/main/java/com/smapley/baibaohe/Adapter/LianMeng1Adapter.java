package com.smapley.baibaohe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.LianMengItem;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.List;

/**
 * Created by smapley on 2015/5/17.
 */
public class LianMeng1Adapter extends BaseAdapter {
    /**
     * 得到一个LayoutInfalter对象用来导入布局
     */
    private LayoutInflater mInflater;
    private Context context;
    /**
     * list的数据
     */
    public List<LianMengItem> listitem;
    private GetBitmap getBitmap;

    /**
     * 构造函数
     */
    public LianMeng1Adapter(Context context,
                            List listitem) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listitem = listitem;
        getBitmap = new GetBitmap(context);
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
            convertView = mInflater.inflate(R.layout.layout_main2_item, parent, false);
            holder.pic = (ImageView) convertView.findViewById(R.id.main2_pic);
            holder.name = (TextView) convertView.findViewById(R.id.main2_nm);
            holder.des = (TextView) convertView.findViewById(R.id.main2_des);
            holder.addr = (TextView) convertView.findViewById(R.id.main2_addr);
            holder.yinke = (TextView) convertView.findViewById(R.id.main2_yinke);
            holder.text = (TextView) convertView.findViewById(R.id.sousuo_text);
            holder.layout = (RelativeLayout) convertView.findViewById(R.id.layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LianMengItem item = listitem.get(position);
        if (item.getType() == 0) {
            holder.text.setVisibility(View.VISIBLE);
            holder.text.setText(item.text);
        } else {
            holder.text.setVisibility(View.GONE);
        }
        getBitmap.getBitmap(item.pic, holder.pic);
        holder.name.setText(MyData.ToDBC(item.nm));
        holder.yinke.setVisibility(View.VISIBLE);
        holder.yinke.setText(context.getString(R.string.yinke) + item.cs);
        holder.des.setText(MyData.ToDBC(item.des));
        holder.addr.setText(MyData.ToDBC(item.dpdz));
//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, Store.class);
//                intent.putExtra("user2", item.user2);
//                intent.putExtra("type", 1);
//                intent.putExtra("bian", MyData.BIAN);
//                context.startActivity(intent);
//            }
//        });

        return convertView;

    }


    class ViewHolder {
        ImageView pic;
        TextView name;
        TextView des;
        TextView addr;
        TextView yinke;
        TextView text;
        RelativeLayout layout;
    }


}

