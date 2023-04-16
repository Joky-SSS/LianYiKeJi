package com.lianyi.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lianyi.app.R;
import com.lianyi.app.model.RoomListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RoomListAdapter extends BaseQuickAdapter<RoomListBean.ListBean, BaseViewHolder> {
    public RoomListAdapter(@Nullable List<RoomListBean.ListBean> data) {
        super(R.layout.item_room, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RoomListBean.ListBean listBean) {
        baseViewHolder.setText(R.id.tv_room_name,listBean.getName());
        baseViewHolder.setText(R.id.tv_goods_quantity,String.format(getContext().getResources().getString(R.string.str_room_count),listBean.getQuantity()));
        baseViewHolder.setText(R.id.tv_goods_amount,String.format(getContext().getResources().getString(R.string.str_room_amount),listBean.getAmount()));
    }
}
