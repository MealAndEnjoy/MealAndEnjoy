package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;


import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import adapters.EntertainmentAdapter;
import adapters.MyPagerAdapter;
import entity.HomeShopList;
import entity.ShopDemo;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;


public class EntertainmentActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnTouchListener{
    public static final int VIEW_PAGER_DELAY = 2000;
    private MyPagerAdapter mAdapter;
    private List<ImageView> mItems;
    private ImageView[] mBottomImages;
    private LinearLayout mBottomLiner;
    private ViewPager mViewPager;

    private int currentViewPagerItem;
    //是否自动播放
    private boolean isAutoPlay;

    private EntertainmentActivity.MyHandler mHandler;
    private Thread mThread;
    //主线程新建handler获得子线程服务器请求数据更新UI
    private ListView listView;
    private OkHttpClient okHttpClient;
    private ArrayList<ShopDemo> s;
    //获取布局使用
    private ImageButton btn_return2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        mHandler = new EntertainmentActivity.MyHandler(this);
        //配置轮播图ViewPager
        mViewPager = ((ViewPager) findViewById(R.id.live_view_pager));
        mItems = new ArrayList<>();
        mAdapter = new MyPagerAdapter(mItems, this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);
        isAutoPlay = true;
        //获取控件对象
        btn_return2 = findViewById(R.id.imgbtn_chcity);
        //结束当前Activity跳转至上一Activity
        btn_return2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //TODO: 添加ImageView
        addImageView();
        mAdapter.notifyDataSetChanged();
        //设置底部4个小点
        setBottomIndicator();

        //获取listView控件
        listView = findViewById(R.id.lv_foodshops);
         //实例化okhttp对象
        okHttpClient = new OkHttpClient();
        //调用下载图片的子线程
        mThread = new Thread(new MyThread());
        mThread.start();
    }
    private void addImageView(){
        ImageView view0 = new ImageView(this);
        view0.setImageResource(R.mipmap.p5);
        ImageView view1 = new ImageView(this);
        view1.setImageResource(R.mipmap.p2);
        ImageView view2 = new ImageView(this);
        view2.setImageResource(R.mipmap.p3);
        ImageView view3 = new ImageView(this);
        view3.setImageResource(R.mipmap.p4);

        view0.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view3.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mItems.add(view0);
        mItems.add(view1);
        mItems.add(view2);
        mItems.add(view3);
    }

    private void setBottomIndicator() {
        //获取指示器(下面三个小点)
        mBottomLiner = (LinearLayout) findViewById(R.id.live_indicator);
        //右下方小圆点
        mBottomImages = new ImageView[mItems.size()];
        for (int i = 0; i < mBottomImages.length; i++) {
            ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.setMargins(5, 0, 5, 0);
            imageView.setLayoutParams(params);
            //如果当前是第一个 设置为选中状态
            if (i == 0) {
                imageView.setImageResource(R.drawable.indicator_select);
            } else {
                imageView.setImageResource(R.drawable.indicator_no_select);
            }
            mBottomImages[i] = imageView;
            //添加到父容器
            mBottomLiner.addView(imageView);
        }

        //让其在最大值的中间开始滑动, 一定要在 mBottomImages初始化之前完成
        int mid = MyPagerAdapter.MAX_SCROLL_VALUE / 2;
        mViewPager.setCurrentItem(mid);
        currentViewPagerItem = mid;

        //定时发送消息
        mThread = new Thread(){
            @Override
            public void run() {
                super.run();
                while (true) {
                    mHandler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(HomeActivity.VIEW_PAGER_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        mThread.start();
    }






    ///////////////////////////////////////////////////////////////////////////
    // ViewPager的监听事件
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        currentViewPagerItem = position;
        if (mItems != null) {
            position %= mBottomImages.length;
            int total = mBottomImages.length;

            for (int i = 0; i < total; i++) {
                if (i == position) {
                    mBottomImages[i].setImageResource(R.drawable.indicator_select);
                } else {
                    mBottomImages[i].setImageResource(R.drawable.indicator_no_select);
                }
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isAutoPlay = false;
                break;
            case MotionEvent.ACTION_UP:
                isAutoPlay = true;
                break;
        }
        return false;
    }

    ///////////////////////////////////////////////////////////////////////////
    // 为防止内存泄漏, 声明自己的Handler并弱引用Activity
    ///////////////////////////////////////////////////////////////////////////
    private static class MyHandler extends Handler {
        private WeakReference<EntertainmentActivity> mWeakReference;

        public MyHandler(EntertainmentActivity activity) {
            mWeakReference = new WeakReference<EntertainmentActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    EntertainmentActivity activity = mWeakReference.get();
                    if (activity.isAutoPlay) {

                        activity.mViewPager.setCurrentItem(++activity.currentViewPagerItem);
                    }
                    break;
                case 1:
                     final EntertainmentActivity activity2 = mWeakReference.get();
                    activity2.s = new ArrayList<>();
                    Bundle b = msg.getData();
                    Set<String> keySet = b.keySet();
                    Gson gson = new Gson();
                    String shoplist = b.getString("slist");
                    activity2.s = gson.fromJson(shoplist,new TypeToken<ArrayList<ShopDemo>>(){}.getType());
                     activity2.listView = activity2.findViewById(R.id.lv_entertainment);
                    EntertainmentAdapter entertainmentAdapter = new EntertainmentAdapter(activity2,R.layout.activity_entertainment_item,activity2.prepaerdDate(activity2.s));
                    activity2.listView.setAdapter(entertainmentAdapter);
                    activity2.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent2 = new Intent();
                            intent2.setClass(activity2.getApplicationContext(),
                                    ShopDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            ShopDemo shopDemo = activity2.s.get(position);
                            bundle.putSerializable("shopdemo",shopDemo);
                            intent2.putExtras(bundle);
                            activity2.getApplicationContext().startActivity(intent2);
                        }
                    });
                    //动态获取listview控件的高度
                    ViewGroup.LayoutParams params = activity2.listView.getLayoutParams();
                    ListAdapter listAdapter = activity2.listView.getAdapter();
                    View listitem = listAdapter.getView(0,null,activity2.listView);
                    listitem.measure(0,0);

                    params.height = listAdapter.getCount() * listitem.getMeasuredHeight();
                    activity2.listView.setLayoutParams(params);

            }

        }
    }
    public List<HomeShopList> prepaerdDate(ArrayList<ShopDemo> list){
        List<HomeShopList> shopLists = new ArrayList<>();
        File file = new File(this.getFilesDir().getAbsolutePath()+"/img");
        if (!file.exists()){
            file.mkdir();
        }
        for(int i =0;i < list.size();i++){
            HomeShopList shopList = new HomeShopList();
            shopList.setShopname(list.get(i).getShopdName());
            shopList.setShopimg(list.get(i).getShopimg());
            shopLists.add(shopList);
        }
        return shopLists;
    }
    //请求list中图片创建子线程
    class MyThread extends Thread {
        @Override
        public void run() {
            String str = "娱乐页list请求";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/demo001/shop/getEntertainshops.action");
            builder.post(body);
            Request request = builder.build();
            Call call = okHttpClient.newCall(request);
            try {
                //接受从服务器端返回的数据
                Response response = call.execute();
                String jshoplist = response.body().string();
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("slist",jshoplist);
                msg.setData(b);
                msg.what = 1;
                mHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mHandler.removeCallbacks(mThread);
        }
    }
}