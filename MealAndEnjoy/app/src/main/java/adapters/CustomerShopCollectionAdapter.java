package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ll.mealandenjoy.R;

import java.util.List;

import entity.Shop;

import static fragment.Home_Fragment.ip;


/**
 * Created by ll on 2018/6/19.
 */

public class CustomerShopCollectionAdapter extends BaseAdapter {
    public static final String PIC_URL = "http://"+ip+":8080/demo001";
    public String path;
    //上下文环境
    private Context mContext;
    //子项目的布局id
    private int mLayout;
    private List<Shop> shopData;

    public CustomerShopCollectionAdapter(String path, Context mContext, int mLayout, List<Shop> shopData) {
        //getFilesDir()方法用于获取/data/data/<application package>/files目录
        this.path = mContext.getFilesDir().getAbsolutePath();
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.shopData = shopData;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i("ceshi",shopData.get(position).getShopimg());
        if(null == convertView) {
            //1.获取子项目item的布局文件
            // 用于加载布局的LayoutInflater对象
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //使用inflate方法加载布局文件（参数1：布局文件的ID，参数2：ViewGroup）
            //2.布局文件赋值给convertView参数
            convertView = mInflater.inflate(mLayout, null);
        }
        //3.获取布局文件中的控件对象
        //获取图片的图片按钮对象
        ImageView shopPic = convertView.findViewById(R.id.iv_shop);
        //获取商品名字的文本框对象
        TextView shopName = convertView.findViewById(R.id.tv_shop_name);
        //4.利用传递的数据源给相应的控件赋值
        Bitmap bm = BitmapFactory.decodeFile(path+shopData.get(position).getShopimg());
        shopPic.setImageBitmap(bm);
        shopName.setText(shopData.get(position).getShopdName());
        //返回子项目布局视图
        return convertView;
    }
}
