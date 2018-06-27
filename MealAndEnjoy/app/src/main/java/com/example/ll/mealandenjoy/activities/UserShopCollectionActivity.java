package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.ll.mealandenjoy.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapters.CustomerShopCollectionAdapter;
import entity.HomeShopList;
import entity.Shop;
import entity.ShopDemo;
import entity.User;
import entity.UserApplication;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;

public class UserShopCollectionActivity extends AppCompatActivity {
    private ListView lvUserCollection;
    private OkHttpClient ok;
    private Thread thread ;
    private ImageButton imgbtn_rtn2;
    private CustomerShopCollectionAdapter.OnDeleteClickListener onDeleteClickListener ;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_shop_collection);
        //获取控件
        lvUserCollection=findViewById(R.id.lv_user_collection);
        imgbtn_rtn2 = findViewById(R.id.imgbtn_rtn2);
        //获取传过来的shop列表
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        ArrayList<ShopDemo> shopCollectionList=(ArrayList<ShopDemo>) bundle.getSerializable("shopCollectionList");
        imgbtn_rtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CustomerShopCollectionAdapter customerShopCollectionAdapter=new CustomerShopCollectionAdapter(getFilesDir().getAbsolutePath(),this,R.layout.shop_collection_item,prepaerDate(shopCollectionList));
        lvUserCollection.setAdapter(customerShopCollectionAdapter);
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

}
