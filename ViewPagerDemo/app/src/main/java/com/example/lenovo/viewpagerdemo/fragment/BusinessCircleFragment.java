package com.example.lenovo.viewpagerdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.lenovo.viewpagerdemo.R;
import com.example.lenovo.viewpagerdemo.adapters.ShowBussinessCircleAdapter;
import com.example.lenovo.viewpagerdemo.entity.BusinessCircle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张刘洋 on 2018/3/13.
 */

public class BusinessCircleFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //加载选项卡对应的选项页面
        View view = inflater.inflate(R.layout.businesscircle_page,container,false);
        //获取布局文件中控件对象
        //获取ListView控件
        ListView lvProducts = view.findViewById(R.id.lv_businesscircle);
        //实例化Adapter对象
        final ShowBussinessCircleAdapter adapter = new ShowBussinessCircleAdapter(
                getActivity(),
                R.layout.businesscircle_item,
                prepareDatas());
        //给ListView绑定适配器
        lvProducts.setAdapter(adapter);
        //给ListView注册事件监听器
        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
        //返回选项卡对应的选项页面
        return view;
    }

    private List<BusinessCircle> prepareDatas(){
        List<BusinessCircle> businessCircles = new ArrayList<BusinessCircle>();
        //第1个元素矿泉水矿泉水矿泉水矿泉水
        BusinessCircle businessCircle1 = new BusinessCircle("商圈1",(Integer)R.mipmap.ic_launcher,666);
        BusinessCircle businessCircle2 = new BusinessCircle("商圈1",(Integer)R.mipmap.ic_launcher_round,666);
        BusinessCircle businessCircle3 = new BusinessCircle("商圈1",(Integer)R.mipmap.ic_launcher,666);
        BusinessCircle businessCircle4 = new BusinessCircle("商圈1",(Integer)R.mipmap.ic_launcher_round,666);
        BusinessCircle businessCircle5 = new BusinessCircle("商圈1",(Integer)R.mipmap.ic_launcher,666);
        BusinessCircle businessCircle6 = new BusinessCircle("商圈1",(Integer)R.mipmap.ic_launcher_round,666);
        BusinessCircle businessCircle7 = new BusinessCircle("商圈1",(Integer)R.mipmap.ic_launcher,666);
        businessCircles.add(businessCircle1);
        businessCircles.add(businessCircle2);
        businessCircles.add(businessCircle3);
        businessCircles.add(businessCircle4);
        businessCircles.add(businessCircle5);
        businessCircles.add(businessCircle6);
        businessCircles.add(businessCircle7);

        return businessCircles;
    }
}
