package adapters;

import android.content.Context;
import android.graphics.Bitmap;


import com.example.ll.mealandenjoy.R;

import java.util.List;

import entity.Bean;
import entity.ShopDemo;
import entity.ViewHolder;

/**
 * Created by lenovo on 2018/6/20.
 */

public class SearchAdapter extends CommonAdapter<Bean> {

    public SearchAdapter(Context context, List<Bean> data, int layoutId){
        super(context,data,layoutId);
    }
    @Override
    public void convert(ViewHolder holder, int position){
        holder.setImageBitmap(R.id.item_search_iv_icon,mData.get(position).getBitmap())
                .setText(R.id.item_search_tv_title,mData.get(position).getTitle())
                .setText(R.id.item_search_tv_content,mData.get(position).getContent())
                .setText(R.id.item_search_tv_comments,mData.get(position).getComments());
    }

}
