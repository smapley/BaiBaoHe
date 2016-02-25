package com.smapley.baibaohe.utls.http.bitmap;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.smapley.baibaohe.utls.MyData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by smapley on 2015/6/20.
 */
public class LoadImage {
    private ImageFileCache fileCache;
    public int LOCK;

    public void Load(List<String> urls) {
        fileCache = new ImageFileCache();
        LOCK = urls.size();
        for (String url : urls) {
            Bitmap result = fileCache.getImage(url);
            if (result == null) {
                GetPic_AsyncTask task = new GetPic_AsyncTask(url);
                task.execute(url);
                System.out.println(url + "从网络获取......");

            } else {
                LOCK--;
            }
        }

    }

    /**
     * 网络获取图片
     */
    public class GetPic_AsyncTask extends AsyncTask<String, Void, Bitmap> {
        private String url;

        GetPic_AsyncTask(String url) {
            this.url = url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 在后台加载图片。
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(MyData.URL_FILE_YUAN + params[0]);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);
                //connection.setDoOutput(true);
                Log.i("getpic", "------------->>" + url);
                int code = connection.getResponseCode();
                Log.i("getpic", "------------->>" + code);
                if (code == 200) {


                    System.out.println("从web获取" + params[0]);

                    InputStream inputStream = connection.getInputStream();
                    ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
                    int ch;
                    while ((ch = inputStream.read()) != -1) {
                        bytestream.write(ch);
                    }
                    byte imgdata[] = bytestream.toByteArray();
                    bytestream.close();
                    Bitmap bitmap = DoBitmap.getBitmap(DoBitmap.FromByteArray, imgdata,
                            800, 800);
                    Log.i("getpic", "------------->>1");

                    return bitmap;

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            LOCK--;
            if (result != null) {
                fileCache.saveBitmap(result, url);
            }

        }
    }

}
