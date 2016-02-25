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
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alipay.sdk.app.PayTask;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.pay.PayResult;
import com.smapley.baibaohe.pay.SignUtils;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by smapley on 2015/7/9.
 */
public class QianBao extends Activity {

    private TextView item1;
    private TextView item2;
    private TextView item3;
    private TextView item4;
    private TextView gold;
    private TextView bao;
    // �̻�PID
    public static final String PARTNER = "2088021078637071";
    // �̻��տ��˺�
    public static final String SELLER = "316344445@qq.com";
    // �̻�˽Կ��pkcs8��ʽ
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJbSGOfIspREM+Bd3MBt9Xxs1egAJFpWMFlBVLuTJaTiiLlg7FwqFOc3sZ4R5faN4BjjuMpqZlMkYlCq2tsqCPyB2LOJEYWCcVKfYICUGDgpxkspt9UB0lDNSGIW64sIKmvcFOS349wv0GRIL+Jp5rFDLDvjZ1f41kWKKVzUfhgVAgMBAAECgYEAiGmG9T3lp4z4jtrWq4XJH70gzDI0rzB9kn0wsmepCLWMjH9JySKWvXr2P85YfORd6KUvooUR/+lMs0GVqd0fOjjfzaE2CXoW2mKd2pkb++KUQ3u1Yc5SVkVwItrPUWOTTHQqBav01OoAceE/FhL9BihaPQ9e2iy6NwxvvXpeOBECQQDx4ju6K8/7XV1yDJGlgoeuJQBAIqYvpTb5AyHzErmIfAqt8BGwp56/TxVu7WyNHAx5HMcchvmpx2OSQ27HzNU/AkEAn59fkRsLBYw1MTsjZqcmdf9qdPjRugZiga9eztOijLz6GhgRPqShOcEfcD1vr7Csm+ZIkBjwD5IE4x8VwYeZqwJAJJGDXh4Jj4MKAZgM3Ozi/lzxsMCMR1++896ZX1pRWmUGaE2HHyH4Sgv2vZJ/esXmzNig8ZsmW5idYRt4wBQjmQJATeVKj9dwo35unt3LQtcjL8Y7P2YFgxCGld7tF2W0F5ZJPt6r27Qfcb3LB80TaduAAHx6wMdKr26EsAmFZnI0DQJAcJsIx7jpl8PemwuI73O8h8alhAIvJ39wlr4R5k/qYLfgdCOmD/as/1RDKjaEhcTswwWTERTDfAqH96fjPFkq9Q==";
    // ֧������Կ
//	public static final String RSA_PUBLIC = "";

    private static final int SDK_PAY_FLAG = 1;

    private static final int ADDCHONGZHI = 2;

    private final int GETDATA = 3;

    private String price;
    private String id;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qianbao);
        progressDialog = new ProgressDialog(this);
        initView();
        getdata();
    }

    private void initView() {
        item1 = (TextView) findViewById(R.id.qianbao_item1);
        item2 = (TextView) findViewById(R.id.qianbao_item2);
        item3 = (TextView) findViewById(R.id.qianbao_item3);
        item4 = (TextView) findViewById(R.id.qianbao_item4);
        gold = (TextView) findViewById(R.id.qianbao_gold);
        bao = (TextView) findViewById(R.id.qianbao_bao);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(QianBao.this);
                builder.setTitle(R.string.tip2);
                LayoutInflater inflater = LayoutInflater.from(QianBao.this);
                View view1 = inflater.inflate(R.layout.layout_inputtext, null, false);
                final EditText editText = (EditText) view1.findViewById(R.id.editext);
                builder.setView(view1);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        final String num = editText.getText().toString();
                        if (num != null) {
                            progressDialog.show();
                            price = num;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HashMap map = new HashMap();
                                    map.put("phone", MyData.PHONE);
                                    map.put("gold", num);
                                    String result = HttpUtils.updata(map, MyData.URL_ADDCHONGZHI);
                                    Message msg = new Message();
                                    msg.what = ADDCHONGZHI;
                                    msg.obj = result;
                                    mHandler.handleMessage(msg);
                                }
                            }).start();


                        }
                    }
                });
                builder.create().show();
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QianBao.this, FuKuan.class));

            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QianBao.this, ShouKuan.class));
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QianBao.this, TiXian.class));
            }
        });
    }


    private void getdata() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("phone", MyData.PHONE);
                mHandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETGOLD)).sendToTarget();
            }
        }).start();
    }

    /**
     * create the order info. ����������Ϣ
     */
    public String getOrderInfo(String subject, String body, String price, String id) {
        // ǩԼ���������ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // ǩԼ����֧�����˺�
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // �̻���վΨһ������
        orderInfo += "&out_trade_no=" + "\"" + id + "\"";

        // ��Ʒ����
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // ��Ʒ����
        orderInfo += "&body=" + "\"" + body + "\"";

        // ��Ʒ���
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // �������첽֪ͨҳ��·��
        orderInfo += "&notify_url=" + "\"" + MyData.URL_ADDGOLD
                + "\"";

        // ����ӿ����ƣ� �̶�ֵ
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // ֧�����ͣ� �̶�ֵ
        orderInfo += "&payment_type=\"1\"";

        // �������룬 �̶�ֵ
        orderInfo += "&_input_charset=\"utf-8\"";

        // ����δ����׵ĳ�ʱʱ��
        // Ĭ��30���ӣ�һ����ʱ���ñʽ��׾ͻ��Զ����رա�
        // ȡֵ��Χ��1m��15d��
        // m-���ӣ�h-Сʱ��d-�죬1c-���죨���۽��׺�ʱ����������0��رգ���
        // �ò�����ֵ������С���㣬��1.5h����ת��Ϊ90m��
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_tokenΪ���������Ȩ��ȡ����alipay_open_id,���ϴ˲����û���ʹ����Ȩ���˻�����֧��
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // ֧��������������󣬵�ǰҳ����ת���̻�ָ��ҳ���·�����ɿ�
        orderInfo += "&return_url=\"m.alipay.com\"";

        // �������п�֧���������ô˲���������ǩ���� �̶�ֵ ����ҪǩԼ���������п����֧��������ʹ�ã�
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. �����̻������ţ���ֵ���̻���Ӧ����Ψһ�����Զ����ʽ�淶��
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
     * sign the order info. �Զ�����Ϣ����ǩ��
     *
     * @param content ��ǩ��������Ϣ
     */
    public String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. ��ȡǩ����ʽ
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case GETDATA:
                        List<Map> result = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Map>>() {
                        });
                        if (result!=null&&!result.isEmpty()) {
                            gold.setText(result.get(0).get("gold").toString());
                            bao.setText(result.get(0).get("bao").toString());
                        }
                        break;
                    case ADDCHONGZHI:
                        progressDialog.dismiss();
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("newid").toString()) > 0) {
                            id = map.get("dingdan").toString();
                            // ����
                            String orderInfo = getOrderInfo(getString(R.string.app_name), "0", price, id);
                            // �Զ�����RSA ǩ��
                            String sign = sign(orderInfo);
                            try {
                                // �����sign ��URL����
                                sign = URLEncoder.encode(sign, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            // �����ķ���֧���������淶�Ķ�����Ϣ
                            final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                                    + getSignType();

                            Runnable payRunnable = new Runnable() {

                                @Override
                                public void run() {
                                    // ����PayTask ����
                                    PayTask alipay = new PayTask(QianBao.this);
                                    // ����֧���ӿڣ���ȡ֧�����
                                    String result = alipay.pay(payInfo);

                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };

                            // �����첽����
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
                        break;

                    case SDK_PAY_FLAG: {
                        PayResult payResult = new PayResult((String) msg.obj);

                        Log.i("qianbao", payResult.toString());
                        // ֧�������ش˴�֧���������ǩ�������֧����ǩ����Ϣ��ǩԼʱ֧�����ṩ�Ĺ�Կ����ǩ
                        String resultInfo = payResult.getResult();

                        String resultStatus = payResult.getResultStatus();

                        // �ж�resultStatus Ϊ��9000�������֧���ɹ�������״̬�������ɲο��ӿ��ĵ�
                        if (TextUtils.equals(resultStatus, "9000")) {
                            getdata();
                            Toast.makeText(QianBao.this, R.string.pay_item1,
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // �ж�resultStatus Ϊ�ǡ�9000����������֧��ʧ��
                            // ��8000������֧�������Ϊ֧������ԭ�����ϵͳԭ���ڵȴ�֧�����ȷ�ϣ����ս����Ƿ�ɹ��Է�����첽֪ͨΪ׼��С����״̬��
                            if (TextUtils.equals(resultStatus, "8000")) {
                                Toast.makeText(QianBao.this, R.string.pay_item2,
                                        Toast.LENGTH_SHORT).show();

                            } else {
                                // ����ֵ�Ϳ����ж�Ϊ֧��ʧ�ܣ������û�����ȡ��֧��������ϵͳ���صĴ���
                                Toast.makeText(QianBao.this, R.string.pay_item3,
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                        break;
                    }
                    default:
                        break;
                }
            } catch (Exception e) {
            }
        }
    };

}
