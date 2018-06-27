package map;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.IpPrefix;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.ll.mealandenjoy.R;
import com.example.ll.mealandenjoy.activities.MainActivity;
import com.example.ll.mealandenjoy.activities.ShopDetailsActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import entity.ShopDemo;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static fragment.Home_Fragment.ip;

/**
 * Created by Lenovo1 on 2018/6/25.
 */

public class map_activity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String PIC_URL = "http://"+ip+":8080/MealAndEnjoyServer";
    private static final String TAG = PermissionUtils.class.getSimpleName();
    private ImageView mapimg;
    private TextView title;
    private ImageView imageView = null;
    private boolean firstLocation=true;
    private String permissionInfo;
    MapView mapView = null;
    private LocationClient locationClient;
    private BaiduMap mapControl;
    //private List<LatLng> latLngs;
    private OkHttpClient ok;
    private Thread thread;
    private Thread mthread;
    private Thread ethread;
    private ArrayList<ShopDemo> s;
    //private Bundle b = new Bundle();
    //private int n = 0;
    private Button btn_meal;
    private Button btn_entertainment;
    private Button btn_rtn;
    private Handler mHandler = new Handler(){
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
                        ShopDemo shopDemo = gson.fromJson((String)b.get(key),ShopDemo.class);
                        s.add(shopDemo);
                    }
                    getMarkerData(s,map_activity.this);
                    mapView.invalidate();
                    break;
                case 2:

                    Bundle b2 = msg.getData();
                    String meallist = b2.getString("meallist");
                    Log.i("ceshi1",meallist);
                    Gson gson1 = new Gson();
                    ArrayList<ShopDemo> meal = gson1.fromJson(meallist,new TypeToken<ArrayList<ShopDemo>>(){}.getType());
                    getMarkerData(meal,map_activity.this);
                    mapView.invalidate();
                    break;
                case 3:
                    Bundle b3 = msg.getData();
                    String Enlist = b3.getString("Enlist");
                    Gson gson2 = new Gson();
                    ArrayList<ShopDemo> Elist = gson2.fromJson(Enlist,new TypeToken<ArrayList<ShopDemo>>(){}.getType());
                    getMarkerData(Elist,map_activity.this);
                    mapView.invalidate();
                    break;
            }
        }
    };
    //需要动态获取权限
    private final int SDK_PERMISSION_REQUEST = 127;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //初始化context信息
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.map_model);
        mapView = (MapView)findViewById(R.id.bmapView);
        btn_meal = findViewById(R.id.meal);
        btn_entertainment = findViewById(R.id.entertainment);
        btn_rtn = findViewById(R.id.return_prior);
        ok = new OkHttpClient();
        File file = new File(getFilesDir().getAbsolutePath()+"/img");
        if(!file.exists()){
            file.mkdir();
        }

        LatLng latLng0 = new LatLng(38.049681,114.51368);
        //LatLng latLng1 = new LatLng(38.051875,114.51313);
        //LatLng latLng2 = new LatLng(38.040301,114.45750);
        thread = new Thread(new MyThread());
        thread.start();
        btn_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mthread = new Thread(new MealThread());
                mthread.start();
            }
        });
        btn_entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ethread = new Thread(new EntertainmentThread());
                ethread.start();
            }
        });
        btn_rtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imageView = findViewById(R.id.dingwei);
        //获取地图控制器类的对象
        mapControl = mapView.getMap();
        mapControl.setIndoorEnable(true);
        mapControl.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mapControl.hideInfoWindow();
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        //设置图层定位方式
        mapControl.setMyLocationEnabled(true);

        //setMaker(latLng1);
        //setMaker(latLng2);
        getPersimmions();
        //1. 初始化定位客户端
        locationClient = new LocationClient(getApplicationContext());
        //开启定位服务
        locationClient.start();



        //2. 设置定位客户端参数
        LocationClientOption option = new LocationClientOption();
        //给参数配置属性
        //打开GPS定位
        option.setOpenGps(true);
        //设置允许获取地址信息
        option.setIsNeedAddress(true);
        //设置循环获取定位点信息的时间间隔
        option.setScanSpan(1000);
        //设置允许获取定位点的周边POI数据
        option.setIsNeedLocationPoiList(true);
        //设置采用的经纬度坐标,采用百度坐标系
        option.setCoorType("bd09ll");
        //设置定位模式
        option.setLocationMode(
                LocationClientOption.LocationMode.Hight_Accuracy);
        option.setNeedDeviceDirect(false);
        //给定位客户端应用定位参数
        locationClient.setLocOption(option);

        //3. 给定位客户端注册定位监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                //获得维度数据
                double lat = bdLocation.getLatitude();
                //获得经度数据
                double lng = bdLocation.getLongitude();
                Log.i("zuobiao","lat:"+lat+",lng:"+lng);
                Log.i("error",":"+bdLocation.getLocType());
                //获取定位地址信息
                String address = bdLocation.getAddrStr();
                //Log.i("ceshi","地址:"+address);
                //设置定位显示方式
//                MyLocationConfiguration config = new MyLocationConfiguration(
//                        MyLocationConfiguration.LocationMode.NORMAL,
//                        true,
//                        BitmapDescriptorFactory.fromResource(R.mipmap.loc)
//                );
                //定位显示方式设置给地图控制器
//                mapControl.setMyLocationConfiguration(config);

                MyLocationData data = new MyLocationData.Builder()
                        .latitude(lat)
                        .longitude(lng)
                        .accuracy(bdLocation.getRadius())
                        .build();
                //设置定位数据
                mapControl.setMyLocationData(data);
                //setMaker(latLng0);
                LatLng latLng = new LatLng(lat,lng);

                MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                if(firstLocation){
                    mapControl.animateMapStatus(statusUpdate);
                    firstLocation = false;
                }

            }
        });

//        setMaker(latLng0);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        LayoutInflater i = LayoutInflater.from(this);
//        View view = i.inflate(R.layout.popwindow,null);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLocation = true;

            }
        });

    }

    public void getMarkerData(ArrayList<ShopDemo> list, final Context context){
        mapControl.clear();
        for(final ShopDemo shopDemo :list) {
            downloadpic(shopDemo.getShopimg(), PIC_URL + shopDemo.getShopimg());
            LatLng point = new LatLng(shopDemo.getLat(), shopDemo.getLng());
            //Log.i("ceshi",shopDemo.getLat()+"/"+shopDemo.getLng()+"/"+shopDemo.getShopdId());
            setMaker(point,shopDemo.getShopdName(),shopDemo.getShopimg());

//                Log.i("ceshi","下载完成");
//                LayoutInflater inflater = LayoutInflater.from(context);
//                Log.i("ceshiz", context.toString());
//                View view = inflater.inflate(R.layout.popwindow, null);
//                title = view.findViewById(R.id.maptitle);
//                title.setText(shopDemo.getShopdName());
//                String demo = getFilesDir().getAbsolutePath() + shopDemo.getShopimg();
//                File img = new File(getFilesDir().getAbsolutePath() + shopDemo.getShopimg());
//                Log.i("子线程getmaker", demo);
//                mapimg = view.findViewById(R.id.mapimg);
//                if (img.exists()) {
//                    Log.i("ceshi", "图片已经存在");
//                    Bitmap bitmap = BitmapFactory.decodeFile(getFilesDir().getAbsolutePath() + shopDemo.getShopimg());
//                    String demo1 = getFilesDir().getAbsolutePath() + shopDemo.getShopimg();
//                    Log.i("图片存放", demo1);
//                    mapimg.setImageBitmap(bitmap);
//                } else {
//                    Log.i("ceshi", "图片不存在");
//                    Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
//                    mapimg.setImageBitmap(bitmap);
//
//                }
//                InfoWindow.OnInfoWindowClickListener inwinCL = new InfoWindow.OnInfoWindowClickListener() {
//                    @Override
//                    public void onInfoWindowClick() {
//                        Toast.makeText(context, shopDemo.getShopdName(), Toast.LENGTH_SHORT).show();
//                    }
//                };
//                InfoWindow infoWindow = new InfoWindow(view, point, 0);
//
//                mapControl.showInfoWindow(infoWindow);


        }

    }

    /**
     * 设置Maker
     */
    private void setMaker(final LatLng latLng,final String name,final String img){
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.mapbiaoji);
        OverlayOptions options = new MarkerOptions()
                .position(latLng)
                .icon(bitmap);
        Overlay maker = mapControl.addOverlay(options);


        //点击监听器
        mapControl.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener(){
            @Override
            public boolean onMarkerClick(Marker marker){
                LatLng latLng1 = marker.getPosition();
                double lat = latLng1.latitude;
                double lng = latLng1.longitude;
                //Toast.makeText(MainActivity.this,"lat:"+lat+",lng:"+lng,Toast.LENGTH_SHORT).show();
                cinfo(latLng1);
//               LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
//               View view = inflater.inflate(R.layout.popwindow,null);
//               title = view.findViewById(R.id.maptitle);
//               title.setText(name);
//               Bitmap bm = BitmapFactory.decodeFile(MainActivity.this.getFilesDir().getAbsolutePath()+img);
//               mapimg = view.findViewById(R.id.mapimg);
//               mapimg.setImageBitmap(bm);
//               InfoWindow infoWindow = new InfoWindow(view,latLng,0);
//               mapControl.showInfoWindow(infoWindow);
                return false;
            }
        });
    }
    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
			/*
			 * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }

            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }
    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }

        }else{
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }
    @Override
    protected void onStop(){
        super.onStop();
        mapControl.setMyLocationEnabled(false);
        locationClient.stop();
    }
    @Override
    protected void onStart(){
        super.onStart();
        mapControl.setMyLocationEnabled(true);
        if(!locationClient.isStarted()){
            locationClient.start();
        }
    }

    class MyThread extends Thread {
        @Override
        public void run(){
            Log.i("ceshi","diaoyong");
            String str = "首页list请求";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/MealAndEnjoyServer/shop/homelist.action");
            //builder.url("http://172.16.12.228:8080/demo001/ShopDemo/homelist.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);

            try {
                Response response = call.execute();

                String jshoplist = response.body().string();
                Gson gson = new Gson();
                Log.i("ceshi",jshoplist);
                List<ShopDemo> shoplist = gson.fromJson(jshoplist,new TypeToken<List<ShopDemo>>(){}.getType());
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                for(int i = 0;i<shoplist.size();i++){
                    String n = String.valueOf(i);

                    b.putString(n,gson.toJson(shoplist.get(i)));

                }
                msg.setData(b);
                msg.what = 1;
                mHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mHandler.removeCallbacks(thread);
        }
    }
    class MealThread extends Thread{
        @Override
        public void run(){
            String str = "meallist请求";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/MealAndEnjoyServer/shop/getDelishops.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);
            try {
                Response response = call.execute();

                String meallist = response.body().string();
                Log.i("ceshi0",meallist);
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("meallist",meallist);
                msg.setData(b);
                msg.what = 2;
                mHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    class EntertainmentThread extends Thread{
        @Override
        public void run(){
            String str = "Enlist请求";
            MediaType type = MediaType.parse("text/plain;charset=UTF-8");
            RequestBody body = RequestBody.create(type,str);
            Request.Builder builder = new Request.Builder();
            builder.url("http://"+ip+":8080/MealAndEnjoyServer/shop/getEntertainshops.action");
            builder.post(body);
            Request request = builder.build();
            Call call = ok.newCall(request);
            try {
                Response response = call.execute();
                String Enlist = response.body().string();
                Message msg = Message.obtain();
                Bundle b = new Bundle();
                b.putString("Enlist",Enlist);
                msg.setData(b);
                msg.what = 3;
                mHandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void downloadpic(final String name,final String picurl){
        Boolean finish = false;
        File file = new File(getFilesDir().getAbsolutePath(),name);
        Log.i("图片下载",getFilesDir().getAbsolutePath()+name+picurl);
        if(!file.exists()){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    FileOutputStream fout = null;
                    InputStream in = null;
                    File file1 = new File(getFilesDir().getAbsolutePath(),name);
                    Log.i("图片是否存在",getFilesDir().getAbsolutePath()+name);
                    try {
                        fout = new FileOutputStream(file1);
                        URL url = new URL(picurl);
                        in = url.openStream();

                        int len = -1;
                        byte[] b = new byte[1024];
                        while ((len = in.read(b))!= -1){
                            fout.write(b,0,len);
                        }

                        Log.i("ceshi","图片正在下载");
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if(fout != null){
                                fout.close();
                            }
                            if (in != null){
                                in.close();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
            //Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(),R.mipmap.ic_launcher);
            //return bitmap;
        }

    }
    public void cinfo(LatLng latLng){
        Log.i("ceshi","diaoyong"+latLng.toString());
        for(final ShopDemo so:s){
            LatLng point = new LatLng(so.getLat(),so.getLng());
            Log.i("ceshi",point.toString());
            if(latLng.toString().equals(point.toString())){
                Log.i("ceshi","gouzao"+so.getShopimg()+so.getShopdName());
                final LayoutInflater inflater = LayoutInflater.from(map_activity.this);
                View view = inflater.inflate(R.layout.popwindow,null);
                title = view.findViewById(R.id.maptitle);
                title.setText(so.getShopdName());
                Bitmap bm = BitmapFactory.decodeFile(map_activity.this.getFilesDir().getAbsolutePath()+so.getShopimg());
                mapimg = view.findViewById(R.id.mapimg);
                mapimg.setImageBitmap(bm);
                mapimg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //商店跳转
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), ShopDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        ShopDemo shopDemo = so;
                        bundle.putSerializable("shopdemo",shopDemo);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
                InfoWindow infoWindow = new InfoWindow(view,latLng,-40);

                mapControl.showInfoWindow(infoWindow);
            }
        }
    }

}
