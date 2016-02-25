package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;
import com.smapley.baibaohe.utls.http.bitmap.LoadImage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/6/18.
 */
public class ChouJiang extends Activity implements View.OnClickListener {

    private String bian;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;
    private ImageView imageView10;
    private ImageView imageView11;
    private ImageView imageView12;
    private ImageView imageView1s;
    private ImageView imageView2s;
    private ImageView imageView3s;
    private ImageView imageView4s;
    private ImageView imageView5s;
    private ImageView imageView6s;
    private ImageView imageView7s;
    private ImageView imageView8s;
    private ImageView imageView9s;
    private ImageView imageView10s;
    private ImageView imageView11s;
    private ImageView imageView12s;
    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;
    private RelativeLayout relativeLayout5;
    private RelativeLayout relativeLayout6;
    private RelativeLayout relativeLayout7;
    private RelativeLayout relativeLayout8;
    private RelativeLayout relativeLayout9;
    private RelativeLayout relativeLayout10;
    private RelativeLayout relativeLayout11;
    private RelativeLayout relativeLayout12;
    private final int GETDATA = 1;
    private final int UPDATA = 2;
    private final int LOADIMAGE = 3;
    private List<ImageView> listImage;
    private List<ImageView> listImages;
    private List<View> listLayout;
    private View nowView;
    private boolean CanClick = true;
    private int clickNum = -1;
    private boolean isOpen = true;
    private int[] num;
    private GetBitmap getBitmap;
    private ProgressDialog progressDialog;
    private List<Map> listUrl;
    private TextView result;
    private AnimationDrawable animationDrawable;
    private String jiangpinname;
    private String user2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choujiang);
        initData();
        initView();
        getData();
    }

    private void initData() {
        bian = getIntent().getStringExtra("bian");
        user2 = getIntent().getStringExtra("user2");
        listImage = new ArrayList<>();
        listImages = new ArrayList<>();
        listLayout = new ArrayList<>();
        getBitmap = new GetBitmap(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCanceledOnTouchOutside(false);// ���õ����ĻDialog����ʧ
    }

    private void initView() {
        result = (TextView) findViewById(R.id.choujiang_result);
        imageView1 = (ImageView) findViewById(R.id.choujiang_image1);
        imageView2 = (ImageView) findViewById(R.id.choujiang_image2);
        imageView3 = (ImageView) findViewById(R.id.choujiang_image3);
        imageView4 = (ImageView) findViewById(R.id.choujiang_image4);
        imageView5 = (ImageView) findViewById(R.id.choujiang_image5);
        imageView6 = (ImageView) findViewById(R.id.choujiang_image6);
        imageView7 = (ImageView) findViewById(R.id.choujiang_image7);
        imageView8 = (ImageView) findViewById(R.id.choujiang_image8);
        imageView9 = (ImageView) findViewById(R.id.choujiang_image9);
        imageView10 = (ImageView) findViewById(R.id.choujiang_image10);
        imageView11 = (ImageView) findViewById(R.id.choujiang_image11);
        imageView12 = (ImageView) findViewById(R.id.choujiang_image12);
        imageView1s = (ImageView) findViewById(R.id.choujiang_image1s);
        imageView2s = (ImageView) findViewById(R.id.choujiang_image2s);
        imageView3s = (ImageView) findViewById(R.id.choujiang_image3s);
        imageView4s = (ImageView) findViewById(R.id.choujiang_image4s);
        imageView5s = (ImageView) findViewById(R.id.choujiang_image5s);
        imageView6s = (ImageView) findViewById(R.id.choujiang_image6s);
        imageView7s = (ImageView) findViewById(R.id.choujiang_image7s);
        imageView8s = (ImageView) findViewById(R.id.choujiang_image8s);
        imageView9s = (ImageView) findViewById(R.id.choujiang_image9s);
        imageView10s = (ImageView) findViewById(R.id.choujiang_image10s);
        imageView11s = (ImageView) findViewById(R.id.choujiang_image11s);
        imageView12s = (ImageView) findViewById(R.id.choujiang_image12s);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.choujiang_layout1);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.choujiang_layout2);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.choujiang_layout3);
        relativeLayout4 = (RelativeLayout) findViewById(R.id.choujiang_layout4);
        relativeLayout5 = (RelativeLayout) findViewById(R.id.choujiang_layout5);
        relativeLayout6 = (RelativeLayout) findViewById(R.id.choujiang_layout6);
        relativeLayout7 = (RelativeLayout) findViewById(R.id.choujiang_layout7);
        relativeLayout8 = (RelativeLayout) findViewById(R.id.choujiang_layout8);
        relativeLayout9 = (RelativeLayout) findViewById(R.id.choujiang_layout9);
        relativeLayout10 = (RelativeLayout) findViewById(R.id.choujiang_layout10);
        relativeLayout11 = (RelativeLayout) findViewById(R.id.choujiang_layout11);
        relativeLayout12 = (RelativeLayout) findViewById(R.id.choujiang_layout12);

        imageView1s.setOnClickListener(this);
        imageView2s.setOnClickListener(this);
        imageView3s.setOnClickListener(this);
        imageView4s.setOnClickListener(this);
        imageView5s.setOnClickListener(this);
        imageView6s.setOnClickListener(this);
        imageView7s.setOnClickListener(this);
        imageView8s.setOnClickListener(this);
        imageView9s.setOnClickListener(this);
        imageView10s.setOnClickListener(this);
        imageView11s.setOnClickListener(this);
        imageView12s.setOnClickListener(this);

        stop(imageView1s);
        stop(imageView2s);
        stop(imageView3s);
        stop(imageView4s);
        stop(imageView5s);
        stop(imageView6s);
        stop(imageView7s);
        stop(imageView8s);
        stop(imageView9s);
        stop(imageView10s);
        stop(imageView11s);
        stop(imageView12s);

        listImage.add(imageView1);
        listImage.add(imageView2);
        listImage.add(imageView3);
        listImage.add(imageView4);
        listImage.add(imageView5);
        listImage.add(imageView6);
        listImage.add(imageView7);
        listImage.add(imageView8);
        listImage.add(imageView9);
        listImage.add(imageView10);
        listImage.add(imageView11);
        listImage.add(imageView12);

        listImages.add(imageView1s);
        listImages.add(imageView2s);
        listImages.add(imageView3s);
        listImages.add(imageView4s);
        listImages.add(imageView5s);
        listImages.add(imageView6s);
        listImages.add(imageView7s);
        listImages.add(imageView8s);
        listImages.add(imageView9s);
        listImages.add(imageView10s);
        listImages.add(imageView11s);
        listImages.add(imageView12s);

        listLayout.add(relativeLayout1);
        listLayout.add(relativeLayout2);
        listLayout.add(relativeLayout3);
        listLayout.add(relativeLayout4);
        listLayout.add(relativeLayout5);
        listLayout.add(relativeLayout6);
        listLayout.add(relativeLayout7);
        listLayout.add(relativeLayout8);
        listLayout.add(relativeLayout9);
        listLayout.add(relativeLayout10);
        listLayout.add(relativeLayout11);
        listLayout.add(relativeLayout12);


    }

    private void stop(ImageView view) {
        animationDrawable = (AnimationDrawable) view.getDrawable();
        animationDrawable.stop();
    }

    private void start(int position) {
        animationDrawable = (AnimationDrawable) listImages.get(position).getDrawable();
        animationDrawable.start();
        showView(position);
    }

    private void clearn() {
        for (ImageView imageView : listImage) {
            imageView.setImageDrawable(null);
        }
    }

    @Override
    public void onClick(View view) {
        nowView = view;
        if (CanClick) {
            upData();
            int i = 0;
            for (ImageView imageViews : listImages) {
                //�õ������imageview
                if (imageViews.getId() == view.getId()) {
                    clickNum = i;
                    listLayout.get(i).setBackgroundResource(R.drawable.textview_circle2s);
                }
                i++;
            }
        }
        CanClick = false;

    }

    private void getData() {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("bian", bian);
                map.put("user2", user2);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETJP)).sendToTarget();
            }
        }).start();

    }

    private void upData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("bian", bian);
                map.put("phone", MyData.PHONE);
                map.put("user2", user2);
                mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_ADDDJIANG)).sendToTarget();
            }
        }).start();
    }

    private void setView(String url) {
        clearn();
        num = new int[listUrl.size()];
        for (int i = 0; i < listUrl.size(); i++) {
            int nowNum = getNum();
            if (url != null && listUrl.get(i).get("pic").toString().equals(url)) {
                getBitmap.getBitmap(url, listImage.get(clickNum));
                num[i] = clickNum;
            } else {
                getBitmap.getBitmap(listUrl.get(i).get("pic").toString(), listImage.get(nowNum));
                num[i] = nowNum;

            }

        }

    }

    private int getNum() {
        int size = 11;
        int number = (int) (Math.random() * size);
        Log.i("choujiang", "------------------>>" + size);
        Log.i("choujiang", "------------------>>" + number);
        for (int nowNum : num) {
            if (number == nowNum || number == clickNum) {
                return (getNum());
            }
        }
        return number;
    }

    private void loadImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < listUrl.size(); i++) {
                    list.add(listUrl.get(i).get("pic").toString());
                }
                LoadImage loadImage = new LoadImage();
                loadImage.Load(list);
                while (true) {
                    if (loadImage.LOCK == 0)
                        mhandler.obtainMessage(LOADIMAGE).sendToTarget();
                    break;
                }
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
                        List<List<Map>> list = JSON.parseObject(msg.obj.toString(), new TypeReference<List<List<Map>>>() {
                        });
                        if (list.get(1) != null && !list.get(1).isEmpty()) {
                            listUrl = new ArrayList<>();
                            for (int i = 1; i < list.size(); i++) {
                                listUrl.addAll(list.get(i));
                            }
                            loadImage();
                        } else {
                            progressDialog.dismiss();
                            CanClick = false;
                            Toast.makeText(ChouJiang.this, R.string.jiangquan_bucunzai, Toast.LENGTH_SHORT).show();
                        }
                        break;

                    case UPDATA:
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        //δ����
                        if (Integer.parseInt(map.get("picid").toString()) == 0) {
                            jiangpinname = getString(R.string.choujiang_no);
                            setView(null);
                            start(clickNum);
                        } else if (Integer.parseInt(map.get("picid").toString()) > 0) {
                            jiangpinname = map.get("ming").toString();
                            setView(map.get("pic").toString());
                            start(clickNum);
                        }

                        break;
                    case LOADIMAGE:
                        progressDialog.dismiss();

                        break;
                }

            } catch (Exception e) {
                progressDialog.dismiss();
                CanClick = false;
                Toast.makeText(ChouJiang.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }

        }
    };

    private void showView(final int position) {
        if (listImage.get(position).getVisibility() == View.GONE) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    result.setText(jiangpinname);

                    //��ʼ��
                    Animation scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.4f);
                    //���ö���ʱ��
                    scaleAnimation.setDuration(500);
                    scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            for (int i = 0; i < listImage.size(); i++) {
                                if (i != position) {
                                    start(i);
                                }
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    listImage.get(position).setVisibility(View.VISIBLE);
                    listImage.get(position).startAnimation(scaleAnimation);

                }
            }, 3900);

        }
    }
}
