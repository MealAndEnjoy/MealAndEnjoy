package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
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

import adapters.DeliciousFoodAdapter;
import adapters.MyPagerAdapter;
import entity.HomeShopList;
import entity.ShopDemo;
import map.map_activity;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;


public class DeliciousFoodActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnTouchListener{
    public static final int VIEW_PAGER_DELAY = 2000;
    private MyPagerAdapter mAdapter;
    private List<ImageView> mItems;
    private ImageView[] mBottomImages;
    private LinearLayout mBottomLiner;
    private ViewPager mViewPager;

    private int currentViewPagerItem;
    //是否自动播放
    private boolean isAutoPlay;

    private DeliciousFoodActivity.MyHandler mHandler;
    private Thread mThread;
    private Button map_model;
    private EditText editText;
    //主线程新建handler获得子线程服务器请求数据更新UI
    private ListView listView;
    private OkHttpClient ok;
    private ArrayList<ShopDemo> s;
    //获取布局使用
    private ImageButton btn_return;
    private ImageButton btn_1;
    private ImageButton btn_2;
    private ImageButton btn_3;
    private ImageButton btn_4;
    private ImageButton btn_5;
    private ImageButton btn_6;
    private ImageButton btn_7;
    private ImageButton btn_8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delicious_food);
        mHandler = new DeliciousFoodActivity.MyHandler(this);
        //配置轮播图ViewPager
        mViewPager = ((ViewPager) findViewById(R.id.live_view_pager));
        mItems = new ArrayList<>();
        mAdapter = new MyPagerAdapter(mItems, this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);
        isAutoPlay = true;
        //12345
        map_model = findViewById(R.id.map_moudle);
        map_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this, map_activity.class);
                startActivity(intent);
            }
        });
        //给按钮注册事件监听器
        editText = findViewById(R.id.search_et_input);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(false);
        editText.requestFocus();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
        //TODO: 添加ImageView
        addImageView();
        mAdapter.notifyDataSetChanged();
        //设置底部4个小点
        setBottomIndicator();
        //添加listview
        ok = new OkHttpClient();
        mThread = new Thread(new MyThread());
        listView = findViewById(R.id.lv_foodshops);
        mThread.start();
        //获取布局文件中控件对象
        btn_return = findViewById(R.id.btn_left);
        btn_1  = findViewById(R.id.btn_1);
        btn_2  = findViewById(R.id.btn_2);
        btn_3  = findViewById(R.id.btn_3);
        btn_4  = findViewById(R.id.btn_4);
        btn_5  = findViewById(R.id.btn_5);
        btn_6  = findViewById(R.id.btn_6);
        btn_7  = findViewById(R.id.btn_7);
        btn_8  = findViewById(R.id.btn_8);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this,FoodType1Activity.class);
                startActivity(intent);
            }
        });
        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this,FoodType2Activity.class);
                startActivity(intent);
            }
        });
        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this,FoodType3Activity.class);
                startActivity(intent);
            }
        });
        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this,FoodType4Activity.class);
                startActivity(intent);
            }
        });
        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this,FoodType5Activity.class);
                startActivity(intent);
            }
        });
        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this,FoodType6Activity.class);
                startActivity(intent);
            }
        });
        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this,FoodType7Activity.class);
                startActivity(intent);
            }
        });
        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeliciousFoodActivity.this,FoodType8Activity.class);
                startActivity(intent);
            }
        });
    }
    private void addImageView(){
        ImageView view1 = new ImageView(this);
        view1.setImageResource(R.mipmap.p2);
        ImageView view2 = new ImageView(this);
        view2.setImageResource(R.mipmap.p5);
        ImageView view3 = new ImageView(this);
        view3.setImageResource(R.mipmap.p6);
        ImageView view4= new ImageView(this);
        view4.setImageResource(R.mipmap.p7);
        ImageView view5 = new ImageView(this);
        view5.setImageResource(R.mipmap.p8);
        ImageView view6 = new ImageView(this);
        view6.setImageResource(R.mipmap.p9);

        view1.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view2.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view3.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view4.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view5.setScaleType(ImageView.ScaleType.CENTER_CROP);
        view6.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mItems.add(view1);
        mItems.add(view2);
        mItems.add(view3);
        mItems.add(view4);
        mItems.add(view5);
        mItems.add(view6);
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
        private WeakReference<DeliciousFoodActivity> mWeakReference;

        public MyHandler(DeliciousFoodActivity activity) {
            mWeakReference = new WeakReference<DeliciousFoodActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    DeliciousFoodActivity activity = mWeakReference.get();
                    if (activity.isAutoPlay) {

                        activity.mViewPager.setCurrentItem(++activity.currentViewPagerItem);
                    }
                    break;
                case 1:
                    final DeliciousFoodActivity activity1 = mWeakReference.get();
                    activity1.s = new ArrayList<>();
                    Bundle b = msg.getData();
                    Set<String> keySet = b.keySet();
                    Gson gson = new Gson();
                    String shoplist = b.getString("slist");
                    activity1.s = gson.fromJson(shoplist,new TypeToken<ArrayList<ShopDemo>>(){}.getType());

                    Log.i("ceshi",activity1.s.toString());
                    activity1.listView = activity1.findViewById(R.id.lv_foodshops);
                    DeliciousFoodAdapter deliciousFoodAdapter = new DeliciousFoodAdapter(activity1.getApplicationContext(),R.layout.activity_delicious_food_item,activity1.prepaerDate(activity1.s));
                    activity1.listView.setAdapter(deliciousFoodAdapter);
                    activity1.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent2 = new Intent();
                            intent2.setClass(activity1.getApplicationContext(),
                                    ShopDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            ShopDemo shopDemo = activity1.s.get(position);
                            bundle.putSerializable("shopdemo",shopDemo);
                            intent2.putExtras(bundle);
                            activity1.getApplicationContext().startActivity(intent2);
                        }
                    });
                    //动态获取listview控件的高度
                    ViewGroup.LayoutParams params = activity1.listView.getLayoutParams();
                    ListAdapter listAdapter = activity1.listView.getAdapter();
                    View listitem = listAdapter.getView(0,null,activity1.listView);
                    listitem.measure(0,0);

                    params.height = listAdapter.getCount() * listitem.getMeasuredHeight();
                    activity1.listView.setLayoutParams(params);
                    break;
            }

        }
    }

    //数据源
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
                mHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mHandler.removeCallbacks(mThread);
        }
    }

}
