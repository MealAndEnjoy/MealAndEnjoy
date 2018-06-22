package com.example.lenovo.viewpagerdemo.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lenovo.viewpagerdemo.R;
import com.example.lenovo.viewpagerdemo.entity.Shop;

public class ShopMainActivity extends AppCompatActivity {
    private TextView tvShopname;
    private Button btnSelectState;
    private Button btnLineQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_main);
        //获取控件
        tvShopname=findViewById(R.id.tv_shopname);
        btnSelectState=findViewById(R.id.btn_select_state);
        btnLineQueue=findViewById(R.id.btn_line_queue);
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
    }
}
