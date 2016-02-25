package com.smapley.baibaohe.utls.http.bitmap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by smapley on 2015/6/7.
 */
public class DoBitmap {

    public static final int FromPath = 1;
    public static final int FromByteArray = 2;

    /**
     * ͼƬ�ߴ�ѹ��
     * ��ȡbitmap
     */
    public static Bitmap getBitmap(int From, Object data, int reqWidth, int reqHeight) {

        // ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        switch (From) {
            case FromPath:
                BitmapFactory.decodeFile((String) data, options);
                // �������涨��ķ�������inSampleSizeֵ
                options.inSampleSize = getSize(options, reqWidth, reqHeight);
                // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
                options.inJustDecodeBounds = false;
                return rotaingImageView(DoBitmap.getPictureDegree((String) data), BitmapFactory.decodeFile((String) data, options));
            case FromByteArray:
                byte[] mdata = (byte[]) data;
                BitmapFactory.decodeByteArray(mdata, 0, mdata.length, options);
                // �������涨��ķ�������inSampleSizeֵ
                options.inSampleSize = getSize(options, reqWidth, reqHeight);
                // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeByteArray(mdata, 0, mdata.length, options);
        }

        return null;
    }


    /**
     * ����Դ�ļ���ȡbitmap
     */
    public static Bitmap getBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // ��һ�ν�����inJustDecodeBounds����Ϊtrue������ȡͼƬ��С
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // �������涨��ķ�������inSampleSizeֵ
        options.inSampleSize = getSize(options, reqWidth, reqHeight);
        // ʹ�û�ȡ����inSampleSizeֵ�ٴν���ͼƬ
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * ��ȡbitmap���ű���
     */
    public static int getSize(BitmapFactory.Options options,
                              int reqWidth, int reqHeight) {
        // ԴͼƬ�ĸ߶ȺͿ��
        final int height = options.outHeight;
        final int width = options.outWidth;
        System.out.println(width + "_____" + height);
        System.out.println(reqWidth + "_____" + reqHeight);
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // �����ʵ�ʿ�ߺ�Ŀ���ߵı���
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // ѡ���͸�����С�ı�����ΪinSampleSize��ֵ���������Ա�֤����ͼƬ�Ŀ�͸�
            // һ��������ڵ���Ŀ��Ŀ�͸ߡ�
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * ���ļ�ͼƬ��ȡ��ת�Ƕ�
     *
     * @param path �ļ�·��
     * @return ��ת�Ƕ�
     */
    public static int getPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * ��תͼƬ
     *
     * @param argu ��ת�Ƕ�
     */
    public static Bitmap rotaingImageView(int argu, Bitmap bitmap) {
        //��תͼƬ ����
        Matrix matrix = new Matrix();
        matrix.postRotate(argu);
        // �����µ�ͼƬ
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * ͼƬ����ѹ��
     */
    public static byte[] compressToBytes(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
            baos.reset();//����baos�����baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��
            options -= 10;//ÿ�ζ�����10
        }
        return baos.toByteArray();
    }

    /**
     * ͼƬ����ѹ��
     */
    public static Bitmap compressToBitmap(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) {  //ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
            baos.reset();//����baos�����baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//����ѹ��options%����ѹ��������ݴ�ŵ�baos��
            options -= 10;//ÿ�ζ�����10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//��ѹ���������baos��ŵ�ByteArrayInputStream��
        Bitmap result = BitmapFactory.decodeStream(isBm, null, null);//��ByteArrayInputStream��������ͼƬ
        return result;
    }
}
