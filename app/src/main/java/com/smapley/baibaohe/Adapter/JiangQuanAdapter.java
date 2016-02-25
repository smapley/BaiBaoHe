package com.smapley.baibaohe.Adapter;

import android.content.Context;
import android.text.ClipboardManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/6/14.
 */
public class JiangQuanAdapter extends BaseAdapter {
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

    /**
     * 构造函数
     */
    public JiangQuanAdapter(Context context,
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
            convertView = mInflater.inflate(R.layout.layout_jiangquan_item, parent, false);
            holder.quan = (TextView) convertView.findViewById(R.id.jiangquan_bianhao);
            holder.fuzhi = (TextView) convertView.findViewById(R.id.jiangquan_fuzhi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.quan.setText(listitem.get(position).get("bian").toString());

        holder.fuzhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager cbm = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                cbm.setText(holder.quan.getText().toString());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap map = new HashMap();
                        map.put("bian", holder.quan.getText().toString());
                        HttpUtils.updata(map, MyData.URL_UPDATEZT1);
                    }
                }).start();
                listitem.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, R.string.fuzhiyincang, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }


    class ViewHolder {
        TextView quan;
        TextView fuzhi;
    }


}
