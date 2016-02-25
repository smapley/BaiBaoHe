package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.SetHead;
import com.smapley.baibaohe.mode.SetItem;
import com.smapley.baibaohe.newAdapter.SetNewAdapter;
import com.smapley.baibaohe.pubu.library.DividerItemDecoration;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;
import com.smapley.baibaohe.utls.http.bitmap.ImageFileCache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/6/5.
 */
public class Set extends Activity {
    private final int GETDATA1 = 1;
    private final int GETDATA2 = 2;
    private final int UPDATA = 3;
    private final int UPDATAFILE = 4;
    private boolean GET1 = false;
    private boolean GET2 = false;
    private String user2;

    private TextView item2;
    private EditText tag;
    private ImageView shoucang;
    private LinearLayout menu;
    private TextView menu1;
    private TextView menu2;
    private TextView menu3;
    private SharedPreferences sharedPreferences;
    private ProgressDialog dialog;
    private Uri imageUri = Uri.fromFile(new File(ImageFileCache.getDirectory(),
            "uppic.jpg"));
    private boolean ISSAVE = true;
    private ArrayList<String> mSelectPath;
    private RecyclerView listView;
    private SetNewAdapter adapter;
    private int menutag = 1;
    private GetBitmap getBitmap;
    private boolean haseHead = false;
    private List listitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set);
        sharedPreferences = getSharedPreferences(MyData.USERSP, Context.MODE_PRIVATE);
        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.tips));
        getBitmap = new GetBitmap(this);
        initData();
        initView();
        getData();

    }


    private void initData() {
        user2 = MyData.PHONE;
        MyData.CANDELECT = true;
    }

    private void initView() {

        listView = (RecyclerView) findViewById(R.id.set_list);
        listView.setLayoutManager(new LinearLayoutManager(Set.this));
        listView.addItemDecoration(new DividerItemDecoration(Set.this, LinearLayoutManager.VERTICAL));
        adapter = new SetNewAdapter(Set.this, new ArrayList(), getBitmap);
        listView.setAdapter(adapter);
        item2 = (TextView) findViewById(R.id.set_item2);
        item2.setVisibility(View.GONE);
        menu = (LinearLayout) findViewById(R.id.set_menu);
        menu1 = (TextView) findViewById(R.id.menu_item1);
        menu2 = (TextView) findViewById(R.id.menu_item2);
        menu3 = (TextView) findViewById(R.id.menu_item3);
        tag = (EditText) findViewById(R.id.set_tag);
        shoucang = (ImageView) findViewById(R.id.set_shoucang);
        shoucang.setImageResource(R.drawable.tianjia);
        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menu.getVisibility() == View.VISIBLE) {
                    menu.setVisibility(View.GONE);
                } else {
                    menu.setVisibility(View.VISIBLE);
                }
            }
        });
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menutag = 1;
                menu.setVisibility(View.GONE);
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");// 相片类型
                intent.putExtra("scale", true);
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX", 1);// 表示剪切框的比例1:1的效果
                intent.putExtra("aspectY", 1);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, 0);

            }
        });
        menu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menutag = 2;
                menu.setVisibility(View.GONE);
                shoucang.setVisibility(View.GONE);
//                tag.setEnabled(true);
//                tag.setBackgroundResource(R.drawable.textview_edge);
//                tag.setTextColor(getResources().getColor(R.color.black));
                item2.setText(R.string.okay);
                item2.setVisibility(View.VISIBLE);
                adapter.edit = true;
                adapter.notifyDataSetChanged();
                ISSAVE = false;
            }
        });

        menu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                menutag = 3;
                menu.setVisibility(View.GONE);
                shoucang.setVisibility(View.GONE);
                adapter.delect = true;
                adapter.notifyDataSetChanged();
                item2.setText(R.string.okay);
                item2.setVisibility(View.VISIBLE);
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (menutag == 3) {
                    item2.setVisibility(View.GONE);
                    shoucang.setVisibility(View.VISIBLE);
                    adapter.delect = false;
                    adapter.notifyDataSetChanged();
                }
                if (menutag == 2) {
                    int minnum = 0;
                    int maxnum = 0;
                    final SetHead setHead = (SetHead) adapter.listitem.get(0);
                    try {
                        minnum = Integer.parseInt(setHead.min);
                        maxnum = Integer.parseInt(setHead.max);
                    } catch (Exception e) {

                    }
                    if (minnum <= maxnum && minnum <= 100 && maxnum <= 100) {
                        item2.setVisibility(View.GONE);
                        shoucang.setVisibility(View.VISIBLE);

                        adapter.edit = false;
                        adapter.notifyDataSetChanged();
//                        tag.setEnabled(false);
//                        tag.setBackgroundDrawable(null);
//                        tag.setTextColor(getResources().getColor(R.color.white));
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HashMap map = new HashMap();
                                map.put("user2", MyData.PHONE);
                                map.put("nm", tag.getText().toString());
                                map.put("zmin", setHead.min);
                                map.put("zmax", setHead.max);
                                map.put("des", setHead.des);
                                List result = new ArrayList();
                                result.addAll(adapter.listitem);
                                result.remove(0);
                                map.put("result", JSON.toJSON(result));
                                mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_UPDATAPIC)).sendToTarget();
                            }
                        }).start();
                        ISSAVE = true;
                    } else {
                        Toast.makeText(Set.this, R.string.err, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode == 0) {
                doResult();
            }
        } catch (Exception e) {

        }
    }

    private void doResult() {
        dialog.setMessage(getString(R.string.updata));
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap();
                map.put("phone", MyData.PHONE);
                map.put("zs", 1);
                Map filemap = new HashMap();
                for (int i = 0; i < 1; i++) {
                    File file = new File(ImageFileCache.getDirectory() + "uppic.jpg");
                    String suffix = "";
                    String name = file.getName();
                    final int idx = name.lastIndexOf(".");
                    if (idx > 0) {
                        suffix = name.substring(idx);
                    }
                    filemap.put(i + suffix, file);
//                    filemap.put("pic" + (i + 1), DoBitmap.compressToBytes(DoBitmap.getBitmap(DoBitmap.FromPath, path, 480, 800)));
                }
                mhandler.obtainMessage(UPDATAFILE, HttpUtils.post(MyData.URL_ADDPHOTO, map, filemap, HttpUtils.File)).sendToTarget();
            }
        }).start();
    }

    private void getData() {
        GET1 = false;
        GET2 = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("user2", MyData.PHONE);
                mhandler.obtainMessage(GETDATA2, HttpUtils.updata(map, MyData.URL_GETDESCRIBE)).sendToTarget();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("user2", MyData.PHONE);
                mhandler.obtainMessage(GETDATA1, HttpUtils.updata(map, MyData.URL_GETPIC2)).sendToTarget();
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
                        GET1 = true;
                        List<SetItem> data1 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<SetItem>>() {
                        });
                        if (data1 != null && !data1.isEmpty()) {
                            if (GET2) {
                                listitem.addAll(data1);
                                adapter.listitem = listitem;
                                adapter.notifyDataSetChanged();
                            } else {
                                listitem = data1;
                            }
                        }
                        break;
                    case GETDATA2:
                        GET2 = true;
                        List<SetHead> data2 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<SetHead>>() {
                        });
                        if (data2 != null && !data2.isEmpty()) {
                            tag.setText(data2.get(0).nm);
                            if (GET1) {
                                listitem.add(0, data2.get(0));
                                adapter.listitem = listitem;
                                adapter.notifyDataSetChanged();
                            } else {
                                listitem = data2;
                            }
                        }
                        break;

                    case UPDATA:
                        String result = JSON.parseObject(msg.obj.toString(), new TypeReference<String>() {
                        });
                        boolean okay = false;
                        boolean max = true;
                        for (int i = 0; i < result.length(); i++) {
                            if (Integer.parseInt(result.substring(i, i + 1)) == 2) {
                                Toast.makeText(Set.this, R.string.max, Toast.LENGTH_SHORT).show();
                                max = false;
                                break;
                            }
                        }
                        if (max) {
                            for (int i = 0; i < result.length(); i++) {
                                if (Integer.parseInt(result.substring(i, i + 1)) == 1) {
                                    okay = true;
                                    break;
                                }
                            }

                            if (okay) {
                                Toast.makeText(Set.this, R.string.changed, Toast.LENGTH_SHORT).show();
                                getData();
                            } else {
                                Toast.makeText(Set.this, R.string.changfile, Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case UPDATAFILE:
                        dialog.dismiss();
                        Map map2 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map2.get("newid").toString()) > 0) {
                            Toast.makeText(Set.this, R.string.uped, Toast.LENGTH_SHORT).show();
                            getData();
                        } else {
                            Toast.makeText(Set.this, R.string.upefiled, Toast.LENGTH_SHORT).show();

                        }
                        break;
                }

            } catch (Exception e) {
                try {
                    Toast.makeText(Set.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
                } catch (Exception e1) {

                }

            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (menu.getVisibility() == View.VISIBLE) {
                menu.setVisibility(View.GONE);
            } else if (ISSAVE) {
                finish();
            } else {
                Toast.makeText(Set.this, R.string.nosave, Toast.LENGTH_SHORT).show();
            }
        }
        return false;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyData.CANDELECT = false;
    }
}
