package adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.ll.mealandenjoy.R;

import java.util.List;

import entity.Numberr;

/**
 * Created by lenovo on 2018/6/18.
 */

public class CustomqueueAdapter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //子项目的布局id
    private int mLayout;
    private List<Numberr> nrlist;
    public CustomqueueAdapter(Context mContext, int mLayout, List<Numberr> nrlist){
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.nrlist = nrlist;
    }

    /**
     *
     * @return 返回数据源的数量
     */
    @Override
    public int getCount(){
        return nrlist.size();
    }
    /**
     *
     * @param mposition 当前选择的item的id
     * @return 返回选择的item项的数据
     */
    @Override
    public Object getItem(int mposition){
        return nrlist.get(mposition);
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
        private TextView queue_number;
        private TextView queue_phone_number;
        private TextView queue_time;
        private TextView queue_state;
        private TextView queue_table_size;
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
        if(null == convertView){
            //1. 获取子项目item的布局文件
            //用于加载布局的LayoutInflater对象
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //使用inflate方法加载布局文件（参数1：布局文件的ID，参数2：ViewGroup）
            //2. 布局文件赋值给convertView参数
            convertView = mInflater.inflate(mLayout, null);
            viewHolder = new ViewHolder();
            viewHolder.queue_number = convertView.findViewById(R.id.queue_number);
            viewHolder.queue_phone_number = convertView.findViewById(R.id.queue_phone_number);
            viewHolder.queue_state = convertView.findViewById(R.id.queue_state);
            viewHolder.queue_time = convertView.findViewById(R.id.queue_time);
            viewHolder.queue_table_size = convertView.findViewById(R.id.queue_table_size);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        if(nrlist.get(mposition).getState().equals("已叫")){
            viewHolder.queue_number.setTextColor(Color.parseColor("#2ECC71"));
            viewHolder.queue_number.setText("  号    码   :  "+nrlist.get(mposition).getNumberrId()+"");
            viewHolder.queue_time.setTextColor(Color.parseColor("#2ECC71"));
            viewHolder.queue_time.setText("  时    间   :  "+nrlist.get(mposition).getDate().toString());
            viewHolder.queue_state.setTextColor(Color.parseColor("#2ECC71"));
            viewHolder.queue_state.setText("号码状态 :  "+nrlist.get(mposition).getState());
            viewHolder.queue_phone_number.setTextColor(Color.parseColor("#2ECC71"));
            viewHolder.queue_phone_number.setText(" 手 机 号  :  "+nrlist.get(mposition).getUserphone());
            viewHolder.queue_table_size.setTextColor(Color.parseColor("#2ECC71"));
            viewHolder.queue_table_size.setText("桌子类型 :  "+nrlist.get(mposition).getqName());
            return convertView;
        }else{
            viewHolder.queue_number.setTextColor(Color.parseColor("#F06292"));
            viewHolder.queue_number.setText("  号    码   :  "+nrlist.get(mposition).getNumberrId()+"");
            viewHolder.queue_time.setTextColor(Color.parseColor("#F06292"));
            viewHolder.queue_time.setText("  时    间   :  "+nrlist.get(mposition).getDate().toString());
            viewHolder.queue_state.setTextColor(Color.parseColor("#F06292"));
            viewHolder.queue_state.setText("号码状态 :  "+nrlist.get(mposition).getState());
            viewHolder.queue_phone_number.setTextColor(Color.parseColor("#F06292"));
            viewHolder.queue_phone_number.setText(" 手 机 号  :  "+nrlist.get(mposition).getUserphone());
            viewHolder.queue_table_size.setTextColor(Color.parseColor("#F06292"));
            viewHolder.queue_table_size.setText("桌子类型 :  "+nrlist.get(mposition).getqName());
            return convertView;
        }
    }
}
