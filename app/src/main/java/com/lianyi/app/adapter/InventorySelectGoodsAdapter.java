package com.lianyi.app.adapter;

import androidx.appcompat.widget.AppCompatTextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lianyi.app.R;
import com.lianyi.app.model.InventoryGoodBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.List;


public class InventorySelectGoodsAdapter extends BaseQuickAdapter<InventoryGoodBean, BaseViewHolder> {
    private DecimalFormat df = new DecimalFormat("#########");
    public InventorySelectGoodsAdapter(@Nullable List<InventoryGoodBean> data) {
        super(R.layout.item_inventory_select_goods, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, InventoryGoodBean goodsBean) {
        viewHolder.setText(R.id.tv_serial_number,viewHolder.getLayoutPosition() + 1 + "");
        viewHolder.setText(R.id.tv_good_name, goodsBean.getName());
        AppCompatTextView state = viewHolder.getView(R.id.tv_good_state);
        if (goodsBean.isSelected()) {
            state.setText("已选");
            state.setTextColor(getContext().getResources().getColor(R.color.green_dark));
        } else {
            state.setText("选入");
            state.setTextColor(getContext().getResources().getColor(R.color.main_blue));
        }
        String spec = goodsBean.getSpec();
        if (spec !=null && spec.length()>5) spec = spec.substring(0,5)+"...";
        viewHolder.setText(R.id.tv_goods_details, String.format(getContext().getResources().getString(R.string.str_goods_details), goodsBean.getCode(), spec, goodsBean.getUom()));
        Float price = null;
        try {
            price = Float.parseFloat(goodsBean.getPrice());
        } catch (NumberFormatException e) {
            price = 0F;
        }
        viewHolder.setText(R.id.tv_goods_price, String.format(getContext().getResources().getString(R.string.str_unit_price), df.format(price)));
    }
}
