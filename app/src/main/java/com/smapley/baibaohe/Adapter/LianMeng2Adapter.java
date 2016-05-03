package com.smapley.baibaohe.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.smapley.baibaohe.Activity.LianMeng;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.LianMengItem;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.HashMap;
import java.util.List;

/**
 * Created by smapley on 2015/5/17.
 */
public class LianMeng2Adapter extends BaseAdapter {
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
    private final int UPDATA = 1;
    private ProgressDialog progressDialog;

    /**
     * 构造函数
     */
    public LianMeng2Adapter(Context context,
                            List listitem) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.listitem = listitem;
        progressDialog = new ProgressDialog(context);
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
            convertView = mInflater.inflate(R.layout.layout_main2s_item, parent, false);
            holder.pic = (ImageView) convertView.findViewById(R.id.main2_pic);
            holder.name = (TextView) convertView.findViewById(R.id.main2_nm);
            holder.des = (TextView) convertView.findViewById(R.id.main2_des);
            holder.addr = (TextView) convertView.findViewById(R.id.main2_addr);
            holder.yinke = (TextView) convertView.findViewById(R.id.main2_yinke);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final LianMengItem item = listitem.get(position);
        getBitmap.getBitmap(item.pic, holder.pic);
        holder.name.setText(MyData.ToDBC(item.nm));
        holder.yinke.setVisibility(View.VISIBLE);
        holder.yinke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap map = new HashMap();
                        map.put("user2", item.user2);
                        map.put("phone", MyData.PHONE);
                        mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_UPDATELIANZT1)).sendToTarget();
                    }
                }).start();
            }
        });
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
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case UPDATA:
                        int result = JSON.parseObject(msg.obj.toString(), new TypeReference<Integer>() {
                        });
                        if (result > 0) {
                            Toast.makeText(context, R.string.tianjiasucc, Toast.LENGTH_SHORT).show();
                            ((LianMeng) context).getData();
                        } else {
                            Toast.makeText(context, R.string.tianjiafile, Toast.LENGTH_SHORT).show();
                        }
                }
            } catch (Exception e) {
                Toast.makeText(context, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };

}