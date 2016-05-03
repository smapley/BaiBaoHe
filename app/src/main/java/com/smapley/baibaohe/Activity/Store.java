package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.sdk.app.PayTask;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.pay.PayResult;
import com.smapley.baibaohe.pay.SignUtils;
import com.smapley.baibaohe.pubu.fragment.PubuFragment;
import com.smapley.baibaohe.pubu.mode.Photo;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by smapley on 2015/6/17.
 */
public class Store extends Activity {

    private TextView tag;
    private TextView miaoshu;
    private TextView choujiang;
    private String bian;
    private TextView dianpuid;
    private final int GETDATA = 1;
    private final int GETDATA2 = 2;
    private final int GETDATA3 = 3;
    private final int FUKUAN = 4;
    private final int YANZHENG = 7;
    private String user2;
    private int type;
    private TextView zhanshi;
    private PubuFragment pubuFragment;
    private boolean canChoujiang = false;
    // 商户PID
    public static final String PARTNER = "2088021078637071";
    // 商户收款账号
    public static final String SELLER = "316344445@qq.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJbSGOfIspREM+Bd3MBt9Xxs1egAJFpWMFlBVLuTJaTiiLlg7FwqFOc3sZ4R5faN4BjjuMpqZlMkYlCq2tsqCPyB2LOJEYWCcVKfYICUGDgpxkspt9UB0lDNSGIW64sIKmvcFOS349wv0GRIL+Jp5rFDLDvjZ1f41kWKKVzUfhgVAgMBAAECgYEAiGmG9T3lp4z4jtrWq4XJH70gzDI0rzB9kn0wsmepCLWMjH9JySKWvXr2P85YfORd6KUvooUR/+lMs0GVqd0fOjjfzaE2CXoW2mKd2pkb++KUQ3u1Yc5SVkVwItrPUWOTTHQqBav01OoAceE/FhL9BihaPQ9e2iy6NwxvvXpeOBECQQDx4ju6K8/7XV1yDJGlgoeuJQBAIqYvpTb5AyHzErmIfAqt8BGwp56/TxVu7WyNHAx5HMcchvmpx2OSQ27HzNU/AkEAn59fkRsLBYw1MTsjZqcmdf9qdPjRugZiga9eztOijLz6GhgRPqShOcEfcD1vr7Csm+ZIkBjwD5IE4x8VwYeZqwJAJJGDXh4Jj4MKAZgM3Ozi/lzxsMCMR1++896ZX1pRWmUGaE2HHyH4Sgv2vZJ/esXmzNig8ZsmW5idYRt4wBQjmQJATeVKj9dwo35unt3LQtcjL8Y7P2YFgxCGld7tF2W0F5ZJPt6r27Qfcb3LB80TaduAAHx6wMdKr26EsAmFZnI0DQJAcJsIx7jpl8PemwuI73O8h8alhAIvJ39wlr4R5k/qYLfgdCOmD/as/1RDKjaEhcTswwWTERTDfAqH96fjPFkq9Q==";
    // 支付宝公钥
//	public static final String RSA_PUBLIC = "";

    private static final int SDK_PAY_FLAG = 5;

    private static final int ADDCHONGZHI = 6;

    private String price;
    private String id;
    private ProgressDialog progressDialog;
    private String gold;
    private EditText min;
    private EditText max;
    private EditText min2;
    private EditText max2;
    private LinearLayout linearLayout;
    private float yDown;
    private float yUp;
    private String FuKuanId;
    private LinearLayout layout_jilv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store);
        progressDialog = new ProgressDialog(this);
        MyData.USER2 = null;
        initData();
        initView();
        getdata();
    }

    private void initData() {
        type = getIntent().getIntExtra("type", -1);
        switch (type) {
            case 1:
                bian = getIntent().getStringExtra("bian");
                user2 = getIntent().getStringExtra("user2");
                break;
            case 0:
                user2 = getIntent().getStringExtra("user2");
                MyData.USER2 = user2;
                break;
        }
        pubuFragment = pubuFragment.newPubu(-1, new PubuFragment.PuBuCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onScrollUp() {
                linearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onScrollDown() {
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initView() {
        layout_jilv = (LinearLayout) findViewById(R.id.jilv);
        layout_jilv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Store.this, ZheKou.class);
                intent.putExtra("user2", user2);
                startActivity(intent);
            }
        });
        linearLayout = (LinearLayout) findViewById(R.id.store_lin2);
        linearLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        yDown = event.getY();
                        Log.i("ydown", yDown + "");
                        break;
                    case MotionEvent.ACTION_MOVE:

                        break;
                    case MotionEvent.ACTION_UP:
                        yUp = event.getY();
                        Log.i("yUp", yUp + "");
                        if (yDown - yUp > 20) {
                            linearLayout.setVisibility(View.GONE);
                        }
                        break;

                }
                return true;
            }
        });
        min = (EditText) findViewById(R.id.store_min);
        max = (EditText) findViewById(R.id.store_max);
        min2 = (EditText) findViewById(R.id.store_min2);
        max2 = (EditText) findViewById(R.id.store_max2);
        dianpuid = (TextView) findViewById(R.id.jiangpin_id);
        zhanshi = (TextView) findViewById(R.id.store_zhanshi);
        tag = (TextView) findViewById(R.id.store_tag);
        miaoshu = (TextView) findViewById(R.id.store_miaoshu);
        choujiang = (TextView) findViewById(R.id.store_choujiang);
        if (type == 0) {
            choujiang.setText(R.string.fukuan);
        }
        choujiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (MyData.UTYPE != -1) {
                    if (type == 1) {
                        if (canChoujiang) {
                            Intent intent = new Intent(Store.this, ChouJiang.class);
                            intent.putExtra("bian", bian);
                            intent.putExtra("user2", user2);
                            startActivity(intent);
                        } else {
                            Toast.makeText(Store.this, R.string.jiangquan_bucunzai, Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
                        builder.setTitle(R.string.tip3);
                        LayoutInflater inflater = LayoutInflater.from(Store.this);
                        View view1 = inflater.inflate(R.layout.layout_inputtext, null, false);
                        final EditText editText = (EditText) view1.findViewById(R.id.editext);
                        builder.setView(view1);
                        builder.setNegativeButton(R.string.cancel, null);
                        builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (editText.getText().toString() != null) {
                                    progressDialog.show();
                                    gold = editText.getText().toString();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HashMap map = new HashMap();
                                            map.put("phone", MyData.PHONE);
                                            map.put("gold", gold);
                                            mhandler.obtainMessage(YANZHENG, HttpUtils.updata(map, MyData.URL_YANZHENG)).sendToTarget();

                                        }
                                    }).start();
                                }
                            }
                        });
                        builder.create().show();
                    }

                } else {
                    startActivity(new Intent(Store.this, Login.class));
                }
            }


        });

        zhanshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Store.this, ZhanShi.class);
                intent.putExtra("src", 0);
                startActivity(intent);
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.store_lin, pubuFragment).commit();
    }

    private void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case 1:
                        HashMap map = new HashMap();
                        map.put("bian", bian);
                        map.put("user2", user2);
                        mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETJP)).sendToTarget();
                        break;
                    case 0:
                        HashMap map1 = new HashMap();
                        map1.put("user2", user2);
                        mhandler.obtainMessage(GETDATA2, HttpUtils.updata(map1, MyData.URL_GETPIC1)).sendToTarget();

                        HashMap map2 = new HashMap();
                        map2.put("user2", user2);
                        mhandler.obtainMessage(GETDATA3, HttpUtils.updata(map2, MyData.URL_GETDESCRIBE)).sendToTarget();
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
                        List<List<Map>> lists = JSON.parseObject(msg.obj.toString(), new TypeReference<List<List<Map>>>() {
                        });
                        if (lists.get(0) != null && !lists.get(0).isEmpty()) {
                            tag.setText(lists.get(0).get(0).get("nm").toString());
                            miaoshu.setText(MyData.ToDBC(lists.get(0).get(0).get("des").toString()));
                            min.setText(lists.get(0).get(0).get("zmin").toString());
                            max.setText(lists.get(0).get(0).get("zmax").toString());
                            min2.setText(lists.get(0).get(0).get("zmin").toString());
                            max2.setText(lists.get(0).get(0).get("zmax2").toString());
                            dianpuid.setText(getString(R.string.id) + lists.get(0).get(0).get("shangid").toString());
                            List<Map> result = new ArrayList<>();
                            for (int i = 1; i < lists.size(); i++) {
                                result.addAll(lists.get(i));
                            }
                            List<Photo> list = JSON.parseObject(JSON.toJSON(result).toString(), new TypeReference<List<Photo>>() {
                            });
                            MyData.USER2 = list.get(0).user2;
                            pubuFragment.onSuccess(list);
                            canChoujiang = true;
                        } else {
                            Toast.makeText(Store.this, R.string.jiangquan_bucunzai, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case GETDATA2:
                        List<List<Photo>> list = JSON.parseObject(msg.obj.toString(), new TypeReference<List<List<Photo>>>() {
                        });
                        List<Photo> result = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            result.addAll(list.get(i));
                        }
                        pubuFragment.onSuccess(result);
                        break;
                    case GETDATA3:
                        List<Map> data2 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Map>>() {
                        });
                        if (data2 != null && !data2.isEmpty()) {
                            tag.setText(data2.get(0).get("nm").toString());
                            miaoshu.setText(MyData.ToDBC(data2.get(0).get("des").toString()));
                            min.setText(data2.get(0).get("zmin").toString());
                            max.setText(data2.get(0).get("zmax").toString());
                            min2.setText(data2.get(0).get("zmin").toString());
                            max2.setText(data2.get(0).get("zmax2").toString());
                            dianpuid.setText(getString(R.string.id) + data2.get(0).get("shangid").toString());

                        }
                        break;
                    case YANZHENG:
                        progressDialog.dismiss();
                        final Map map3 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        int result3 = Integer.parseInt(map3.get("newid").toString());
                        if (result3 > 0) {

                            progressDialog.show();
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HashMap map = new HashMap();
                                    map.put("phone", MyData.PHONE);
                                    map.put("body", MyData.USER2);
                                    map.put("gold", gold);
                                    mhandler.obtainMessage(FUKUAN, HttpUtils.updata(map, MyData.URL_ADDFUKUAN)).sendToTarget();
                                }
                            }).start();

//                            AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
//                            builder.setTitle(R.string.tip4);
//                            LayoutInflater inflater = LayoutInflater.from(Store.this);
//                            View view1 = inflater.inflate(R.layout.layout_inputtext, null, false);
//                            final EditText editText = (EditText) view1.findViewById(R.id.editext);
//                            builder.setView(view1);
//                            builder.setNegativeButton(R.string.cancel, null);
//                            builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    if (editText.getText().toString() != null) {
//                                        progressDialog.show();
//                                        new Thread(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                HashMap map = new HashMap();
//                                                map.put("phone", MyData.PHONE);
//                                                map.put("mima", editText.getText().toString());
//                                                map.put("body", MyData.USER2);
//                                                map.put("gold", gold);
//                                                mhandler.obtainMessage(FUKUAN, HttpUtils.updata(map, MyData.URL_ADDFUKUAN)).sendToTarget();
//                                            }
//                                        }).start();
//                                    }
//                                }
//                            });
//                            builder.create().show();
                        } else if (result3 < 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
                            builder.setTitle(R.string.tips);
                            builder.setMessage(getString(R.string.msg2) + map3.get("que").toString() + getString(R.string.msg3));
                            builder.setNegativeButton(R.string.cancel, null);
                            builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog.show();
                                    price = map3.get("que").toString();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HashMap map = new HashMap();
                                            map.put("phone", MyData.PHONE);
                                            map.put("gold", price);
                                            String result = HttpUtils.updata(map, MyData.URL_ADDCHONGZHI);
                                            Message msg = new Message();
                                            msg.what = ADDCHONGZHI;
                                            msg.obj = result;
                                            mhandler.handleMessage(msg);
                                        }
                                    }).start();

                                }
                            });
                            builder.create().show();
                        }
                        break;
                    case FUKUAN:
                        progressDialog.dismiss();
                        final Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        int result2 = Integer.parseInt(map.get("newid").toString());
                        if (result2 > 0) {
                            FuKuanId = map.get("dingdan").toString();
                            Intent intent = new Intent(Store.this, FuKuanResult.class);
                            intent.putExtra("id", FuKuanId);
                            startActivity(intent);
//                            AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
//                            LayoutInflater inflater = LayoutInflater.from(Store.this);
//                            View view1 = inflater.inflate(R.layout.layout_mes, null, false);
//                            builder.setView(view1);
//                            builder.setPositiveButton(R.string.okay, null);
//                            builder.create().show();
                        } else if (result2 < 0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
                            builder.setTitle(R.string.tips);
                            builder.setMessage(getString(R.string.msg2) + map.get("que").toString() + getString(R.string.msg3));
                            builder.setNegativeButton(R.string.cancel, null);
                            builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    progressDialog.show();
                                    price = map.get("que").toString();
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            HashMap map = new HashMap();
                                            map.put("phone", MyData.PHONE);
                                            map.put("gold", price);
                                            String result = HttpUtils.updata(map, MyData.URL_ADDCHONGZHI);
                                            Message msg = new Message();
                                            msg.what = ADDCHONGZHI;
                                            msg.obj = result;
                                            mhandler.handleMessage(msg);
                                        }
                                    }).start();

                                }
                            });
                            builder.create().show();
                        } else {
                            Toast.makeText(Store.this, R.string.pay_item3, Toast.LENGTH_SHORT).show();
                        }

                        break;
                    case ADDCHONGZHI:
                        progressDialog.dismiss();
                        Map map2 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map2.get("newid").toString()) > 0) {
                            FuKuanId = map2.get("dingdan").toString();
                            id = map2.get("dingdan").toString();
                            // 订单
                            String orderInfo = getOrderInfo(getString(R.string.app_name), user2, price, id);
                            // 对订单做RSA 签名
                            String sign = sign(orderInfo);
                            try {
                                // 仅需对sign 做URL编码
                                sign = URLEncoder.encode(sign, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            // 完整的符合支付宝参数规范的订单信息
                            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                                    + getSignType();

                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    // 构造PayTask 对象
                                    PayTask alipay = new PayTask(Store.this);
                                    // 调用支付接口，获取支付结果
                                    String result = alipay.pay(payInfo);

                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mhandler.sendMessage(msg);
                                }
                            };

                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
                        break;

                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);

                        Log.i("qianbao", payResult.toString());
                        // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                        String resultInfo = payResult.getResult();

                        String resultStatus = payResult.getResultStatus();

                        // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                        if (TextUtils.equals(resultStatus, "9000")) {
                            Intent intent = new Intent(Store.this, FuKuanResult.class);
                            intent.putExtra("id", FuKuanId);
                            startActivity(intent);
//                            AlertDialog.Builder builder = new AlertDialog.Builder(Store.this);
//                            LayoutInflater inflater = LayoutInflater.from(Store.this);
//                            View view1 = inflater.inflate(R.layout.layout_mes, null, false);
//                            builder.setView(view1);
//                            builder.setPositiveButton(R.string.okay, null);
//                            builder.create().show();
                        } else {
                            // 判断resultStatus 为非“9000”则代表可能支付失败
                            // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(Store.this, R.string.pay_item2,
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                                Toast.makeText(Store.this, R.string.pay_item3,
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                        break;
                    }
                }

            } catch (Exception e) {
                Toast.makeText(Store.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }


    };

    /**
     * create the order info. 创建订单信息
     */
    public String getOrderInfo(String subject, String body, String price, String id) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + id + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + MyData.URL_ADDGOLD
                + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    public String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
                Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
