package com.lianyi.app.weight;

import android.content.Context;
import android.text.TextUtils;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.lxj.xpopup.core.BottomPopupView;
import com.lianyi.app.R;
import com.lianyi.app.model.RoomListBean;

/**
  * @ClassName:      CreateCabinetPopup
  * @Description:     新增柜子
  * @Author:         Lee
  * @CreateDate:     2020/7/1 17:06
 */
public class CreateCabinetPopup extends BottomPopupView {
    AppCompatTextView tvRoomName;
    AppCompatEditText etCabinetName;
    AppCompatButton btCancel, btConfirm;
    RoomListBean.ListBean mListBean;

    public CreateCabinetPopup(Context context, RoomListBean.ListBean listBean) {
        super(context);
        this.mListBean = listBean;


    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_cabinet_layout;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tvRoomName = findViewById(R.id.tv_room_name);
        etCabinetName = findViewById(R.id.et_cabinet_name);
        btCancel = findViewById(R.id.bt_cancel);
        btConfirm = findViewById(R.id.bt_confirm);

        tvRoomName.setText(mListBean.getName());
        btCancel.setOnClickListener(v -> {
            dismiss();
        });

        btConfirm.setOnClickListener(v -> {
            if (mOnCreateCabinetListener!=null){
                mOnCreateCabinetListener.onCreateCabinet(TextUtils.isEmpty(etCabinetName.getText().toString())?"":etCabinetName.getText().toString());
            }
            dismiss();
        });

    }

    onCreateCabinetListener mOnCreateCabinetListener;

    public CreateCabinetPopup setOnCreateCabinetListener(onCreateCabinetListener onCreateCabinetListener) {
        mOnCreateCabinetListener = onCreateCabinetListener;
        return this;
    }

    public interface onCreateCabinetListener{
        void onCreateCabinet(String cabinetName);
    }
}
