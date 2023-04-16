package com.lianyi.app.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lianyi.app.R;
import com.lianyi.app.model.InventoryRoomListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InventoryRoomListAdapter extends BaseQuickAdapter<InventoryRoomListBean.RoomBean, BaseViewHolder> {
    public InventoryRoomListAdapter(@Nullable List<InventoryRoomListBean.RoomBean> data) {
        super(R.layout.item_inventory_room, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, InventoryRoomListBean.RoomBean listBean) {
        baseViewHolder.setText(R.id.tv_room_name,listBean.getName());
        baseViewHolder.setText(R.id.tv_goods_quantity,String.format(getContext().getResources().getString(R.string.str_room_count),listBean.getQuantity()));
        baseViewHolder.setText(R.id.tv_goods_amount,String.format(getContext().getResources().getString(R.string.str_room_amount_unit),listBean.getAmount()));
        baseViewHolder.setText(R.id.tv_profit_quantity,String.format(getContext().getResources().getString(R.string.str_profit_count),listBean.getGainQty()));
        baseViewHolder.setText(R.id.tv_profit_amount,String.format(getContext().getResources().getString(R.string.str_profit_amount_unit),listBean.getGainAmount()));
        baseViewHolder.setText(R.id.tv_deficit_quantity,String.format(getContext().getResources().getString(R.string.str_deficit_count),listBean.getLossQty()));
        baseViewHolder.setText(R.id.tv_deficit_amount,String.format(getContext().getResources().getString(R.string.str_deficit_amount_unit),listBean.getLossAmount()));
    }
}
