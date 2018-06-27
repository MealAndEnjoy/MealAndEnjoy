package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ll.mealandenjoy.R;

import entity.Shop;


public class ShopMainActivity extends AppCompatActivity {
    private TextView tvShopname;
    private Button btnSelectState;
    private Button btnLineQueue;
    private Button btnBackMain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        //获取控件
        tvShopname=findViewById(R.id.tv_shopname);
        btnSelectState=findViewById(R.id.btn_select_state);
        btnLineQueue=findViewById(R.id.btn_line_queue);
        btnBackMain = findViewById(R.id.btn_back_main);
        //为返回客户端首页设置点击事件监听器
        btnBackMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent();
                intent1.setClass(getApplicationContext(),MainActivity.class);
                startActivity(intent1);
            }
        });
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        Shop shop;
        if(bundle != null){
            shop=(Shop) bundle.getSerializable("shop");
            tvShopname.setText("店铺名:"+shop.getShopdName());
        }
        btnSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent();
                i.putExtras(bundle);
                i.setClass(getApplicationContext(),SelectStateActivity.class);
                startActivity(i);
            }
        });
        btnLineQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent();
                intent3.putExtras(bundle);
                intent3.setClass(ShopMainActivity.this,QueueInfoActivity.class);
                startActivity(intent3);
            }
        });
    }
}
