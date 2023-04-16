package com.lianyi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.lianyi.app.R;
import com.lianyi.app.model.SubjectBean;

import java.util.ArrayList;
import java.util.List;

import cn.forward.androids.views.EasyAdapter;
import cn.forward.androids.views.STextView;

public class InventorySubjectTagAdapter extends EasyAdapter<InventorySubjectTagAdapter.MyViewHolder> {
    List<SubjectBean> mData;
    Context context;

    public InventorySubjectTagAdapter(Context context, @Nullable List<SubjectBean> data) {
        super(context);
        this.context = context;
        this.mData = data;
    }


    @Override
    public MyViewHolder whenCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_subject_select, viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void whenBindViewHolder(MyViewHolder holder, int position) {
        //绑定
        holder.textView.setTag(position);
        holder.textView.setText(mData.get(position).getName());
        if (getSingleSelectedPosition() == position){
            holder.selectTag.setVisibility(View.VISIBLE);
            holder.textView.setTextColor(context.getResources().getColor(R.color.main_blue));
        }else{
            holder.selectTag.setVisibility(View.INVISIBLE);
            holder.textView.setTextColor(context.getResources().getColor(R.color.color_999999));
        }
    }

    @Override
    public int getItemCount() {
        return mData != null && !mData.isEmpty() ? mData.size() : new ArrayList<SubjectBean>().size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView textView;
        private View selectTag;

        MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_subject_name);
            selectTag = itemView.findViewById(R.id.tag_selected);
        }
    }

    public List<SubjectBean> getData() {
        return mData;
    }

    public void setData(List<SubjectBean> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}