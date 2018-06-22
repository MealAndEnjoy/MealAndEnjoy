package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.ll.mealandenjoy.R;

import java.util.List;

import entity.BusinessCircle;

/**
 * Created by 张刘洋 on 2018/3/10.
 */

public class ShowBussinessCircleAdapter extends BaseAdapter {

    //上下文环境
    private Context mContext;
    //子项目布局ID
    private int mLayout;
    //数据源
    private List<BusinessCircle> BusinessCircles;

    public ShowBussinessCircleAdapter(Context mContext, int mLayout, List<BusinessCircle> mProductsDatas) {
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.BusinessCircles = mProductsDatas;
    }

    @Override
    public int getCount() {
        return BusinessCircles.size();
    }

    @Override
    public Object getItem(int i) {
        return BusinessCircles.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        if(null == view) {
            //1.获取子项目item的布局文件
            //用于加载布局的LayoutInflater对象
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //使用inflate方法加载布局文件（参数1：布局文件的ID,参数2：ViewGroup）
            //2.布局文件赋值给view参数
            view = mInflater.inflate(mLayout, null);
        }
            //3.获取布局文件中的控件对象
                //获取商品图控件对象
            ImageView picture = view.findViewById(R.id.businesscircle_image);
                 //获取商品名的文本框控件对象
            TextView juli = view.findViewById(R.id.businesscircle_juli);
            //4.利用传递的数据给相应的控件对象赋值
             BusinessCircle businessCircle = BusinessCircles.get(i);
             picture.setImageResource((int)businessCircle.getImgurl());
            juli.setText(businessCircle.getJuli()+"");
        //5.返回子项目布局视图
        return view;
    }


}
