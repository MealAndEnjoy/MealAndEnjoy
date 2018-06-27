package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.CustomSIAdapter;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import static fragment.Home_Fragment.ip;

/**
 * Created by lenovo on 2018/6/12.
 */

public class grid_activity extends AppCompatActivity {
    private GridView gridView;
    private OkHttpClient ok;
    private Thread thread;
    private List<String> s;
    private ImageView imageView;
    private String str;
    private Handler mhandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case  1:
                    s = new ArrayList<>();
                    Bundle b = msg.getData();
                    String res = b.getString("silist");
                    Gson gson = new Gson();
                    s = new ArrayList<>();
                    s = gson.fromJson(res,new TypeToken<List<String>>(){}.getType());

                    CustomSIAdapter siAdapter = new CustomSIAdapter(grid_activity.this, R.layout.shopgrid_item,s);
                    gridView.setAdapter(siAdapter);
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            gridView.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            Bitmap bm = BitmapFactory.decodeFile(grid_activity.this.getFilesDir().getAbsolutePath()+s.get(position));
                            imageView.setImageBitmap(bm);
                        }
                    });
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imageView.setVisibility(View.GONE);
                            gridView.setVisibility(View.VISIBLE);
                        }
                    });
                    break;

            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_grid);

        ok = new OkHttpClient();
        gridView = findViewById(R.id.shop_grid);
        imageView = findViewById(R.id.grid_img);
        File dir = new File(this.getFilesDir().getAbsolutePath()+"/img");
        if(!dir.exists()){
            dir.mkdir();
        }
        //使用intent传入shopid并赋值给str
        //str = intent...传入的shopid
        Intent intent = getIntent();
        int ShopId = intent.getIntExtra("shopId",0);
        str = String.valueOf(ShopId);
        thread = new Thread(new MyThread());
        thread.start();
    }

    class MyThread extends Thread {
        @Override
        public void run(){

//            str = "1";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/MealAndEnjoyServer/shop/getshopgrid.action");
            //builder.url("http://172.16.12.228:8080/demo001/ShopDemo/homelist.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);

            try {
                Response response = call.execute();

                String jsilist = response.body().string();
                Gson gson = new Gson();
                Log.i("ceshi",jsilist);
                List<String> silist = gson.fromJson(jsilist,new TypeToken<List<String>>(){}.getType());
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("silist",jsilist);
//                for(int i = 0;i<silist.size();i++){
//                    String n = String.valueOf(i);
//
//                    b.putString(n,gson.toJson(silist.get(i)));
//
//                }
                msg.setData(b);
                msg.what = 1;
                mhandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mhandler.removeCallbacks(thread);
        }
    }



}
