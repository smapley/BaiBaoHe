package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Adapter.PhoneAdapter;
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
 * Created by smapley on 2015/6/20.
 */
public class Phone extends Activity {

    private ImageView add;
    private SwipeMenuListView listView;
    private final int GETDATA = 1;
    private final int ADDDATA = 2;
    private final int DELDATA = 3;
    private List<Map> listItem;
    private PhoneAdapter adapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone);
        progressDialog = new ProgressDialog(this);
        initView();
        getData();

    }

    private void initView() {
        add = (ImageView) findViewById(R.id.phone_add);
        listView = (SwipeMenuListView) findViewById(R.id.phone_list);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Phone.this);
                builder.setTitle(R.string.phone_item1);
                final EditText editText = new EditText(Phone.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(editText);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        addData(editText.getText().toString());
                    }
                });
                builder.create().show();

            }
        });
        initListView();
    }

    private void getData() {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("phone", MyData.PHONE);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETDYLIST)).sendToTarget();

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
                        progressDialog.dismiss();
                        listItem = JSON.parseObject(msg.obj.toString(), new TypeReference<List>() {
                        });
                        if (listItem != null && !listItem.isEmpty()) {
                            Map map=new HashMap();
                            map.put("dy",getString(R.string.phone_item3));
                            map.put("dyid",getString(R.string.phone_item4));
                            listItem.add(0, map);
                            adapter = new PhoneAdapter(Phone.this, listItem);
                            listView.setAdapter(adapter);
                        }
                        break;
                    case ADDDATA:
                        progressDialog.dismiss();
                        String data = JSON.parseObject(msg.obj.toString(), new TypeReference<String>() {
                        });
                        if (Integer.parseInt(data) > 0) {
                            getData();
                        } else {
                            Toast.makeText(Phone.this, R.string.changfile, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case DELDATA:
                        progressDialog.dismiss();
                        String data2 = JSON.parseObject(msg.obj.toString(), new TypeReference<String>() {
                        });
                        if (Integer.parseInt(data2) > 0) {
                            getData();
                        } else {
                            Toast.makeText(Phone.this, R.string.delectfaile, Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            } catch (Exception e) {
                Toast.makeText(Phone.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void initListView() {
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
                final Map item = listItem.get(position);
                switch (index) {
                    case 0:
                        progressDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HashMap map = new HashMap();
                                map.put("phone", MyData.PHONE);
                                map.put("dy", item.get("dy").toString());
                                mhandler.obtainMessage(DELDATA, HttpUtils.updata(map, MyData.URL_DELDYLIST)).sendToTarget();
                            }
                        }).start();

                        break;
                }
                return false;
            }
        });
    }

    private void addData(final String data) {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("phone", MyData.PHONE);
                map.put("dy", data);
                mhandler.obtainMessage(ADDDATA, HttpUtils.updata(map, MyData.URL_ADDDYLIST)).sendToTarget();
            }
        }).start();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
