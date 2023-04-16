package com.lianyi.app.adapter;

import android.text.TextUtils;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lianyi.app.R;
import com.lianyi.app.model.InventoryGoodBean;
import com.lianyi.app.model.InventoryRoomListBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InventoryGoodListAdapter extends BaseQuickAdapter<InventoryGoodBean, BaseViewHolder> {
    public InventoryGoodListAdapter(@Nullable List<InventoryGoodBean> data) {
        super(R.layout.item_inventory_goods, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, InventoryGoodBean goodBean) {
        baseViewHolder.setText(R.id.tv_serial_number,baseViewHolder.getLayoutPosition() + 1 + "");

        String name = goodBean.getName();
        if (name !=null && name.length()>10) name = name.substring(0,10)+"...";
        baseViewHolder.setText(R.id.tv_good_name, name);
        String spec = goodBean.getSpec();
        if (spec !=null && spec.length()>5) spec = spec.substring(0,5)+"...";
        baseViewHolder.setText(R.id.tv_goods_details, String.format(getContext().getResources().getString(R.string.str_goods_details), goodBean.getCode(), spec, goodBean.getUom()));
        String rfid = "";
        if (!TextUtils.isEmpty(goodBean.getRfidCode())) rfid = goodBean.getRfidCode();
        baseViewHolder.setText(R.id.tv_goods_rfid, String.format(getContext().getResources().getString(R.string.str_rfid), rfid));
        String userName = goodBean.getUserName();
        if (userName !=null && userName.length()>4) userName = userName.substring(0,4)+"...";
        baseViewHolder.setText(R.id.tv_good_user, String.format(getContext().getResources().getString(R.string.str_depository), userName));
        baseViewHolder.setText(R.id.tv_goods_price, String.format(getContext().getResources().getString(R.string.str_unit_price), goodBean.getPrice()));
        switch (goodBean.getBorderColor()) {
            case "GREEN":
                baseViewHolder.setVisible(R.id.tv_new_old_sign, true);
                baseViewHolder.setBackgroundResource(R.id.tv_new_old_sign, R.drawable.green_circle_bg);
                baseViewHolder.setText(R.id.tv_new_old_sign,"新");
                break;
            case "RED":
                baseViewHolder.setVisible(R.id.tv_new_old_sign, true);
                baseViewHolder.setBackgroundResource(R.id.tv_new_old_sign, R.drawable.red_circle_bg);
                baseViewHolder.setText(R.id.tv_new_old_sign,"亏");
                break;
            case "BLUE":
                baseViewHolder.setVisible(R.id.tv_new_old_sign, true);
                baseViewHolder.setBackgroundResource(R.id.tv_new_old_sign, R.drawable.blue_circle_bg);
                baseViewHolder.setText(R.id.tv_new_old_sign,"在");
                break;
            default:
                baseViewHolder.setVisible(R.id.tv_new_old_sign, false);
                break;
        }
    }
}
