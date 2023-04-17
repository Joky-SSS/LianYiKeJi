package com.lianyi.app.weight;

import android.content.Context;
import android.widget.RadioGroup;

import com.allen.library.SuperTextView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lianyi.app.R;
import com.lianyi.app.model.Constant;


/**
 * @ClassName: LabelTemplatePopup
 * @Description: 标签模板
 * @Author: Lee
 * @CreateDate: 2020/7/7 15:52
 */
public class LabelTemplatePopup extends BottomPopupView {
    RadioGroup mRadioGroup;
    SuperTextView svPopTitle;

    public LabelTemplatePopup(Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        svPopTitle = findViewById(R.id.sv_pop_title);
        mRadioGroup = findViewById(R.id.rg_goods_types);

        svPopTitle.setCenterString("标签模板选择");

        mRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_label_type_jxyq:
                    if (mPrintTypeListener != null) {
                        mPrintTypeListener.onSelectPrintType(Constant.JXYQ_TYPE);
                    }
                    dismiss();
                    break;
                case R.id.rb_label_type_jxyq2:
                    if (mPrintTypeListener != null) {
                        mPrintTypeListener.onSelectPrintType(Constant.JXYQ_TYPE2);
                    }
                    dismiss();
                    break;
                case R.id.rb_label_type_jxyq3:
                    if (mPrintTypeListener != null) {
                        mPrintTypeListener.onSelectPrintType(Constant.JXYQ_TYPE3);
                    }
                    dismiss();
                    break;
                case R.id.rb_label_type_xxdj_1:
                    if (mPrintTypeListener != null) {
                        mPrintTypeListener.onSelectPrintType(Constant.XXDJ_TYPE_1);
                    }
                    dismiss();

                    break;
                case R.id.rb_label_type_xxdj_2:
                    if (mPrintTypeListener != null) {
                        mPrintTypeListener.onSelectPrintType(Constant.XXDJ_TYPE_2);
                    }
                    dismiss();

                    break;

                default:
                    break;
            }
        });
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.label_template_layout;
    }

    onSelectPrintTypeListener mPrintTypeListener;

    public LabelTemplatePopup setPrintTypeListener(onSelectPrintTypeListener printTypeListener) {
        mPrintTypeListener = printTypeListener;
        return this;
    }

    public interface onSelectPrintTypeListener {
        void onSelectPrintType(int type);
    }
}
