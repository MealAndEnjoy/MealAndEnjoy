package fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.ll.mealandenjoy.R;
import com.example.ll.mealandenjoy.activities.DeliciousFoodActivity;
import com.example.ll.mealandenjoy.activities.EntertainmentActivity;
import com.example.ll.mealandenjoy.activities.HomeActivity;
import com.example.ll.mealandenjoy.activities.SearchActivity;
import com.example.ll.mealandenjoy.activities.ShopDetailsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import adapters.CustomHomeAdapter;
import adapters.MyPagerAdapter;
import entity.BCList;
import entity.HomeShopList;
import entity.ShopDemo;
import map.map_activity;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lenovo on 2018/5/23.
 */



public class Home_Fragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnTouchListener {
    public static String ip = "10.7.85.230";

    public static final int VIEW_PAGER_DELAY = 2000;
    private MyPagerAdapter mAdapter;
    private List<ImageView> mItems;
    private ImageView[] mBottomImages;
    private LinearLayout mBottomLiner;
    private ViewPager mViewPager;
    private EditText editText;
    private Button map_model;
    private int currentViewPagerItem;
    //是否自动播放
    private boolean isAutoPlay;
//    private View view = getActivity().findViewById(R.id.linear1);

    private MyHandler mHandler;
    private Thread mThread;
    //-----以上为轮播图所用------------
    private ImageButton imgbtn_food;
    private ImageButton imgbtn_entertain;

    //主线程新建handler获得子线程服务器请求数据更新UI
    private int[] a = {R.drawable.demo01,R.drawable.demo02,R.drawable.demo03,R.drawable.demo04,R.drawable.demo05};
    private ListView listView;
    private OkHttpClient ok;
    private Thread thread;
    private ArrayList<ShopDemo> s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //加载选项卡对应的选项页面
        View view = inflater.inflate(R.layout.activity_home,container,false);
        //添加listview
        ok = new OkHttpClient();
        thread = new Thread(new MyThread());
        listView = getActivity().findViewById(R.id.lv_shops);
        thread.start();

        //返回选项卡对应的选项页面
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        //获取控件
        map_model = getActivity().findViewById(R.id.map_moudle);
        map_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), map_activity.class);
                startActivity(intent);
            }
        });
        imgbtn_food = getActivity().findViewById(R.id.imgbtn_food);
        imgbtn_entertain = getActivity().findViewById(R.id.imgbtn_entertain);
        //给按钮注册事件监听器
        editText = getActivity().findViewById(R.id.search_et_input);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(false);
        editText.requestFocus();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

//        editText.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                editText.clearFocus();
//                Intent intent = new Intent();
//                intent.setClass(getContext(), SearchActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });
        ButtonClickListener listener = new ButtonClickListener();
        imgbtn_food.setOnClickListener(listener);
        imgbtn_entertain.setOnClickListener(listener);

        mHandler = new MyHandler(this);
        //配置轮播图ViewPager
        mViewPager = ((ViewPager) getActivity().findViewById(R.id.live_view_pager));
        mItems = new ArrayList<>();
        mAdapter = new MyPagerAdapter(mItems, getActivity());
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOnTouchListener(this);
        mViewPager.addOnPageChangeListener(this);
        isAutoPlay = true;

        //TODO: 添加ImageView
        addImageView();
        mAdapter.notifyDataSetChanged();
        //设置底部4个小点
        setBottomIndicator();
//阿上
    }

    private void addImageView(){
        ImageView view1 = new ImageView(getContext());
        view1.setImageResource(R.mipmap.p2);
        ImageView view2 = new ImageView(getContext());
        view2.setImageResource(R.mipmap.p5);
        ImageView view3 = new ImageView(getContext());
        view3.setImageResource(R.mipmap.p6);
        ImageView view4= new ImageView(getContext());
        view4.setImageResource(R.mipmap.p7);
        ImageView view5 = new ImageView(getContext());
        view5.setImageResource(R.mipmap.p8);
        ImageView view6 = new ImageView(getContext());
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
        mBottomLiner = (LinearLayout) getActivity().findViewById(R.id.live_indicator);
        //右下方小圆点
        mBottomImages = new ImageView[mItems.size()];
        for (int i = 0; i < mBottomImages.length; i++) {
            ImageView imageView = new ImageView(getContext());
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
        private WeakReference<Home_Fragment> mWeakReference;

        public MyHandler(Home_Fragment activity) {
            mWeakReference = new WeakReference<Home_Fragment>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Home_Fragment activity = mWeakReference.get();
                    if (activity.isAutoPlay) {

                        activity.mViewPager.setCurrentItem(++activity.currentViewPagerItem);
                    }

                    break;
                case 1:
                    final Home_Fragment activity1 = mWeakReference.get();
                    Log.i("ceshi","success");
                    activity1.s = new ArrayList<>();
                    Bundle b = msg.getData();
                    Gson gson = new Gson();
                    String shoplist = b.getString("slist");
                    activity1.s = gson.fromJson(shoplist,new TypeToken<ArrayList<ShopDemo>>(){}.getType());

                    Log.i("ceshi",activity1.s.toString());
                    activity1.listView = activity1.getActivity().findViewById(R.id.lv_shops);
                    CustomHomeAdapter customHomeAdapter = new CustomHomeAdapter(activity1.getContext(),R.layout.main_list_item,activity1.prepaerDate(activity1.s));

                    activity1.listView.setAdapter(customHomeAdapter);
                    activity1.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent2 = new Intent();
                            intent2.setClass(activity1.getContext(),
                                    ShopDetailsActivity.class);
                            Bundle bundle = new Bundle();
                            ShopDemo shopDemo = activity1.s.get(position);
                            bundle.putSerializable("shopdemo",shopDemo);
                            intent2.putExtras(bundle);
                           activity1.getContext().startActivity(intent2);
                        }
                    });
                    //动态获取listview高度
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
    class ButtonClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()){
                case R.id.imgbtn_food:
                    //页面跳转
                    //1. 创建Intent对象
                    //2. 指定跳转路线
                    intent.setClass(getContext(),
                            DeliciousFoodActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;
                case R.id.imgbtn_entertain:
                    //页面跳转
                    //1. 创建Intent对象
                    //2. 指定跳转路线
                    intent.setClass(getContext(),
                            EntertainmentActivity.class);
                    //3. 进行跳转
                    startActivity(intent);
                    break;

            }
        }
    }

    public List<HomeShopList> prepaerDate(ArrayList<ShopDemo> list){
        List<HomeShopList> shopLists = new ArrayList<>();
        File dir = new File(getActivity().getFilesDir().getAbsolutePath()+"/img");
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
            String str = "首页list请求";
            Log.i("ceshi",str);
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/MealAndEnjoyServer/shop/homelist.action");
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
            mHandler.removeCallbacks(thread);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        editText = getActivity().findViewById(R.id.search_et_input);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(false);
        editText.requestFocus();
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }
}
