package com.smapley.baibaohe.utls.http;

import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpUtils {

    private static final String TAG = "HttpUtils";
    public static final int File = 1;
    public static final int Bytes = 2;

    public static String updata(HashMap<String, Object> map, String urlString) {
        // 作为StringBuffer初始化的字符串
        StringBuffer buffer = new StringBuffer();
        try {
            if (map != null && !map.isEmpty()) {
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    // 完成转码操作
                    buffer.append(entry.getKey())
                            .append("=")
                            .append(URLEncoder.encode(entry.getValue()
                                    .toString(), "utf-8")).append("&");
                }
                buffer.deleteCharAt(buffer.length() - 1);
            }
            Log.i(TAG, "----------------------->connection：" + urlString);
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);// 表示从服务器获取数据
            connection.setDoOutput(true);// 表示向服务器写数据
            Log.i(TAG, "--------->data:" + buffer.toString());
            // 获得上传信息的字节大小以及长度
            byte[] mydata = buffer.toString().getBytes();
            // 表示设置请求体的类型是文本类型
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",
                    String.valueOf(mydata.length));
            // 获得输出流,向服务器输出数据
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(mydata, 0, mydata.length);
            outputStream.close();
            // 获得服务器响应的结果和状态码
            int responseCode = connection.getResponseCode();
            Log.i(TAG, "--------->responseCode:" + responseCode);
            if (responseCode == 200) {
                return changeInputStream(connection.getInputStream());
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return "";
    }

    // 上传代码，第一个参数，为要使用的URL，第二个参数，为表单内容，第三个参数为要上传的文件，可以上传多个文件，这根据需要页定
    public static String post(String actionUrl, Map<String, Object> params, Map<String, Object> files, int type) {

        String BOUNDARY = java.util.UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";
        try {
            URL uri = new URL(actionUrl);
            Log.i(TAG, "----------------------->connection：" + actionUrl);
            HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setDoInput(true);// 允许输入
            conn.setDoOutput(true);// 允许输出
            conn.setUseCaches(false);
            conn.setRequestMethod("POST"); // Post方式
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Charsert", "UTF-8");
            conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                    + ";boundary=" + BOUNDARY);
            // 首先组拼文本类型的参数
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition: form-data; name=\""
                        + entry.getKey() + "\"" + LINEND);
                sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
                sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
                sb.append(LINEND);
                sb.append(entry.getValue());
                sb.append(LINEND);
            }
            DataOutputStream outStream = new DataOutputStream(
                    conn.getOutputStream());
            outStream.write(sb.toString().getBytes());
            int i = -1;
            // 发送文件数据
            if (files != null)
                for (Map.Entry<String, Object> file : files.entrySet()) {
                    i = i + 1;
                    StringBuilder sb1 = new StringBuilder();
                    sb1.append(PREFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append("Content-Disposition: form-data; name=\"name" + i + "\"; filename=\""
                            + file.getKey() + "\"" + LINEND);
                    sb1.append("Content-Type: multipart/form-data; charset="
                            + CHARSET + LINEND);
                    sb1.append(LINEND);
                    Log.i("file", "---------->>" + sb1.toString());
                    outStream.write(sb1.toString().getBytes());
                    InputStream is = null;
                    switch (type) {
                        case File:
                            is = new FileInputStream((File) file.getValue());
                            break;
                        case Bytes:
                            is = new ByteArrayInputStream((byte[]) file.getValue());
                            break;
                    }
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        outStream.write(buffer, 0, len);
                    }
                    is.close();
                    outStream.write(LINEND.getBytes());
                }
            // 请求结束标志
            byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
            outStream.write(end_data);
            outStream.flush();
            outStream.close();
            // 获得服务器响应的结果和状态码
            int responseCode = conn.getResponseCode();
            Log.i(TAG, "--------->responseCode:" + responseCode);
            if (responseCode == 200) {
                return changeInputStream(conn.getInputStream());
            }
            conn.disconnect();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return "";
    }


    private static final int TIME_OUT = 10 * 10000000; //超时时间
    private static final String CHARSET = "utf-8"; //设置编码
    public static final String SUCCESS = "1";
    public static final String FAILURE = "0";

    /* 上传文件至Server，uploadUrl：接收文件的处理页面 */
    public static String uploadFile(File file, String RequestURL) {

        String BOUNDARY = UUID.randomUUID().toString(); //边界标识 随机生成 String PREFIX = "--" , LINE_END = "\r\n";
        String CONTENT_TYPE = "multipart/form-data"; //内容类型
        try {
            URL url = new URL(RequestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(TIME_OUT);
            conn.setConnectTimeout(TIME_OUT);
            conn.setDoInput(true); //允许输入流
            conn.setDoOutput(true); //允许输出流
            conn.setUseCaches(false); //不允许使用缓存
            conn.setRequestMethod("POST"); //请求方式
            conn.setRequestProperty("Charset", CHARSET);
            //设置编码
            conn.setRequestProperty("connection", "keep-alive");
            conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary=" + BOUNDARY);
            String PREFIX = "--", LINE_END = "\r\n";
            if (file != null) {
                /** * 当文件不为空，把文件包装并且上传 */
                OutputStream outputSteam = conn.getOutputStream();
                DataOutputStream dos = new DataOutputStream(outputSteam);
                StringBuffer sb = new StringBuffer();
                sb.append(PREFIX);
                sb.append(BOUNDARY);
                sb.append(LINE_END);
                /**
                 * 这里重点注意：
                 * name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                sb.append("Content-Disposition: form-data; name=\"name\"; filename=\"" + file.getName() + "\"" + LINE_END);
                sb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
                sb.append(LINE_END);
                dos.write(sb.toString().getBytes());
                InputStream is = new FileInputStream(file);
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = is.read(bytes)) != -1) {
                    dos.write(bytes, 0, len);
                }
                is.close();
                dos.write(LINE_END.getBytes());
                byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END).getBytes();
                dos.write(end_data);
                dos.flush();
                /**
                 * 获取响应码 200=成功
                 * 当响应成功，获取响应的流
                 */
                int res = conn.getResponseCode();
                Log.e(TAG, "response code:" + res);
                if (res == 200) {
                    Log.e(TAG, "response code:" + changeInputStream(conn.getInputStream()));

                    return "1";

                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "0";
    }


    private static String changeInputStream(InputStream inputStream) {
        // TODO Auto-generated method stub

        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream));
        StringBuilder StringBuilder = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                StringBuilder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = StringBuilder.toString();
        Log.i(TAG, "------------->result:" + result);
        return result;
    }

}
