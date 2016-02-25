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
     * 图片尺寸压缩
     * 获取bitmap
     */
    public static Bitmap getBitmap(int From, Object data, int reqWidth, int reqHeight) {

        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        switch (From) {
            case FromPath:
                BitmapFactory.decodeFile((String) data, options);
                // 调用上面定义的方法计算inSampleSize值
                options.inSampleSize = getSize(options, reqWidth, reqHeight);
                // 使用获取到的inSampleSize值再次解析图片
                options.inJustDecodeBounds = false;
                return rotaingImageView(DoBitmap.getPictureDegree((String) data), BitmapFactory.decodeFile((String) data, options));
            case FromByteArray:
                byte[] mdata = (byte[]) data;
                BitmapFactory.decodeByteArray(mdata, 0, mdata.length, options);
                // 调用上面定义的方法计算inSampleSize值
                options.inSampleSize = getSize(options, reqWidth, reqHeight);
                // 使用获取到的inSampleSize值再次解析图片
                options.inJustDecodeBounds = false;
                return BitmapFactory.decodeByteArray(mdata, 0, mdata.length, options);
        }

        return null;
    }


    /**
     * 从资源文件获取bitmap
     */
    public static Bitmap getBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = getSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 获取bitmap缩放比例
     */
    public static int getSize(BitmapFactory.Options options,
                              int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        System.out.println(width + "_____" + height);
        System.out.println(reqWidth + "_____" + reqHeight);
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 从文件图片获取旋转角度
     *
     * @param path 文件路径
     * @return 旋转角度
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
     * 旋转图片
     *
     * @param argu 旋转角度
     */
    public static Bitmap rotaingImageView(int argu, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(argu);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 图片质量压缩
     */
    public static byte[] compressToBytes(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        return baos.toByteArray();
    }

    /**
     * 图片质量压缩
     */
    public static Bitmap compressToBitmap(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 300) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap result = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return result;
    }
}
