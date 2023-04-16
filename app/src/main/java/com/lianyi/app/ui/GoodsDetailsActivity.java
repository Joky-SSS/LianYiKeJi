package com.lianyi.app.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.widget.NestedScrollView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.allen.library.SuperTextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lianyi.app.utils.DoubleClicks;
import com.lxj.xpopup.XPopup;
import com.lianyi.app.R;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.CabinetBean;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.GoodsBean;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.RoomListBean;
import com.lianyi.app.model.SubjectBean;
import com.lianyi.app.model.SysDictBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.model.UsageBean;
import com.lianyi.app.otto.BusData;
import com.lianyi.app.otto.OTTOTags;
import com.lianyi.app.otto.OttoBus;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.weight.AddressPopup;
import com.lianyi.app.weight.DecimalEditTextView;
import com.rxjava.rxlife.RxLife;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_BUTTON;

/**
 * @ClassName: GoodsDetailsActivity
 * @Description:
 * @Author: Lee
 * @CreateDate: 2020/7/1 17:51
 */
@Route(path = RoutePath.PATH_GOOD_DETAILS)
public class GoodsDetailsActivity extends BaseActvity implements CommonTitleBar.OnTitleBarListener, SuperTextView.OnSuperTextViewClickListener {
    @Autowired(name = Constant.GOODS_ID)
    String goodsId;
    @Autowired(name = Constant.ROOM_BEAN)
    RoomListBean.ListBean mListBean;
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;
    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.sv_goods_serial_number)
    SuperTextView svGoodsSerialNumber;
    @BindView(R.id.sv_goods_name)
    SuperTextView svGoodsName;
    @BindView(R.id.sv_goods_specifications)
    SuperTextView svGoodsSpecifications;
    @BindView(R.id.sv_goods_unit)
    SuperTextView svGoodsUnit;
    @BindView(R.id.et_goods_number)
    AppCompatEditText etGoodsNumber;
    @BindView(R.id.et_goods_unit_price)
    DecimalEditTextView etGoodsUnitPrice;
    @BindView(R.id.sv_goods_subject)
    SuperTextView svGoodsSubject;
    @BindView(R.id.sv_goods_use)
    SuperTextView svGoodsUse;
    @BindView(R.id.sv_goods_save_address)
    SuperTextView svGoodsSaveAddress;
    @BindView(R.id.et_goods_depository)
    AppCompatEditText etGoodsDepository;
    @BindView(R.id.sv_goods_date)
    SuperTextView svGoodsDate;
    @BindView(R.id.et_goods_exp)
    AppCompatEditText etGoodsExp;
    @BindView(R.id.et_goods_mfg)
    AppCompatEditText etGoodsMfg;
    @BindView(R.id.et_goods_brand)
    AppCompatEditText etGoodsBrand;
    @BindView(R.id.et_goods_supplier)
    AppCompatEditText etGoodsSupplier;
    @BindView(R.id.et_goods_contact)
    AppCompatEditText etGoodsContact;
    @BindView(R.id.et_goods_contact_phone)
    AppCompatEditText etGoodsContactPhone;
    @BindView(R.id.sv_goods_attribute)
    SuperTextView svGoodsAttribute;
    @BindView(R.id.bt_save)
    AppCompatButton btSave;
    @BindView(R.id.bt_continue)
    AppCompatButton btContinue;
    @BindView(R.id.bt_label_print)
    AppCompatButton btLabelPrint;
    @BindView(R.id.nested_scrollview)
    NestedScrollView nestedScrollview;

    //科目，房间，柜子 ,用途 ，属性
    private String subjectId = "", buildingId = "", cabinetId = "", usageId = "", attributeId = "";

    TimePickerView mTimePickerView;
    GoodsBean mGoodsBean;

    @Override
    public int getLayoutId() {
        return R.layout.goods_details_layout;
    }

    @Override
    public void initView() {
        titleBar.setListener(this);
        svGoodsSubject.setOnSuperTextViewClickListener(this);
        svGoodsSaveAddress.setOnSuperTextViewClickListener(this);
        svGoodsAttribute.setOnSuperTextViewClickListener(this);
        svGoodsUse.setOnSuperTextViewClickListener(this);
        svGoodsSaveAddress.setRightImageViewClickListener(imageView -> {
            svGoodsSaveAddress.setRightIcon(R.drawable.icon_right_back);
            cabinetId = "";
            svGoodsSaveAddress.setRightString("");
        });


        nestedScrollview.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            hideKeyboard(nestedScrollview);
        });
    }

    @Override
    public void initData() {
        RxHttp.get(ApiService.API_GOODS_DETAILS)
                .add("assetId", goodsId)
                .asResponse(GoodsBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(goodsBean -> {
                    initGoods(goodsBean);
                }, throwable -> {

                });

    }

    @Override
    public boolean isRegisterOttO() {
        return false;
    }

    /**
     * 设置物品数据
     *
     * @param goodsBean
     */
    private void initGoods(GoodsBean goodsBean) {
        svGoodsSerialNumber.setRightString(goodsBean.getCode());
        svGoodsName.setRightString(goodsBean.getName());
        svGoodsSpecifications.setRightString(goodsBean.getSpec());
        svGoodsUnit.setRightString(goodsBean.getUom());

        if (!TextUtils.isEmpty(goodsBean.getTargetQuantity())) {
            if (goodsBean.getTargetQuantity().equals("0.00")) {
                etGoodsNumber.setText("");
            } else {
                etGoodsNumber.setText(goodsBean.getTargetQuantity());
            }
        }
        if (!TextUtils.isEmpty(goodsBean.getPrice())) {
            if (goodsBean.getPrice().equals("0.00")) {
                etGoodsUnitPrice.setText("");
            } else {
                etGoodsUnitPrice.setText(goodsBean.getPrice());
            }
        }
        svGoodsSubject.setRightString(goodsBean.getSubjectName());
        svGoodsUse.setRightString(goodsBean.getUsageName());
        svGoodsSaveAddress.setRightString(goodsBean.getLocDesc());
        etGoodsDepository.setText(goodsBean.getUserName());
        svGoodsDate.setRightString(goodsBean.getPurchaseDate());
        etGoodsExp.setText(goodsBean.getWarrantyTime());
        etGoodsMfg.setText(goodsBean.getUsefulLife());
        etGoodsBrand.setText(goodsBean.getBrand());
        etGoodsSupplier.setText(goodsBean.getVendorName());
        etGoodsContact.setText(goodsBean.getVendorContact());
        etGoodsContactPhone.setText(goodsBean.getVendorPhonenum());
        svGoodsAttribute.setRightString(goodsBean.getAssetAttrName());

        subjectId = goodsBean.getSubjectId();
        cabinetId = goodsBean.getContainerId();
        buildingId = goodsBean.getBuildingId();
        usageId = goodsBean.getUsageId();
        attributeId = goodsBean.getAssetAttr();

        mGoodsBean = goodsBean;
    }

    @OnClick({R.id.bt_save, R.id.bt_label_print, R.id.sv_goods_date})
    public void onViewClicked(View view) {
        if (DoubleClicks.isDoubleClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_save:
                if (mGoodsBean != null) {
                    updateGoods();
                }
                break;
            case R.id.bt_label_print:
                ARouter.getInstance().build(RoutePath.PATH_LABEL_PRINT)
                        .withParcelable(Constant.ROOM_BEAN, mListBean)
                        .withParcelable(Constant.TASK, mTaskBean)
                        .navigation();
                break;
            case R.id.sv_goods_date:
                //时间选择器
                initTimePicker();
                break;
            default:
                break;
        }
    }


    /**
     * 更新数据
     */
    private void updateGoods() {
        mGoodsBean.setCode(svGoodsSerialNumber.getRightString());
        mGoodsBean.setContainerId(cabinetId);
        mGoodsBean.setName(svGoodsName.getRightString());
        mGoodsBean.setSpec(svGoodsSpecifications.getRightString());
        mGoodsBean.setUom(svGoodsUnit.getRightString());
        mGoodsBean.setTargetQuantity(etGoodsNumber.getText().toString());
        mGoodsBean.setPrice(etGoodsUnitPrice.getText().toString());
        mGoodsBean.setSubjectId(subjectId);
        mGoodsBean.setSubjectName(svGoodsSubject.getRightString());
        mGoodsBean.setUsageId(usageId);
        mGoodsBean.setTargetAmount(etGoodsUnitPrice.getText().toString());
        mGoodsBean.setUsageName(svGoodsUse.getRightString());
        mGoodsBean.setLocDesc((TextUtils.isEmpty(svGoodsSaveAddress.getRightString()) ? "" : svGoodsSaveAddress.getRightString()));
        mGoodsBean.setUserName(etGoodsDepository.getText().toString());
        mGoodsBean.setPurchaseDate(svGoodsDate.getRightString());
        mGoodsBean.setWarrantyTime(etGoodsExp.getText().toString());
        mGoodsBean.setUsefulLife(etGoodsMfg.getText().toString());
        mGoodsBean.setBrand(etGoodsBrand.getText().toString());
        mGoodsBean.setVendorName(etGoodsSupplier.getText().toString());
        mGoodsBean.setVendorContact(etGoodsContact.getText().toString());
        mGoodsBean.setVendorPhonenum(etGoodsContactPhone.getText().toString());
        mGoodsBean.setAssetAttr(attributeId);

        RxHttp.postJson(ApiService.API_SAVE_GOODS)
                .addAll(GsonUtils.toJson(mGoodsBean))
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading("保存中..."))  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    showToastSuccess("保存成功");
                    OttoBus.getInstance().post(new BusData(OTTOTags.REFRESH_TAGS, ""));
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    ToastUtils.showLong(exception.getMessage());

                });
    }


    private void initTimePicker() {//Dialog 模式下，在底部弹出
        mTimePickerView = new TimePickerBuilder(this, (date, v) -> {
            svGoodsDate.setRightString(getTime(date));
        }).setTimeSelectChangeListener(date -> {
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .addOnCancelClickListener(view -> Log.i("pvTime", "onCancelClickListener"))
                .setItemVisibleCount(5) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(2.0f)
                .isAlphaGradient(true)
                .build();

        Dialog mDialog = mTimePickerView.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM);
            params.leftMargin = 0;
            params.rightMargin = 0;
            mTimePickerView.getDialogContainerLayout().setLayoutParams(params);
            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.3f);
            }
        }
        mTimePickerView.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case ACTION_LEFT_BUTTON:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClickListener(SuperTextView superTextView) {
        switch (superTextView.getId()) {
            case R.id.sv_goods_subject:
                getSubjectList();
                break;
            case R.id.sv_goods_save_address:
                getCabinetList(buildingId);
                break;
            case R.id.sv_goods_attribute:
                getSysDict();
                break;
            case R.id.sv_goods_use:
                if (TextUtils.isEmpty(subjectId)) {
                    showToastFailure("请先选择科目");
                    return;
                }
                getUsage(subjectId, true);
                break;
            default:
                break;
        }
    }


    /**
     * 获取资产属性
     */
    private void getSysDict() {
        RxHttp.get(ApiService.API_SYS_DICT)
                .add("dictvalue", "ly030")
                .asResponseList(SysDictBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(sysDictList -> {
                    String[] strings = new String[sysDictList.size()];
                    for (int i = 0; i < sysDictList.size(); i++) {
                        strings[i] = sysDictList.get(i).getLabel();
                    }
                    runOnUiThread(() -> new XPopup.Builder(GoodsDetailsActivity.this)
                            .asBottomList("请选择资产属性", strings,
                                    (position, text) ->
                                    {
                                        svGoodsAttribute.setRightString(text);
                                        attributeId = sysDictList.get(position).getId();
                                    })
                            .show());

                }, throwable -> {

                });
    }

    /**
     * 获取该房间下的柜子列表
     *
     * @param buildingId
     */
    private void getCabinetList(String buildingId) {
        RxHttp.get(ApiService.API_CABINET_LIST)
                .add("buildingId", buildingId)
                .asResponseList(CabinetBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(cabinetList -> {
                    String[] strings = new String[cabinetList.size()];
                    for (int i = 0; i < cabinetList.size(); i++) {
                        strings[i] = cabinetList.get(i).getName();
                    }
                    runOnUiThread(() -> new XPopup.Builder(GoodsDetailsActivity.this)
                            .asBottomList("请选择存放位置", strings,
                                    (position, text) ->
                                    {
                                        new XPopup.Builder(this).asCustom(new AddressPopup(this, text).setOnAddressListener(address -> {
                                            if (!TextUtils.isEmpty(address)) {
                                                svGoodsSaveAddress.setRightIcon(R.drawable.icon_close);
                                                cabinetId = cabinetList.get(position).getId();
                                                svGoodsSaveAddress.setRightString(address);
                                            }
                                        })).show();
                                    }
                            )
                            .show());
                }, throwable -> {
                });
    }

    /**
     * 根据科目获取用途
     *
     * @param subjectId
     */
    private void getUsage(String subjectId, boolean isShow) {
        RxHttp.get(ApiService.API_USAGE)
                .add("subjectId", subjectId)
                .asResponseList(UsageBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(usageList -> {
                    if (usageList.isEmpty()) {
                        svGoodsUse.setRightString("");
                        usageId = "";
                        if (isShow) {
                            showToastFailure("当前科目下无用途");
                        }
                    } else {
                        String[] strings = new String[usageList.size()];
                        for (int i = 0; i < usageList.size(); i++) {
                            strings[i] = usageList.get(i).getUsageName();
                        }
                        svGoodsUse.setRightString(usageList.get(0).getUsageName());
                        usageId = usageList.get(0).getUsageId();
                        if (isShow) {
                            runOnUiThread(() -> new XPopup.Builder(GoodsDetailsActivity.this)
                                    .asBottomList("请选用途", strings,
                                            (position, text) ->
                                            {
                                                svGoodsUse.setRightString(text);
                                                usageId = usageList.get(position).getUsageId();

                                            }
                                    )
                                    .show());
                        }
                    }

                }, throwable -> {

                });
    }


    /**
     * 获取科目列表
     */
    private void getSubjectList() {
        RxHttp.get(ApiService.API_ALL_SUBJECT)
                .asResponseList(SubjectBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(subjectList -> {
                    String[] strings = new String[subjectList.size()];
                    for (int i = 0; i < subjectList.size(); i++) {
                        strings[i] = subjectList.get(i).getName();
                    }
                    runOnUiThread(() -> new XPopup.Builder(GoodsDetailsActivity.this)
                            .asBottomList("请选择科目", strings,
                                    (position, text) ->
                                    {
                                        svGoodsSubject.setRightString(text);
                                        subjectId = subjectList.get(position).getId();
                                        getUsage(subjectId, false);
                                    }
                            )
                            .show());
                }, throwable -> {
                    HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                    String errorMsg = exception.getResult(); //拿到msg字段
                    Response response = GsonUtils.fromJson(errorMsg, Response.class);
                    showToastFailure(response.getMessage());
                });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}