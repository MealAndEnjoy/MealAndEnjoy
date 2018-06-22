package com.example.lenovo.viewpagerdemo.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.lenovo.viewpagerdemo.R;
import com.example.lenovo.viewpagerdemo.adapters.CustomqueueAdapter;
import com.example.lenovo.viewpagerdemo.entity.Numberr;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.lenovo.viewpagerdemo.fragment.Home_Fragment.ip;

/**
 * Created by lenovo on 2018/6/15.
 */

public class QueueInfoActivity extends AppCompatActivity {
    private ListView listView;
    private Button btn_m;
    private Button btn_b;
    private Button btn_s;
    private Thread thread;
    private Thread bthread;
    private Thread mthread;
    private Thread sthread;
    private String b,m,s;
    private List<Numberr> nrlist;
    private OkHttpClient ok;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Bundle b = msg.getData();
                    String res = b.getString("queueinfo");
                    Gson gson = new Gson();
                    nrlist = new ArrayList<>();
                    nrlist = gson.fromJson(res,new TypeToken<List<Numberr>>(){}.getType());
                    CustomqueueAdapter customqueueAdapter = new CustomqueueAdapter(QueueInfoActivity.this,R.layout.queue_item,nrlist);
                    listView.setAdapter(customqueueAdapter);
                    break;
            }
            btn_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0;i < nrlist.size();i++){
                        if(nrlist.get(i).getqName().equals("大桌B") && nrlist.get(i).getState().equals("未叫")){
                            nrlist.get(i).setState("已叫");
                            b = String.valueOf(nrlist.get(i).getNumberrId());
                            break;
                        }
                    }
                    CustomqueueAdapter customqueueAdapter = new CustomqueueAdapter(QueueInfoActivity.this,R.layout.queue_item,nrlist);
                    bthread = new Thread(new BigThread());
                    bthread.start();
                    listView.setAdapter(customqueueAdapter);
                }
            });
            btn_m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0;i < nrlist.size();i++){
                        if(nrlist.get(i).getqName().equals("中桌M") && nrlist.get(i).getState().equals("未叫")){
                            nrlist.get(i).setState("已叫");
                            m = String.valueOf(nrlist.get(i).getNumberrId());
                            break;
                        }
                    }
                    CustomqueueAdapter customqueueAdapter = new CustomqueueAdapter(QueueInfoActivity.this,R.layout.queue_item,nrlist);
                    mthread = new Thread(new MediumThread());
                    mthread.start();
                    listView.setAdapter(customqueueAdapter);
                }
            });
            btn_s.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for(int i = 0;i < nrlist.size();i++){
                        if(nrlist.get(i).getqName().equals("小桌S") && nrlist.get(i).getState().equals("未叫")){
                            nrlist.get(i).setState("已叫");
                            s = String.valueOf(nrlist.get(i).getNumberrId());
                            break;
                        }
                    }
                    CustomqueueAdapter customqueueAdapter = new CustomqueueAdapter(QueueInfoActivity.this,R.layout.queue_item,nrlist);
                    sthread = new Thread(new SmallThread());
                    sthread.start();
                    listView.setAdapter(customqueueAdapter);
                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_queue);

        ok = new OkHttpClient();
        listView = findViewById(R.id.queue_list);
        btn_s = findViewById(R.id.small);
        btn_b = findViewById(R.id.big);
        btn_m = findViewById(R.id.medium);

        thread = new Thread(new MyThread());
        thread.start();

    }

    class MyThread extends Thread {
        @Override
        public void run(){
            String str = "1";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/demo001/numberr/getcurrentlist.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);

            try {
                Response response = call.execute();
                String nrlist = response.body().string();
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("queueinfo",nrlist);
                msg.setData(b);
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    class BigThread extends Thread{
        @Override
        public void run(){

            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,b);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/demo001/numberr/big.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);
            try {
                Response response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class SmallThread extends Thread{
        @Override
        public void run(){
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,s);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/demo001/numberr/big.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);
            try {
                Response response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class MediumThread extends Thread{
        @Override
        public void run(){
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,m);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/demo001/numberr/big.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);
            try {
                Response response = call.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
