package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Adapter.JiluAdapter;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.listview.SwipeMenu;
import com.smapley.baibaohe.listview.SwipeMenuCreator;
import com.smapley.baibaohe.listview.SwipeMenuItem;
import com.smapley.baibaohe.listview.SwipeMenuListView;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/5/12.
 */
public class JiLu extends Activity {

    private SwipeMenuListView listView;
    private final int GETDATA = 1;
    private final int DELECT = 2;
    private List<Map> listitem;
    private int deletNum;
    private JiluAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.jilu);
        initView();
        getData();
    }

    private void initView() {
        listView = (SwipeMenuListView) findViewById(R.id.jilu_list);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        // step 2. listener item click event
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final Map item = listitem.get(position);
                switch (index) {
                    case 0:
                        deletNum = position;
                        AlertDialog.Builder builder = new AlertDialog.Builder(JiLu.this);
                        builder.setTitle(R.string.tips);
                        builder.setMessage(R.string.delect);
                        builder.setNegativeButton(R.string.cancel, null);
                        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HashMap map = new HashMap();
                                        map.put("bian", item.get("bian").toString());
                                        mhandler.obtainMessage(DELECT, HttpUtils.updata(map, MyData.URL_UPDATEZT7)).sendToTarget();
                                    }
                                }).start();

                            }
                        });
                        builder.create().show();

                        break;
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(JiLu.this, Store.class);
                intent.putExtra("user2", listitem.get(i).get("user2").toString());
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("winner", MyData.PHONE);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETJILU)).sendToTarget();
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
                        listitem = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Map>>() {
                        });
                        if (listitem != null && !listitem.isEmpty()) {
                            adapter = new JiluAdapter(JiLu.this, listitem, 7);
                            listView.setAdapter(adapter);
                        }
                        break;
                    case DELECT:
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("count").toString()) > 0) {
                            listitem.remove(deletNum);
                            adapter.notifyDataSetChanged();
                            Toast.makeText(JiLu.this, R.string.delected, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(JiLu.this, R.string.delectfaile, Toast.LENGTH_SHORT).show();
                        }

                        break;
                }
            } catch (Exception e) {
                Toast.makeText(JiLu.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
