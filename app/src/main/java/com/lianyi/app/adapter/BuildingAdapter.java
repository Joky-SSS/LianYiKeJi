package com.lianyi.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lianyi.app.R;
import com.lianyi.app.model.BuildingBean;
import com.lianyi.app.model.RoomTypeBean;

import java.util.ArrayList;
import java.util.List;

import cn.forward.androids.views.EasyAdapter;
import cn.forward.androids.views.STextView;

/**
  * @ClassName:      BuildingAdapter
  * @Description:
  * @Author:         Lee
  * @CreateDate:     2020/6/28 15:07
 */
public class BuildingAdapter extends EasyAdapter<BuildingAdapter.MyViewHolder> {
    List<BuildingBean> mData;
    Context context;

    public BuildingAdapter(Context context, @Nullable List<BuildingBean> data) {
        super(context);
        this.context = context;
        this.mData = data;
    }


    @Override
    public MyViewHolder whenCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_room_tag, null);
        return new MyViewHolder(view);
    }

    @Override
    public void whenBindViewHolder(MyViewHolder holder, int position) {
        //绑定
        holder.textView.setTag(position);
        holder.textView.setText(mData.get(position).getBuildingName());
    }

    @Override
    public int getItemCount() {
        return mData != null && !mData.isEmpty() ? mData.size() : new ArrayList<RoomTypeBean>().size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private STextView textView;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.rv_item_tag);
        }
    }

    public List<BuildingBean> getData() {
        return mData;
    }

    public void setData(List<BuildingBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}