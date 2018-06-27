package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ll.mealandenjoy.R;
import com.example.ll.mealandenjoy.activities.UserShopCollectionActivity;

import org.w3c.dom.Text;

import java.util.List;

import entity.HomeShopList;
import entity.Shop;
import entity.ShopDemo;
import entity.User;
import entity.UserApplication;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static fragment.Home_Fragment.ip;


/**
 * Created by ll on 2018/6/19.
 */

public class CustomerShopCollectionAdapter extends BaseAdapter {
    public static final String PIC_URL = "http://"+ip+":8080/MealAndEnjoyServer";
    public String path;
    //上下文环境
    private Context mContext;
    //子项目的布局id
    private int mLayout;
    private List<HomeShopList> shopData;
    private float downX; // 点下时候获取的x坐标
    private float upX; // 手指离开时候的x坐标
    private Button button; // 用于执行删除的button
    private Animation animation; // 删除时候的动画
    private View view;
    private OkHttpClient ok;

    public CustomerShopCollectionAdapter(String path, Context mContext, int mLayout, List<HomeShopList> shopData) {
        //getFilesDir()方法用于获取/data/data/<application package>/files目录
        this.path = mContext.getFilesDir().getAbsolutePath();
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.shopData = shopData;
        animation = AnimationUtils.loadAnimation(mContext, R.anim.push_out); // 用xml获取一个动画
    }

    @Override
    public int getCount() {
        return shopData.size();
    }

    @Override
    public Object getItem(int position) {
        return shopData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    private class ViewHolder {
        //item中控件
        ImageView iv_shop;
        TextView tv_shop_name;
        ImageView iv_shop_star;
        TextView  tv_shop_price2;
        TextView tv_shop_type;
        TextView tv_shop_distance;
        ImageView iv_shop_line;
        TextView tv_waitNum2;

        Button bt ;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(null == convertView) {
            //1.获取子项目item的布局文件
            // 用于加载布局的LayoutInflater对象
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //使用inflate方法加载布局文件（参数1：布局文件的ID，参数2：ViewGroup）
            //2.布局文件赋值给convertView参数
            convertView = mInflater.inflate(mLayout, null);
            holder = new ViewHolder();
            holder.bt = convertView.findViewById(R.id.bt);
            holder.iv_shop = convertView.findViewById(R.id.iv_shop);
            holder.iv_shop_line = convertView.findViewById(R.id.iv_shop_line);
            holder.iv_shop_star = convertView.findViewById(R.id.iv_shop_star);
            holder.tv_shop_distance = convertView.findViewById(R.id.tv_shop_distance);
            holder.tv_shop_name = convertView.findViewById(R.id.tv_shop_name);
            holder.tv_shop_type = convertView.findViewById(R.id.tv_shop_type);
            holder.tv_waitNum2 = convertView.findViewById(R.id.tv_waitNum2);
            holder.tv_shop_price2 = convertView.findViewById(R.id.tv_shop_price2);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        convertView.setOnTouchListener(new View.OnTouchListener() { // 为每个item设置setOnTouchListener事件

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final ViewHolder holder = (ViewHolder) v.getTag(); // 获取滑动时候相应的ViewHolder，以便获取button按钮
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 手指按下
                        downX = event.getX(); // 获取手指x坐标
                        if (button != null) {
                            button.setVisibility(View.GONE); // 隐藏显示出来的button
                        }
                        break;
                    case MotionEvent.ACTION_UP: // 手指离开
                        upX = event.getX(); // 获取x坐标值
                        break;
                }

                if (holder.bt != null) {
                    if (Math.abs(downX - upX) > 80 && (upX < downX)) { //向左滑动，删除item
                        holder.bt.setVisibility(View.VISIBLE); // 显示删除button
                        button = holder.bt; // 赋值给全局button，一会儿用
                        view = v; // 得到itemview，在上面加动画
                        return true; // 终止事件
                    }

                    if(Math.abs(downX - upX) > 80 && (upX > downX)) {//撤销删除操作
                        if(holder.bt.getVisibility() == View.VISIBLE) {//此时Button可见
                            holder.bt.setVisibility(View.GONE);
                        }
                        return true; // 终止事件
                    }


                    return false; // 释放事件，使onitemClick可以执行
                }
                return false;
            }

        });

        holder.bt.setOnClickListener(new View.OnClickListener() { // 为button绑定事件

            @Override
            public void onClick(View v) {

                if (button != null) {
                    ok = new OkHttpClient();
                    button.setVisibility(View.GONE); // 点击删除按钮后，隐藏按钮
                    Thread thread = new Thread(){
                        @Override
                        public void run() {
                            UserApplication userApplication = (UserApplication) mContext.getApplicationContext();
                            User user = userApplication.getUser();
                            int shopId = shopData.get(position).getShopId();

                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            String shopid = String.valueOf(shopId);
                            String userid = String.valueOf(user.getUserId());
                            Log.i("ceshi1",userid);
                            formBuilder.add("shopId", shopid);
                            formBuilder.add("userId",userid);
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://"+ip+":8080/MealAndEnjoyServer/user/cancelcollectshop.action")
                                    .post(formBody)
                                    .build();
                            //3. 创建用于提交请求的Call对象
                            Call call = ok.newCall(request);
                        }
                    };
                   thread.start();
                    deleteItem(view, position); // 删除数据，加动画
                }

            }
        });
        //3.获取布局文件中的控件对象

        //获取商品名字的文本框对象
        TextView shopName = convertView.findViewById(R.id.tv_shop_name);

        //4.利用传递的数据源给相应的控件赋值
        Bitmap bm = BitmapFactory.decodeFile(path+shopData.get(position).getShopimg());
        HomeShopList shopList = shopData.get(position);
        holder.iv_shop.setImageBitmap(bm);
        holder.tv_shop_name.setText(shopList.getShopname());
        holder.tv_shop_price2.setText(shopList.getAvgCost());
        holder.tv_waitNum2.setText(shopList.getAllNum());

        //返回子项目布局视图
        return convertView;
    }

    public void deleteItem(View view, final int position) {
        view.startAnimation(animation); // 给view设置动画
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) { // 动画执行完毕
                shopData.remove(position); // 把数据源里面相应数据删除
                notifyDataSetChanged();

            }
        });

    }



    //定义一个接口类用于实现和activity之间的交互
    public interface OnDeleteClickListener {
        //通知activtiy更新数据
        void onDeleteData();
    }
}
