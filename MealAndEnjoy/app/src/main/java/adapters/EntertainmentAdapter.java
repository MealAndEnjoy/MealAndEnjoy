package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ll.mealandenjoy.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import entity.HomeShopList;

import static fragment.Home_Fragment.ip;


/**
 * Created by Lenovo1 on 2018/5/31.
 */

public class EntertainmentAdapter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //子项目的布局ID
    private int mLayout;
    //数据源
    private List<HomeShopList> shopData;
    public static final String PIC_URL = "http://"+ip+":8080/MealAndEnjoyServer";
    private String path;

    public EntertainmentAdapter(Context context, int mLayout, List<HomeShopList> shopData){
        this.mContext = context;
        this.mLayout = mLayout;
        this.shopData = shopData;
        //手机存放图片文件夹的绝对路径
        this.path = context.getFilesDir().getAbsolutePath();
    }

    @Override
    public int getCount() {
        return shopData.size();
    }

    //mposition 当前选择item的id 返回选择item项的数据
    @Override
    public Object getItem(int position) {
        return shopData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        private TextView textView;
        private ImageView imageView;
        private TextView allNum;
        private TextView tv_shop_price ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if (null  == convertView){
            //1. 获取子项目item的布局文件
            //用于加载布局的LayoutInflater对象
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //使用inflate方法加载布局文件（参数1：布局文件的ID，参数2：ViewGroup）
            //2. 布局文件赋值给convertView参数
            convertView = mInflater.inflate(mLayout, null);
            //3. 获取布局文件中的控件对象
            viewHolder.textView = convertView.findViewById(R.id.tv_shop_name);
            viewHolder.imageView = convertView.findViewById(R.id.iv_shop);
            viewHolder.allNum = convertView.findViewById(R.id.tv_waitNum2);
            viewHolder.tv_shop_price = convertView.findViewById(R.id.tv_shop_price2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        File file = new File(path,shopData.get(position).getShopimg());
        //file对象的方法 !file.exists()返回false，不存在!file.exists()返回true
        if (!file.exists()){
               downloadpic(shopData.get(position).getShopimg(),PIC_URL+shopData.get(position).getShopimg());
        }else{
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            viewHolder.imageView.setImageBitmap(bitmap);
        }
        HomeShopList shopList = shopData.get(position);
        viewHolder.textView.setText(shopList.getShopname());
        viewHolder.tv_shop_price.setText(shopList.getAvgCost());
        viewHolder.allNum.setText(shopList.getAllNum());


        return convertView;
    }
    public void downloadpic(final String name, final String picur1){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fout = null;
                InputStream in = null;
                File file = new File(path,name);
                try {
                    fout = new FileOutputStream(file);
                    URL url = new URL(picur1);
                    in = url.openStream();

                    int len = -1;
                    byte[] b = new byte[1024];
                    while((len = in.read(b)) != -1){
                        fout.write(b,0,len);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                        try {
                            if (fout != null) {
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
    }
}
