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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import entity.BCList;
import static fragment.Home_Fragment.ip;
/**
 * Created by lenovo on 2018/6/6.
 */

public class CustomBCAdapter extends BaseAdapter {
    private int[] jiashuju = {R.mipmap.circle01,R.mipmap.circle02,R.mipmap.circle03,R.mipmap.circle04};
    public static final String PIC_URL = "http://"+ip+":8080/MealAndEnjoyServer";
    public String path;
    //上下文环境
    private Context mContext;
    //子项目的布局id
    private int mLayout;
    private List<BCList> bclist;

    public CustomBCAdapter(Context context, int mLayout, List<BCList> bclist){
        this.mContext = context;
        this.mLayout = mLayout;
        this.bclist = bclist;
        this.path = context.getFilesDir().getAbsolutePath();
    }

    /**
     *
     * @return 返回数据源的数量
     */
    @Override
    public int getCount(){
        return bclist.size();
    }
    /**
     *
     * @param mposition 当前选择的item的id
     * @return 返回选择的item项的数据
     */
    @Override
    public Object getItem(int mposition){
        return bclist.get(mposition);
    }

    /**
     *
     * @param mposition
     * @return 当前选择了第几个item项
     */
    @Override
    public long getItemId(int mposition) {
        return mposition;
    }
    private class ViewHolder{
        private TextView textView;
        private ImageView imageView;
    }

    /**
     *
     * @param mposition
     * @param convertView
     * @param parent
     * @return 返回item项的布局视图组件
     */
    @Override
    public View getView(int mposition, View convertView, ViewGroup parent){
        ViewHolder viewHolder = null;
        if (null == convertView){
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //使用inflate方法加载布局文件（参数1：布局文件的ID，参数2：ViewGroup）
            //2. 布局文件赋值给convertView参数
            convertView = mInflater.inflate(mLayout, null);
            viewHolder = new ViewHolder();

            viewHolder.textView = convertView.findViewById(R.id.businesscircle_juli);
            viewHolder.imageView = convertView.findViewById(R.id.businesscircle_image);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        File file = new File(path,bclist.get(mposition).getImgurl());
//        if(!file.exists()){
//            downloadpic(bclist.get(mposition).getImgurl(),PIC_URL+bclist.get(mposition).getImgurl());
//        }else{
//            Log.i("ceshi",PIC_URL+bclist.get(mposition).getImgurl());
//            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            viewHolder.imageView.setImageBitmap(bitmap);
//        }
        viewHolder.imageView.setImageResource(jiashuju[mposition]);
        BCList bc = bclist.get(mposition);
        viewHolder.textView.setText(bc.getDistence());
        return convertView;
    }

    public void downloadpic(final String name,final String picurl){
        new Thread(new Runnable() {
            @Override
            public void run() {
                FileOutputStream fout = null;
                InputStream in = null;

                File file = new File(path,name);
                try {
                    in.reset();
                    fout = new FileOutputStream(file);
                    URL url = new URL(picurl);
                    in = url.openStream();

                    int len = -1;
                    byte[] b = new byte[1024];
                    while ((len = in.read(b)) != -1){
                        fout.write(b,0,len);
                    }
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
    }


}


