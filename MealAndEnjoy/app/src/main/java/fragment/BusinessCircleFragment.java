package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;


import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.ll.mealandenjoy.R;
import com.example.ll.mealandenjoy.activities.BClistDetailActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import adapters.CustomBCAdapter;
import entity.BCList;
import entity.BusinessCircle;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;


public class BusinessCircleFragment extends Fragment {
    private ListView listView;
    private OkHttpClient ok;
    private Thread thread;
    private ArrayList<BusinessCircle> s;
    private LocationClient locationClient;
    private LatLng mlatLng;
    private static String result;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    s = new ArrayList<>();
                    Bundle b = msg.getData();
                    Set<String> keySet = b.keySet();
                    Gson gson = new Gson();
                    for(String key:keySet){
                        BusinessCircle bc = gson.fromJson((String)b.get(key),BusinessCircle.class);
                        s.add(bc);
                    }
                    listView = getActivity().findViewById(R.id.lv_businesscircle);
                    CustomBCAdapter customBCAdapter = new CustomBCAdapter(getActivity().getApplicationContext(),R.layout.businesscircle_item,preparedata(s));
                    listView.setAdapter(customBCAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("cName",s.get(position).getBusinesscirclename());
//                            intent.putExtra()
                            intent.putExtras(bundle);
                            intent.setClass(getActivity(), BClistDetailActivity.class);
                            getContext().startActivity(intent);
                        }
                    });
                    break;
            }
        }

    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //加载选项卡对应的选项页面
        View view = inflater.inflate(R.layout.businesscircle_page,container,false);
        ok = new OkHttpClient();
        thread = new Thread(new MyThread());
        listView = getActivity().findViewById(R.id.lv_businesscircle);

        //获取布局文件中控件对象
        //获取ListView控件
        ListView lvProducts = view.findViewById(R.id.lv_businesscircle);
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        SDKInitializer.initialize(getActivity().getApplicationContext());
        locationClient = new LocationClient(getActivity().getApplicationContext());
        locationClient.start();

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setScanSpan(5000);
        option.setIsNeedLocationPoiList(true);
        option.setCoorType("bd09ll");
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setNeedDeviceDirect(false);
        locationClient.setLocOption(option);
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                mlatLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
                thread.start();
            }
        });
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            String str = "1";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type, str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://" + ip + ":8080/MealAndEnjoyServer/BusinessCircle/bclist.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);
            try {
                Response response = call.execute();
                String jbclist = response.body().string();
                Gson gson = new Gson();
                List<BusinessCircle> bclist = gson.fromJson(jbclist,new TypeToken<List<BusinessCircle>>(){}.getType());
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                for(int i = 0;i<bclist.size();i++){
                    String n = String.valueOf(i);
                    b.putString(n,gson.toJson(bclist.get(i)));
                    Log.i("ceshi",i+""+bclist.get(i).getimgurl());
                }
                msg.setData(b);
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.removeCallbacks(thread);
        }
    }

    public List<BCList> preparedata(ArrayList<BusinessCircle> list){
        List<BCList> bcLists = new ArrayList<>();
        File dir = new File(getActivity().getFilesDir().getAbsolutePath()+"/img");
        if(!dir.exists()){
            dir.mkdir();
        }
        for(BusinessCircle item:list){
            BCList bc = new BCList();
            bc.setImgurl(item.getimgurl());
            String dis = searchRoute(mlatLng,new LatLng(item.getLat(),item.getLng()));
            bc.setDistence(dis);
            bcLists.add(bc);
        }
        return bcLists;
    }

    public String searchRoute(LatLng start,LatLng end){
        double distence = DistanceUtil.getDistance(start, end);
        String temp = "";
        if(distence < 1000){
            temp = distence+"米";
        }else{
            temp = String.format("%.2f",(distence)/1000)+"千米";
        }
        return temp;
    }
}
