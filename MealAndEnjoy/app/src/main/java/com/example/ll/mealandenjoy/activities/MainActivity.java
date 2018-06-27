package com.example.ll.mealandenjoy.activities;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.ll.mealandenjoy.R;

import fragment.BusinessCircleFragment;
import fragment.Home_Fragment;
import fragment.Mine_Fragment;

public class MainActivity extends AppCompatActivity {
    //布局页面中的tabhost标签
    private FragmentTabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tab);
        //1.布局文件中的FragmentTabHost控件对象
        tabHost = findViewById(android.R.id.tabhost);
        //2.初始化FragmentTabHost控件对象
        tabHost.setup(this,
                getSupportFragmentManager(),
                android.R.id.tabcontent);
        //3.添加子页面
        //添加主页面
        TabHost.TabSpec homeTag = tabHost.newTabSpec("homeTag")
                .setIndicator(getFragmentTab(R.drawable.selector_home,"主页"));//关联自定义的选项卡布局0

        tabHost.addTab(homeTag, Home_Fragment.class,null);
        //添加人气商圈布局页面
        TabHost.TabSpec tinyTaoTag = tabHost.newTabSpec("tinyTaoTag")
                .setIndicator(getFragmentTab(R.drawable.selector_busi,"人气商圈"));//关联自定义的选项卡布局
        tabHost.addTab(tinyTaoTag, BusinessCircleFragment.class,null);
        //添加我的页面
        TabHost.TabSpec newsTag = tabHost.newTabSpec("newsTag")
                .setIndicator(getFragmentTab(R.drawable.selector_mine,"我"));//关联自定义的选项卡布局
        tabHost.addTab(newsTag, Mine_Fragment.class,null);
        //设置默认显示的页面
        tabHost.setCurrentTab(0);
    }

    //得到自定义的选项卡View
    private View getFragmentTab(int resid, String text){
        //加载自定义的选项卡layout
        View view = getLayoutInflater().inflate(R.layout.fragment_item, null);
        //获取布局文件中的控件对象
        ImageView imageView  = view.findViewById(R.id.tab_image);
        TextView textView = view.findViewById(R.id.tab_text);
        //设置控件显示的内容，即设置选项卡显示的名称
        imageView.setImageResource(resid);
        textView.setText(text);
        //返回自定义的选项卡View
        return view;
    }

    /*@Override
    protected void onResume() {
        int id = getIntent().getIntExtra("个人界面", 7);

        if (id == 2 ) {
            tabHost.setCurrentTab(2);
            //3代表”我的京东“所在条目的位置，参考下面的源码即可理解
        }else {
            tabHost.setCurrentTab(0);

        }

        super.onResume();
    }*/
}
