package com.example.lenovo.viewpagerdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lenovo on 2018-04-20.
 */

public class DownLoadJpgTask extends AsyncTask<String, Void, Void> {
    private OkHttpClient okHttpClient;
    private String storePath;
    private ImageView ivImg;

    DownLoadJpgTask(){

    }
    DownLoadJpgTask(ImageView ivImg, OkHttpClient okHttpClient, String storePath){
        this.ivImg = ivImg;
        this.okHttpClient = okHttpClient;
        this.storePath = storePath;
    }
    @Override
    protected Void doInBackground(String... strings) {
        //1. 创建OKHTTPClient对象（已有）
        //2. 创建Request请求对象
        Request request = new Request.Builder()
                .url(strings[0])
                .build();
        //3. 创建Call对象
        Call call = okHttpClient.newCall(request);
        //4. 提交请求并获取响应
        try {
            Response response = call.execute();
            //使用流操作response对象，获取图片
            //得到Response对象中文件的输入流
            InputStream in = response.body().byteStream();
            //创建存储图片的输出流
            OutputStream out = new FileOutputStream(storePath);
            int b = -1;
            while((b = in.read()) != -1){
                out.write(b);
                out.flush();
            }
            in.close();
            out.close();
            Log.i("lww", "图片下载并存储完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        //得到下载的图片
        Bitmap bitmap = BitmapFactory.decodeFile(storePath);
        //将图片显示在图片控件中
        ivImg.setImageBitmap(bitmap);
        super.onPostExecute(aVoid);
    }
}
