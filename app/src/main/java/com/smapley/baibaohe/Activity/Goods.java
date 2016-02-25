package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/6/28.
 */
public class Goods extends Activity {

    private ImageView pic;
    private TextView name;
    private TextView shuliang;
    private TextView yizhong;
    private ListView listView;
    private final int GETDATA = 1;
    private String picid;
    private GetBitmap getBitmap;
    private TextView jia;
    private boolean CanJia = false;
    private final int HUANGOU=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods);


        initData();
        initView();
        getData();
    }

    private void initData() {
        picid = getIntent().getStringExtra("picid");
        getBitmap = new GetBitmap(this);
    }

    private void initView() {
        pic = (ImageView) findViewById(R.id.goods_pic);
        name = (TextView) findViewById(R.id.goods_name);
        shuliang = (TextView) findViewById(R.id.goods_shuliang);
        yizhong = (TextView) findViewById(R.id.goods_yizhong);
        listView = (ListView) findViewById(R.id.goods_list);
        jia = (TextView) findViewById(R.id.goods_jia);
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CanJia) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Goods.this);
                    LayoutInflater inflater = LayoutInflater.from(Goods.this);
                    View view = inflater.inflate(R.layout.huangou, null, false);
                    builder.setView(view);
                    builder.setNegativeButton(R.string.cancel, null);
                    builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            huanGou();
                        }
                    });
                    builder.create().show();
                }
            }
        });
    }

    private void setView(Map map) {
        getBitmap.getBitmap(map.get("pic").toString(), pic);
        name.setText(map.get("ming").toString());
        shuliang.setText(map.get("sl").toString());
        yizhong.setText(map.get("yz").toString());
        if (Integer.parseInt(map.get("jin").toString()) != 0 || Integer.parseInt(map.get("bao").toString()) != 0) {
            jia.setText(map.get("jia").toString());
            CanJia = true;
        }
    }

    private void huanGou(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map=new HashMap();
                map.put("phone",MyData.PHONE);
                map.put("picid",picid);
                mhandler.obtainMessage(HUANGOU,HttpUtils.updata(map,MyData.URL_ADDHUAN)).sendToTarget();
            }
        }).start();
    }

    private void setListView(List<Map<String, Object>> list) {
        if (list != null) {
            String[] name = {"nm", "tm"};
            int[] id = {R.id.goods_item_name, R.id.goods_item_time};
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, list, R.layout.layout_goods_item, name, id);
            listView.setAdapter(simpleAdapter);
        }
    }

    private void getData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("picid", picid);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETLISHI)).sendToTarget();
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case GETDATA:
                        List<List<Map<String, Object>>> list = JSON.parseObject(msg.obj.toString(), new TypeReference<List<List<Map<String, Object>>>>() {
                        });
                        if (list != null && !list.isEmpty()) {
                            setView(list.get(0).get(0));
                            setListView(list.get(1));
                        }
                        break;
                    case HUANGOU:

                        Map map=JSON.parseObject(msg.obj.toString(),new TypeReference<Map>(){});
                        int result=Integer.parseInt(map.get("newid").toString());
                        AlertDialog.Builder builder=new AlertDialog.Builder(Goods.this);
                        builder.setTitle(R.string.tips);

                        switch (result){
                            case -2:
                                builder.setMessage(R.string.goods_1);
                                break;
                            case -1:
                                builder.setMessage(R.string.goods_2);
                                break;
                            default:
                                builder.setMessage(R.string.goods_3);
                                break;
                        }

                        builder.setNegativeButton(R.string.okay,null);
                        builder.create().show();
                        getData();
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(Goods.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
