package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Adapter.LianMeng1Adapter;
import com.smapley.baibaohe.Adapter.LianMeng2Adapter;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.listview.SwipeMenu;
import com.smapley.baibaohe.listview.SwipeMenuCreator;
import com.smapley.baibaohe.listview.SwipeMenuItem;
import com.smapley.baibaohe.listview.SwipeMenuListView;
import com.smapley.baibaohe.mode.LianMengItem;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by smapley on 2015/7/28.
 */
public class LianMeng extends Activity {
    private SwipeMenuListView listView1;
    private SwipeMenuListView listView2;
    private TextView item1;
    private TextView item2;
    private final int GETDATA1 = 1;
    private final int GETDATA2 = 2;
    private final int MENU1 = 3;
    private final int MENU2 = 4;
    private LianMeng1Adapter adapter1;
    private LianMeng2Adapter adapter2;
    private GetBitmap getBitmap;
    private List<LianMengItem> listitem1;
    private List<LianMengItem> listitem2;
    private ImageView tianjia;
    private ProgressDialog progressDialog;
    private final int ADD = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lianmeng);
        getBitmap = new GetBitmap(this);
        progressDialog = new ProgressDialog(LianMeng.this);
        adapter1 = new LianMeng1Adapter(LianMeng.this, new ArrayList());
        adapter2 = new LianMeng2Adapter(LianMeng.this, new ArrayList());

        initView();
        getData();
    }

    private void initView() {
        listView1 = (SwipeMenuListView) findViewById(R.id.lianmeng_list);
        listView2 = (SwipeMenuListView) findViewById(R.id.lianmeng_list2);
        tianjia = (ImageView) findViewById(R.id.lianmeng_tianjia);
        listView1.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
        SwipeMenuCreator creator1 = new SwipeMenuCreator() {
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
        listView1.setMenuCreator(creator1);

        // step 2. listener item click event
        listView1.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final LianMengItem item = listitem1.get(position);
                switch (index) {
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(LianMeng.this);
                        builder.setTitle(R.string.tips);
                        builder.setMessage(R.string.jiechu);
                        builder.setNegativeButton(R.string.cancel, null);
                        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        HashMap map = new HashMap();
                                        map.put("user2", item.user2);
                                        map.put("phone", MyData.PHONE);
                                        mhandler.obtainMessage(MENU1, HttpUtils.updata(map, MyData.URL_DELLIAN)).sendToTarget();
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
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LianMeng.this, Store.class);
                intent.putExtra("user2", listitem1.get(position).user2);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        SwipeMenuCreator creator2 = new SwipeMenuCreator() {
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
        listView2.setMenuCreator(creator2);

        // step 2. listener item click event
        listView2.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                final LianMengItem item = listitem2.get(position);
                switch (index) {
                    case 0:
                        AlertDialog.Builder builder = new AlertDialog.Builder(LianMeng.this);
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
                                        map.put("user2", item.user2);
                                        map.put("phone", MyData.PHONE);
                                        mhandler.obtainMessage(MENU1, HttpUtils.updata(map, MyData.URL_DELLIAN)).sendToTarget();
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
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LianMeng.this, Store.class);
                intent.putExtra("user2", listitem2.get(position).user2);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
        item1 = (TextView) findViewById(R.id.lianmeng_item1);
        item2 = (TextView) findViewById(R.id.lianmeng_item2);

        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item1.setBackgroundResource(R.color.chengse);
                item2.setBackgroundDrawable(null);
                item1.setTextColor(getResources().getColor(R.color.white2));
                item2.setTextColor(getResources().getColor(R.color.chengse));
                listView1.setVisibility(View.VISIBLE);
                listView2.setVisibility(View.GONE);
                tianjia.setVisibility(View.GONE);
                getData();
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item2.setBackgroundResource(R.color.chengse);
                item1.setBackgroundDrawable(null);
                item2.setTextColor(getResources().getColor(R.color.white2));
                item1.setTextColor(getResources().getColor(R.color.chengse));
                listView1.setVisibility(View.GONE);
                listView2.setVisibility(View.VISIBLE);
                tianjia.setVisibility(View.VISIBLE);
                getData();
            }
        });
        tianjia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LianMeng.this);
                builder.setTitle(R.string.tip6);
                LayoutInflater inflater = LayoutInflater.from(LianMeng.this);
                View view1 = inflater.inflate(R.layout.layout_inputtext, null, false);
                final EditText editText = (EditText) view1.findViewById(R.id.editext);
                builder.setView(view1);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (editText.getText().toString() != null) {
                            progressDialog.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HashMap map = new HashMap();
                                    map.put("phone", MyData.PHONE);
                                    map.put("id", editText.getText().toString());
                                    mhandler.obtainMessage(ADD, HttpUtils.updata(map, MyData.URL_ADDLIAN)).sendToTarget();

                                }
                            }).start();
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    public void getData() {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("phone", MyData.PHONE);
                mhandler.obtainMessage(GETDATA1, HttpUtils.updata(map, MyData.URL_GETLIANLB)).sendToTarget();
                mhandler.obtainMessage(GETDATA2, HttpUtils.updata(map, MyData.URL_GETLIANLB1)).sendToTarget();
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case GETDATA1:
                        progressDialog.dismiss();
                        List<LianMengItem> list1 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<LianMengItem>>() {
                        });
                        if (list1 != null) {
                            boolean a = true;
                            boolean b = true;
                            for (int i = 0; i < list1.size(); i++) {
                                switch (list1.get(i).zb) {
                                    case 1:
                                        if (a) {
                                            list1.get(i).setData(getString(R.string.lianmeng_1), 0);
                                            a = false;
                                        }
                                        break;
                                    case 2:
                                        if (b) {
                                            list1.get(i).setData(getString(R.string.lianmeng_2), 0);
                                            b = false;
                                        }
                                        break;
                                }
                            }
                            listitem1 = list1;
                            adapter1.listitem = listitem1;
                            adapter1.notifyDataSetChanged();
                        }

                        break;
                    case GETDATA2:
                        progressDialog.dismiss();
                        List<LianMengItem> list2 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<LianMengItem>>() {
                        });
                        if (list2 != null) {
                            listitem2 = list2;
                            adapter2.listitem = listitem2;
                            adapter2.notifyDataSetChanged();

                        }
                        break;
                    case MENU1:
                        progressDialog.dismiss();
                        List<Integer> list3 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Integer>>() {
                        });
                        boolean IsOk = false;
                        if (list3 != null && !list3.isEmpty()) {
                            for (int i = 0; i < list3.size(); i++) {
                                if (list3.get(i) > 0) {
                                    Toast.makeText(LianMeng.this, R.string.jiechusucc, Toast.LENGTH_SHORT).show();
                                    IsOk = true;
                                    break;
                                }
                            }
                            if (IsOk) {
                                getData();
                            } else {
                                Toast.makeText(LianMeng.this, R.string.jiechufile, Toast.LENGTH_SHORT).show();

                            }
                        }
                        break;
                    case ADD:
                        progressDialog.dismiss();
                        int result = JSON.parseObject(msg.obj.toString(), new TypeReference<Integer>() {
                        });
                        if (result > 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LianMeng.this);
                            builder.setTitle(R.string.tips);
                            builder.setMessage(R.string.msg4);
                            builder.setNegativeButton(R.string.okay, null);
                            builder.create().show();
                            getData();
                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LianMeng.this);
                            builder.setTitle(R.string.tips);
                            builder.setMessage(R.string.msg5);
                            builder.setNegativeButton(R.string.okay, null);
                            builder.create().show();
                        }

                        break;
                }
            } catch (Exception e) {
                Toast.makeText(LianMeng.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    };

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
