package com.lianyi.app.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.lianyi.app.R;
import com.lianyi.app.model.RoomTypeBean;

import java.util.ArrayList;
import java.util.List;

import cn.forward.androids.views.EasyAdapter;
import cn.forward.androids.views.STextView;

/**
  * @ClassName:      RoomTypeAdapter
  * @Description:
  * @Author:         Lee
  * @CreateDate:     2020/6/28 15:07
 */
public class RoomTypeAdapter extends EasyAdapter<RoomTypeAdapter.MyViewHolder> {
    List<RoomTypeBean> mData;
    Context context;

    public RoomTypeAdapter(Context context, @Nullable List<RoomTypeBean> data) {
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
        holder.textView.setText(mData.get(position).getName());
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

    public List<RoomTypeBean> getData() {
        return mData;
    }

    public void setData(List<RoomTypeBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}