package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;


import com.example.ll.mealandenjoy.R;

import java.util.List;

import adapters.CustomerShopCollectionAdapter;
import entity.Shop;

public class UserShopCollectionActivity extends AppCompatActivity {
    private ListView lvUserCollection;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shop_collection);
        //获取控件
        lvUserCollection=findViewById(R.id.lv_user_collection);
        //获取传过来的user对象
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        List<Shop> shopCollectionList=(List<Shop>) bundle.getSerializable("shopCollectionList");

        CustomerShopCollectionAdapter customerShopCollectionAdapter=new CustomerShopCollectionAdapter(getFilesDir().getAbsolutePath(),this,R.layout.main_list_item,shopCollectionList);
        lvUserCollection.setAdapter(customerShopCollectionAdapter);
    }
}