package com.lianyi.app.adapter;

import androidx.appcompat.widget.AppCompatImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lianyi.app.R;
import com.lianyi.app.model.GoodsBean;
import com.lianyi.app.weight.AddSubController;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @ClassName: GoodsListAdapter
 * @Description: 标签打印适配器
 * @Author: Lee
 * @CreateDate: 2020/7/1 10:41
 */
public class LabelPrintAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
    public LabelPrintAdapter(@Nullable List<GoodsBean> data) {
        super(R.layout.item_label_print_layout, data);
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
        viewHolder.setText(R.id.tv_unit_price, String.format(getContext().getResources().getString(R.string.str_unit_price_and_number), goodsBean.getPrice(), goodsBean.getTargetQuantity()));
        viewHolder.setText(R.id.tv_save_location, String.format(getContext().getResources().getString(R.string.str_location), goodsBean.getLocDesc()));
        viewHolder.setText(R.id.tv_save_depository, String.format(getContext().getResources().getString(R.string.str_depository), goodsBean.getUserName()));
        viewHolder.setText(R.id.tv_goods_details, String.format(getContext().getResources().getString(R.string.str_goods_details), goodsBean.getCode(), goodsBean.getSpec(), goodsBean.getUom()));
        goodsBean.setPrintCount((int) Double.parseDouble(goodsBean.getTargetQuantity()));
        AddSubController addSubController = viewHolder.getView(R.id.add_sub_controller);
        addSubController.setPosition(viewHolder.getLayoutPosition())    // 传入当前位置，一定要传，不然数据会错乱
                .setBuyMin(1)
                .setCurrentNumber(goodsBean.getPrintCount()).
                setOnChangeValueListener((value, position) -> {
                    getData().get(position).setTargetQuantity(String.valueOf(value));
                });
    }

}
