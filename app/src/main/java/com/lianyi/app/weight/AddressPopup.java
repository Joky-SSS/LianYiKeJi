package com.lianyi.app.weight;

import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.lxj.xpopup.core.BottomPopupView;
import com.lianyi.app.R;

/**
 * @ClassName: CreateCabinetPopup
 * @Description: 新增柜子
 * @Author: Lee
 * @CreateDate: 2020/7/1 17:06
 */
public class AddressPopup extends BottomPopupView {
    AppCompatEditText etLocationAddres;
    AppCompatButton btCancel, btConfirm;
    String mString;

    public AddressPopup(Context context, String string) {
        super(context);
        this.mString = string;
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_address_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        etLocationAddres = findViewById(R.id.et_location_addres);
        btCancel = findViewById(R.id.bt_cancel);
        btConfirm = findViewById(R.id.bt_confirm);

        btCancel.setOnClickListener(v -> {
            dismiss();
        });

        btConfirm.setOnClickListener(v -> {
            if (mOnAddressListener != null) {
                mOnAddressListener.onAddress(etLocationAddres.getText().toString());
            }
            dismiss();
        });

    }

    onAddressListener mOnAddressListener;

    public AddressPopup setOnAddressListener(onAddressListener onAddressListener) {
        mOnAddressListener = onAddressListener;
        return this;
    }

    public interface onAddressListener {
        void onAddress(String address);
    }
}
