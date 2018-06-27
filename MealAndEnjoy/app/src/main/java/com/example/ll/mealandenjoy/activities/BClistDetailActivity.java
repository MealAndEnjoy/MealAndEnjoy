package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.CustomHomeAdapter;
import entity.HomeShopList;
import entity.ShopDemo;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;

/**
 * Created by lenovo on 2018/6/26.
 */

public class BClistDetailActivity extends AppCompatActivity {
    private String id;
    private Thread thread;
    private OkHttpClient ok;
    private ArrayList<ShopDemo> s;
    private ListView listView;
    private Handler handler = new Handler(){
      @Override
        public void handleMessage(Message msg){
          super.handleMessage(msg);
          switch (msg.what){
              case 1:
                  s = new ArrayList<>();
                  Bundle b = msg.getData();
                  Gson gson = new Gson();
                  String shoplist = b.getString("bcdetail");
                  s = gson.fromJson(shoplist,new TypeToken<ArrayList<ShopDemo>>(){}.getType());
                  listView = findViewById(R.id.bclist_detail);
                  CustomHomeAdapter customHomeAdapter = new CustomHomeAdapter(getApplicationContext(),R.layout.main_list_item,preparedata(s));
                  listView.setAdapter(customHomeAdapter);
                  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                          Intent intent2 = new Intent();
                          intent2.setClass(BClistDetailActivity.this,
                                  ShopDetailsActivity.class);
                          Bundle bundle = new Bundle();
                          ShopDemo shopDemo = s.get(position);
                          bundle.putSerializable("shopdemo",shopDemo);
                          intent2.putExtras(bundle);
                          startActivity(intent2);
                      }
                  });
                  break;
          }
      }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bclist_detail);
        ok = new OkHttpClient();
        //获得intent的数据
        Intent intent = getIntent();
//        Bundle bundle = intent.getBundleExtra("cName");
//        String cname = bundle.getString("cName");
        Bundle bundle = intent.getExtras();
        id = bundle.getString("cName");
        thread = new Thread(new MyThread());
        thread.start();
    }

    class MyThread extends Thread {
        @Override
        public void run(){
            String str = "首页list请求";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,id);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/MealAndEnjoyServer/shop/bcdetail.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);

            try {
                Response response = call.execute();
                String bcdetail = response.body().string();
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("bcdetail",bcdetail);
                msg.setData(b);
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public List<HomeShopList> preparedata(ArrayList<ShopDemo> list){
        List<HomeShopList> shopLists = new ArrayList<>();
        File dir = new File(BClistDetailActivity.this.getFilesDir().getAbsolutePath()+"/img");
        if(!dir.exists()){
            dir.mkdir();
        }
        for(int i =0;i < list.size();i++){
            HomeShopList shopList = new HomeShopList();
            shopList.setShopname(list.get(i).getShopdName());
            shopList.setShopimg(list.get(i).getShopimg());
            shopList.setAllNum(list.get(i).getAllNum());
            shopList.setAvgCost(list.get(i).getAvgCost());
            shopLists.add(shopList);
        }
        return shopLists;
    }
}
