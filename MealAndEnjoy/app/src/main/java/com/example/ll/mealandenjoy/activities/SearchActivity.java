package com.example.ll.mealandenjoy.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.SearchAdapter;
import entity.Bean;
import entity.SearchView;
import entity.ShopDemo;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;

/**
 * Created by lenovo on 2018/6/20.
 */

public class SearchActivity extends AppCompatActivity implements SearchView.SearchViewListener {
    /**
     * 搜索结果列表view
     */
    private ListView lvResults;

    /**
     * 搜索view
     */
    private SearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private SearchAdapter resultAdapter;

    /**
     * 数据库数据，总数据
     */
    private List<Bean> dbData;
    private List<ShopDemo> alldata;
    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<Bean> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 10;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        SearchActivity.hintSize = hintSize;
    }

    private List<ShopDemo> hotlist = new ArrayList<>();
    private List<ShopDemo> shoplist = new ArrayList<>();
    private Thread thread;
    private OkHttpClient ok;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Bundle b = msg.getData();
                    String result = b.getString("hotdata");
                    String result1 = b.getString("datalist");
                    Gson gson = new Gson();
                    hotlist = gson.fromJson(result,new TypeToken<List<ShopDemo>>(){}.getType());
                    shoplist = gson.fromJson(result1,new TypeToken<List<ShopDemo>>(){}.getType());
                    alldata = shoplist;
                    break;
            }
            initData();
            initViews();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        ok = new OkHttpClient();
        thread = new Thread(new Mythread());
        thread.start();

    }
    /**
     * 初始化视图
     */
    private void initViews(){
        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);

        searchView.setReslv(lvResults);
        //设置adapter

        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(SearchActivity.this,position+"",Toast.LENGTH_SHORT).show();
                Bean n = resultData.get(position);
                ShopDemo sd = new ShopDemo();
                for (int i =0;i<alldata.size();i++){
                    if(n.getId() == alldata.get(i).getShopdId()){
                        sd = alldata.get(i);
                    }else{
                        continue;
                    }
                }
                Intent intent2 = new Intent();
                intent2.setClass(SearchActivity.this,
                        ShopDetailsActivity.class);
                Bundle bundle = new Bundle();
                ShopDemo shopDemo = sd;
                bundle.putSerializable("shopdemo",shopDemo);
                intent2.putExtras(bundle);
                startActivity(intent2);
            }
        });
    }
    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }
    /**
     * 获取db 数据
     */
    private void getDbData(){
        dbData = new ArrayList<>(shoplist.size());
        for (int i = 0; i < shoplist.size(); i++) {
            File file = new File(SearchActivity.this.getFilesDir().getAbsolutePath()+shoplist.get(i).getShopimg());
            if(file.exists()){
                Bitmap bm = BitmapFactory.decodeFile(SearchActivity.this.getFilesDir().getAbsolutePath()+shoplist.get(i).getShopimg());
                dbData.add(new Bean(bm,shoplist.get(i).getShopdName(),"","",shoplist.get(i).getShopdId()));
            }else {
                Bitmap bm = BitmapFactory.decodeResource(SearchActivity.this.getResources(),R.mipmap.icon);
                dbData.add(new Bean(bm, shoplist.get(i).getShopdName(),"","",shoplist.get(i).getShopdId()));
            }
        }
    }

    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData(){
        hintData = new ArrayList<>(hintSize);
        if(hintSize >= hotlist.size()){
            for(int i = 0;i < hotlist.size();i++){
                hintData.add(hotlist.get(i).getShopdName());
            }
        }else {
            for (int i = 0; i <= hintSize; i++) {
                hintData.add(hotlist.get(i).getShopdName());
            }
        }
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }
    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text){
        if(autoCompleteData == null){
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        }else{
            //根据text获取auto data
            autoCompleteData.clear();
            for(int i =0,count = 0;i<dbData.size() && count<hintSize;i++){
                if(dbData.get(i).getTitle().contains(text.trim())){
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }
        if(autoCompleteAdapter == null){
            autoCompleteAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,autoCompleteData);
        }else{
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text){
        if(resultData == null){
            // 初始化
            resultData = new ArrayList<>();
        }else{
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }
        if(resultAdapter == null){
            resultAdapter = new SearchAdapter(this, resultData, R.layout.item_bean_list);
        }else{
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text){
        //更新数据
        getAutoCompleteData(text);
    }
    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text){
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) SearchActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }

        Toast.makeText(this,"完成搜索",Toast.LENGTH_SHORT).show();
    }
    //点击空白处隐藏软键盘
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {

            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();

            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    class Mythread extends Thread{
        @Override
        public void run(){
            String str = "1";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/MealAndEnjoyServer/shop/gethotshop.action");
            //builder.url("http://172.16.12.228:8080/demo001/ShopDemo/homelist.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);
            Request.Builder builder1 = new Request.Builder();
            builder1.url("http://"+ip+":8080/MealAndEnjoyServer/shop/homelist.action");
            builder1.post(body);
            Request request1 = builder1.build();
            Call call1 = ok.newCall(request1);
            try {
                Response response = call.execute();
                String result = response.body().string();
                Response response1 = call1.execute();
                String result1 = response1.body().string();
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("hotdata",result);
                b.putString("datalist",result1);
                msg.setData(b);
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
