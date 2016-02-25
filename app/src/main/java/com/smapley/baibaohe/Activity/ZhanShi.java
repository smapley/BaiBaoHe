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
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.pubu.fragment.PubuFragment;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.ImageFileCache;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by smapley on 2015/6/14.
 */
public class ZhanShi extends Activity {
    private final int GETDATA1 = 1;
    private final int UPDATA = 3;
    private final int UPDATAFILE = 4;
    private LinearLayout linearLayout;
    private ImageView shoucang;
    private LinearLayout menu;
    private TextView menu1;
    private SharedPreferences sharedPreferences;
    private ProgressDialog dialog;
    private Uri imageUri = Uri.fromFile(new File(ImageFileCache.getDirectory(),
            "uppic.jpg"));
    private boolean ISSAVE = true;
    private PubuFragment pubuFragment;
    private ArrayList<String> mSelectPath;
    private int src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhanshi);
        sharedPreferences = getSharedPreferences(MyData.USERSP, Context.MODE_PRIVATE);
        dialog = new ProgressDialog(this);
        dialog.setTitle(getString(R.string.tips));
        initData();
        initView();

    }


    private void initData() {
        src = getIntent().getIntExtra("src", 0);
        MyData.CANDELECT = true;
    }

    private void initView() {
        pubuFragment = PubuFragment.newPubu(src);
        getFragmentManager().beginTransaction().replace(R.id.zhanshi_lin, pubuFragment).commit();
        linearLayout = (LinearLayout) findViewById(R.id.zhanshi_lin);
        menu = (LinearLayout) findViewById(R.id.zhanshi_menu);
        menu1 = (TextView) findViewById(R.id.menu_item1);
        shoucang = (ImageView) findViewById(R.id.zhanshi_shoucang);
        if (src != 1) {
            shoucang.setVisibility(View.GONE);
        }
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
                menu.setVisibility(View.GONE);
                int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
                boolean showCamera = true;
                int maxNum = 9;
                Intent intent = new Intent(ZhanShi.this, MultiImageSelectorActivity.class);
                // 是否显示拍摄图片
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, showCamera);
                // 最大可选择图片数量
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
                // 选择模式
                intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
                // 默认选择
//                if (mSelectPath != null && mSelectPath.size() > 0) {
//                    intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//                }
                startActivityForResult(intent, 0);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK && requestCode == 0) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

                doResult(mSelectPath);


            }
        } catch (Exception e) {

        }
    }

    private void doResult(final List<String> list) {
        dialog.setMessage(getString(R.string.updata));
        dialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map map = new HashMap();
                map.put("phone", MyData.PHONE);
                map.put("zs", list.size());
                Map filemap = new HashMap();
                for (int i = 0; i < list.size(); i++) {
                    String path = list.get(i);
                    File file = new File(path);
                    String suffix = "";
                    String name = file.getName();
                    final int idx = name.lastIndexOf(".");
                    if (idx > 0) {
                        suffix = name.substring(idx);
                    }
                    filemap.put(i + suffix, file);
//                    filemap.put("pic" + (i + 1), DoBitmap.compressToBytes(DoBitmap.getBitmap(DoBitmap.FromPath, path, 480, 800)));
                }
                mhandler.obtainMessage(UPDATAFILE, HttpUtils.post(MyData.URL_ADDDPIC, map, filemap, HttpUtils.File)).sendToTarget();
            }
        }).start();
    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {

                    case UPDATA:
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("count").toString()) > 0) {
                            Toast.makeText(ZhanShi.this, R.string.changed, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ZhanShi.this, R.string.changfile, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case UPDATAFILE:
                        dialog.dismiss();
                        Map map2 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map2.get("newid").toString()) > 0) {
                            Toast.makeText(ZhanShi.this, R.string.uped, Toast.LENGTH_SHORT).show();
                            pubuFragment.Refresh();
                        } else {
                            Toast.makeText(ZhanShi.this, R.string.upefiled, Toast.LENGTH_SHORT).show();

                        }
                        break;
                }

            } catch (Exception e) {
                try {
                    Toast.makeText(ZhanShi.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(ZhanShi.this, R.string.nosave, Toast.LENGTH_SHORT).show();
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
