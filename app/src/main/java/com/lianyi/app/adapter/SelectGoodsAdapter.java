package com.lianyi.app.adapter;

import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lianyi.app.R;
import com.lianyi.app.model.GoodsBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @ClassName: GoodsListAdapter
 * @Description: 物品列表适配器
 * @Author: Lee
 * @CreateDate: 2020/7/1 10:41
 */
public class SelectGoodsAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    private int type;

    public SelectGoodsAdapter(@Nullable List<GoodsBean> data) {
        super(R.layout.item_select_goods_layout, data);
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, GoodsBean goodsBean) {
        AppCompatImageView ivNewAngle = viewHolder.getView(R.id.iv_goods_select);
        if (goodsBean.isSelect()) {
            ivNewAngle.setBackgroundResource(R.drawable.radio_checked_shape);
        } else {
            ivNewAngle.setBackgroundResource(R.drawable.radio_uncheck_shape);

        }


        viewHolder.setText(R.id.tv_serial_number, viewHolder.getLayoutPosition() + 1 + "");
        viewHolder.setText(R.id.tv_goods_name, goodsBean.getName());
        if (type == 3) {
            viewHolder.setText(R.id.tv_unit_price, String.format(getContext().getResources().getString(R.string.str_unit_price_and_number), goodsBean.getRefPrice(), goodsBean.getTargetQuantity()));
        } else {
            viewHolder.setText(R.id.tv_unit_price, String.format(getContext().getResources().getString(R.string.str_unit_price_and_number), goodsBean.getPrice(), goodsBean.getTargetQuantity()));

        }
        viewHolder.setText(R.id.tv_save_location, String.format(getContext().getResources().getString(R.string.str_location), goodsBean.getLocDesc()));
        viewHolder.setText(R.id.tv_save_depository, String.format(getContext().getResources().getString(R.string.str_depository), goodsBean.getUserName()));
        viewHolder.setText(R.id.tv_goods_details, String.format(getContext().getResources().getString(R.string.str_goods_details), goodsBean.getCode(), goodsBean.getSpec(), goodsBean.getUom()));
    }
}
