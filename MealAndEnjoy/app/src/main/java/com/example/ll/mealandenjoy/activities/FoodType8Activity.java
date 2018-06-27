package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.TypeOneAdapter;
import entity.HomeShopList;
import entity.ShopDemo;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;

public class FoodType8Activity extends AppCompatActivity {
    private OkHttpClient ok;
    private Thread thread;
    private ListView listView;
    private ImageButton imgbtnRetn;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Bundle b = msg.getData();
                    Gson gson = new Gson();
                    String shoplist = b.getString("slist");
                    final ArrayList<ShopDemo> shopDemos = gson.fromJson(shoplist,new TypeToken<ArrayList<ShopDemo>>(){}.getType());
                    listView = findViewById(R.id.type1);
                    TypeOneAdapter typeOneAdapter = new TypeOneAdapter(getApplicationContext(),R.layout.activity_delicious_food_item,prepaerDate(shopDemos));
                    listView.setAdapter(typeOneAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent2 = new Intent();
                            intent2.setClass(getApplicationContext(),
                                    ShopDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            ShopDemo shopDemo = shopDemos.get(position);
                            bundle.putSerializable("shopdemo",shopDemo);
                            intent2.putExtras(bundle);
                            /*activity1.getApplicationContext().startActivity(intent2);*/
                            intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            getApplicationContext().startActivity(intent2);
                        }
                    });
                    //动态获取listview控件的高度
                    ViewGroup.LayoutParams params = listView.getLayoutParams();
                    ListAdapter listAdapter = listView.getAdapter();
                    View listitem = listAdapter.getView(0,null,listView);
                    listitem.measure(0,0);
                    params.height = listAdapter.getCount() * listitem.getMeasuredHeight();
                    listView.setLayoutParams(params);

                    break;

            };
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_type8);
        listView = findViewById(R.id.type1);
        imgbtnRetn=findViewById(R.id.imgbtn_retn);
        //为返回按钮注册点击事件监听器
        imgbtnRetn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ok = new OkHttpClient();
        thread = new Mythread1();
        thread.start();
    }
    public List<HomeShopList> prepaerDate(ArrayList<ShopDemo> list){
        List<HomeShopList> shopLists = new ArrayList<>();
        File dir = new File(this.getFilesDir().getAbsolutePath()+"/img");
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
    //请求图片list子线程
    class MyThread extends Thread {
        @Override
        public void run(){
            String str = "美食页list请求";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/MealAndEnjoyServer/shop/getDelishops.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);

            try {
                //接受从服务器端返回的数据
                Response response = call.execute();
                String jshoplist = response.body().string();
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("slist",jshoplist);
                msg.setData(b);
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.removeCallbacks(thread);
        }
    }
    class Mythread1 extends Thread{
        @Override
        public void run() {
            FormBody.Builder formBuilder = new FormBody.Builder();
            //添加键值对数据
            String typeid =String.valueOf(8);
            formBuilder.add("typeID", typeid);
            FormBody formBody = formBuilder.build();
            //创建Request请求对象
            Request request = new Request.Builder()
                    .url("http://"+ip+":8080/MealAndEnjoyServer/shop/type.action")
                    .post(formBody)
                    .build();
            //3. 创建用于提交请求的Call对象
            Call call = ok.newCall(request);
            //4. 提交异步请求，并获取响应数据
            try {
                Response response = call.execute();
                String jshoplist = response.body().string();
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("slist",jshoplist);
                msg.setData(b);
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
