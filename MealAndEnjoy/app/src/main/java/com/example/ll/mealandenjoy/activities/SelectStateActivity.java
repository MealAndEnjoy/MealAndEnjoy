package com.example.ll.mealandenjoy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ll.mealandenjoy.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entity.Shop;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;


public class SelectStateActivity extends AppCompatActivity {
    private Spinner spinner;
    private ImageButton btnRtn;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private Button btnSubmit;
    private Shop shop;
    private TextView tvShopState;
    //定义OkHttpClient对象
    private OkHttpClient okHttpClient;
    //自定义一个Handler类
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //处理消息，做相应的操作
            switch (msg.what) {
                //获取消息内容  msg.arg1   msg.arg2   msg.getData()
                case 0:
                    Toast.makeText(getApplicationContext(),"未更新成功，请稍候再试", Toast.LENGTH_SHORT).show();
                case 1:
                    Toast.makeText(getApplicationContext(),"修改成功", Toast.LENGTH_SHORT).show();
                    tvShopState.setText("您当前的状态是:"+shop.getState());
                    Bundle b=msg.getData();
                    Intent intent=new Intent();
                    intent.setClass(getApplicationContext(),ShopMainActivity.class);
                    intent.putExtras(b);
                    startActivity(intent);
                    break;
            }
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_state);
        spinner=(Spinner) findViewById(R.id.spinner);
        btnRtn=findViewById(R.id.imgbtn_rtn);
        btnSubmit=findViewById(R.id.btn_submit);
        tvShopState=findViewById(R.id.tv_shop_state);
        btnRtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //数据
        data_list=new ArrayList<String>();
        data_list.add("未营业");
        data_list.add("可取号");
        data_list.add("取消取号");
        //初始化OKHTTPClient对象
        okHttpClient = new OkHttpClient();
        //适配器
        arr_adapter=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,data_list);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //得到shop
        Intent intent=getIntent();
        final Bundle bundle=intent.getExtras();
        shop=(Shop)bundle.getSerializable("shop");
        tvShopState.setText("您当前的状态是:"+shop.getState());
        //为确定按钮设置点击事件监听器
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //得到选中的状态
                final String state=spinner.getSelectedItem().toString();
                //2. 创建Request请求对象
                // 构造向服务器提交的键值对数据信息
                new Thread() {
                    @Override
                    public void run() {
                        FormBody.Builder formBuilder = new FormBody.Builder();
                        //添加键值对数据
                        formBuilder.add("shopId",shop.getShopdId()+"" );
                        Log.i("yyh",shop.getShopdId()+"");
                        formBuilder.add("state",state);
                        FormBody formBody = formBuilder.build();
                        //创建Request请求对象
                        Request request = new Request.Builder()
                                .url("http://"+ip+":8080/demo001/shop/updatestate.action")
                                .post(formBody)
                                .build();
                        //3. 创建用于提交请求的Call对象
                        Call call = okHttpClient.newCall(request);
                        //4. 提交请求并获取服务器返回的响应信息（Response对象封装）
                        try {
                            Response response=call.execute();
                            String str = response.body().string();
                            Log.i("chenggong",str);
                            Message message=handler.obtainMessage();
                            if (str.equals("未更新成功，请稍候再试")){
                                message.what=0;
                            }else{
                                Gson gson=new Gson();
                                shop=gson.fromJson(str,Shop.class);
                                Bundle b=new Bundle();
                                b.putSerializable("shop",shop);
                                message.setData(b);
                                message.what=1;
                                //利用Handler对象发送消息
                                handler.sendMessage(message);
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        /*call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("yyh", "请求失败");
                                Log.i("yyyyy",e.toString());
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                        *//*Log.i("yyh", response.body().string());*//*
                                String str = response.body().string();
                                Message message=handler.obtainMessage();
                                if(str == "用户名或密码不能为空"){
                                    message.what=1;
                                    Log.i("hh","第一种情况");
                                }else if(str == "您输入的用户名或密码不正确"){
                                    message.what=2;
                                }
                                //利用Handler对象发送消息
                                handler.sendMessage(message);
                            }
                        }*/
                    }

                }.start();
            }
        });
    }
}
