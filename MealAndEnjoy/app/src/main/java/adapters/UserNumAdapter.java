package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;


import com.example.ll.mealandenjoy.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entity.NumDemo;
import entity.User;
import entity.UserApplication;
import okhttp3.OkHttpClient;

/**
 * Created by ll on 2018/6/20.
 */

public class UserNumAdapter extends BaseAdapter {
    //上下文环境
    private Context mContext;
    //子项目的布局id
    private int mLayout;
    private List<NumDemo> userNumData;
    //定义OKHTTPClient对象
    private OkHttpClient okHttpClient= new OkHttpClient();

    public UserNumAdapter(Context mContext, int mLayout, List<NumDemo> userNumData){
        this.mContext = mContext;
        this.mLayout = mLayout;
        this.userNumData = userNumData;
    }

    @Override
    public int getCount() {
        return userNumData.size();
    }

    @Override
    public Object getItem(int position) {
        return userNumData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(null == convertView) {
            //1.获取子项目item的布局文件
            // 用于加载布局的LayoutInflater对象
            LayoutInflater mInflater = LayoutInflater.from(mContext);
            //使用inflate方法加载布局文件（参数1：布局文件的ID，参数2：ViewGroup）
            //2.布局文件赋值给convertView参数
            convertView = mInflater.inflate(mLayout, null);
        }
        //3.获取布局文件中的控件对象
        TextView tvShopName=convertView.findViewById(R.id.tv_shop_name);
        TextView tvNowNum=convertView.findViewById(R.id.tv_now_num);
        TextView tvMynum=convertView.findViewById(R.id.tv_mynum);
        TextView tvNumTime=convertView.findViewById(R.id.tv_numtime);
        TextView tvNumState=convertView.findViewById(R.id.tv_num_state_content);
        Button btnCancel=convertView.findViewById(R.id.btn_cancel);
        TextView tvLapse=convertView.findViewById(R.id.tv_lapse);
        Button btnMove=convertView.findViewById(R.id.btn_move);
        TextView tvGetNum=convertView.findViewById(R.id.textView3);
        //4.利用传递的数据源给相应的控件赋值
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String numTime = format.format(date);
        UserApplication userApplication=(UserApplication) mContext.getApplicationContext();
        final User user=userApplication.getUser();
        if (numTime.equals( userNumData.get(position).getGetNumtime())){
            btnMove.setVisibility(View.VISIBLE);
//            tvLapse.setVisibility(View.GONE);
//            tvGetNum.setVisibility(View.VISIBLE);
//            btnCancel.setVisibility(View.VISIBLE);
            if(userNumData.get(position).getNowNum() == 0){
                tvNowNum.setText("无");
            }else{
                tvNowNum.setText(userNumData.get(position).getNowNum()+"");
            }
            tvShopName.setText(userNumData.get(position).getShopName());
            tvMynum.setText(userNumData.get(position).getMyNum()+"");
            tvNumTime.setText(userNumData.get(position).getGetNumtime());
            tvNumState.setText(userNumData.get(position).getNumState());


            /*btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int num=userNumData.get(position).getMyNum();
                    final int userId=user.getUserId();
                    new Thread() {
                        @Override
                        public void run() {
                            FormBody.Builder formBuilder = new FormBody.Builder();
                            //添加键值对数据
                            formBuilder.add("userId", userId + "");
                            formBuilder.add("num", num + "");
                            FormBody formBody = formBuilder.build();
                            //创建Request请求对象
                            Request request = new Request.Builder()
                                    .url("http://10.7.85.250:8080/demo001/deleteusernum.action")
                                    .post(formBody)
                                    .build();
                            //3. 创建用于提交请求的Call对象
                            Call call = okHttpClient.newCall(request);
                            //4. 提交请求并获取服务器返回的响应信息（Response对象封装）
                            try {
                                Response response = call.execute();
                                String str = response.body().string();
                                if(str.equals("未删除成功,请稍候再试~")){
                                    Toast.makeText(mContext,"未删除成功,请稍候再试~",Toast.LENGTH_SHORT);
                                }else{
                                    Gson gson = new Gson();
                                    Type UserNumList = new TypeToken<List<NumDemo>>() {
                                    }.getType();
                                    List<NumDemo> userNumList = gson.fromJson(str, UserNumList);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                    }.start();
                }
            });*/
        }else{
//            tvGetNum.setVisibility(View.GONE);
//            tvLapse.setVisibility(View.VISIBLE);
//            btnCancel.setVisibility(View.GONE);
            btnMove.setVisibility(View.VISIBLE);
            tvNowNum.setText("--");
            tvShopName.setText(userNumData.get(position).getShopName());
            tvMynum.setText(userNumData.get(position).getMyNum()+"");
            tvNumTime.setText(userNumData.get(position).getGetNumtime());
            tvNumState.setText("--");
        }
        //返回子项目布局视图
        return convertView;
    }
}
