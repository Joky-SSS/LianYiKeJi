package com.lianyi.app.ui;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_TEXT;

import android.app.Dialog;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.NestedScrollView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.allen.library.SuperTextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.lianyi.app.R;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.CabinetBean;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.InventoryGoodBean;
import com.lianyi.app.model.InventoryRoomListBean;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.SubjectBean;
import com.lianyi.app.model.SysDictBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.model.UsageBean;
import com.lianyi.app.otto.BusData;
import com.lianyi.app.otto.OTTOTags;
import com.lianyi.app.otto.OttoBus;
import com.lianyi.app.utils.DoubleClicks;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.weight.AddressPopup;
import com.lianyi.app.weight.DecimalEditTextView;
import com.lxj.xpopup.XPopup;
import com.rxjava.rxlife.RxLife;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;

/**
 * RFID盘点物品详情
 */
@Route(path = RoutePath.PATH_INVENTORY_GOODS_DETAILS)
public class InventoryGoodsDetailsActivity extends BaseActvity implements CommonTitleBar.OnTitleBarListener, SuperTextView.OnSuperTextViewClickListener {
    @Autowired(name = Constant.GOODS_ID)
    String goodsId;
    @Autowired(name = Constant.ROOM_BEAN)
    InventoryRoomListBean.RoomBean mRoomBean;
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;
    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.sv_goods_serial_number)
    SuperTextView svGoodsSerialNumber;
    @BindView(R.id.sv_goods_name)
    AppCompatTextView svGoodsName;
    @BindView(R.id.sv_goods_specifications)
    AppCompatTextView svGoodsSpecifications;
    @BindView(R.id.sv_goods_unit)
    SuperTextView svGoodsUnit;
    @BindView(R.id.et_goods_number)
    AppCompatTextView etGoodsNumber;
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

    @BindView(R.id.nested_scrollview)
    NestedScrollView nestedScrollview;
    @BindView(R.id.et_goods_barcode)
    AppCompatTextView etGoodsBarcode;
    private DecimalFormat df = new DecimalFormat("###");
    //科目，房间，柜子 ,用途 ，属性
    private String subjectId = "", buildingId = "", cabinetId = "", usageId = "", attributeId = "";

    TimePickerView mTimePickerView;
    InventoryGoodBean mGoodsBean;
    private boolean isContinue = false;

    @Override
    public int getLayoutId() {
        return R.layout.inventory_goods_details_layout;
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
        RxHttp.get(ApiService.API_RFID_GOODS_DETAILS,goodsId)
                .asResponse(InventoryGoodBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(goodsBean -> {
                    initGoods(goodsBean);
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
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
    private void initGoods(InventoryGoodBean goodsBean) {
        svGoodsSerialNumber.setRightString(goodsBean.getCode());
        svGoodsName.setText(goodsBean.getName());
        svGoodsSpecifications.setText(goodsBean.getSpec());
        svGoodsUnit.setRightString(goodsBean.getUom());

//        if (!TextUtils.isEmpty(goodsBean.getQuantity())) {
//            if (goodsBean.getQuantity().equals("0.00")) {
//                etGoodsNumber.setText("");
//            } else {
//                etGoodsNumber.setText(goodsBean.getQuantity());
//            }
//        }
        if (!TextUtils.isEmpty(goodsBean.getAmount())) {
            if (goodsBean.getAmount().equals("0.00")) {
                etGoodsUnitPrice.setText("");
            } else {
                etGoodsUnitPrice.setText(goodsBean.getAmount());
            }
        }
        svGoodsSubject.setRightString(goodsBean.getSubjectName());
        svGoodsUse.setRightString(goodsBean.getUsageName());
        svGoodsSaveAddress.setRightString(goodsBean.getLocDesc());
        etGoodsDepository.setText(goodsBean.getUserName());
        svGoodsDate.setRightString(goodsBean.getPurchaseDate());
        etGoodsExp.setText(goodsBean.getWarrantyTime());
        Float usageLife = null;
        try {
            usageLife = Float.parseFloat(goodsBean.getUsefulLife());
        } catch (NumberFormatException e) {
            usageLife = 0F;
        }
        etGoodsMfg.setText(df.format(usageLife));
        etGoodsBrand.setText(goodsBean.getBrand());
        etGoodsSupplier.setText(goodsBean.getVendorName());
        etGoodsContact.setText(goodsBean.getVendorContact());
        etGoodsContactPhone.setText(goodsBean.getVendorPhonenum());
        svGoodsAttribute.setRightString(goodsBean.getAssetAttrName());
        etGoodsBarcode.setText(goodsBean.getRfidCode());

        subjectId = goodsBean.getSubjectId();
        cabinetId = goodsBean.getContainerId();
        buildingId = goodsBean.getBuildingId();
        usageId = goodsBean.getUsageId();
        attributeId = goodsBean.getAssetAttr();

        mGoodsBean = goodsBean;
    }

    @OnClick({R.id.bt_save, R.id.sv_goods_date,R.id.bt_continue})
    public void onViewClicked(View view) {
        if (DoubleClicks.isDoubleClick(view)) {
            return;
        }
        switch (view.getId()) {
            case R.id.bt_save:
                if (mGoodsBean != null) {
                    isContinue = false;
                    updateGoods();
                }
                break;
            case R.id.bt_continue:
                if (mGoodsBean != null) {
                    isContinue = true;
                    updateGoods();
                }
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
        mGoodsBean.setName(svGoodsName.getText().toString());
        mGoodsBean.setSpec(svGoodsSpecifications.getText().toString());
        mGoodsBean.setUom(svGoodsUnit.getRightString());
        mGoodsBean.setQuantity(etGoodsNumber.getText().toString());
        mGoodsBean.setPrice(etGoodsUnitPrice.getText().toString());
        mGoodsBean.setSubjectId(subjectId);
        mGoodsBean.setSubjectName(svGoodsSubject.getRightString());
        mGoodsBean.setUsageId(usageId);
        mGoodsBean.setAmount(etGoodsUnitPrice.getText().toString());
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
        mGoodsBean.setAssetAttrName(svGoodsAttribute.getRightString());
        mGoodsBean.setRfidCode(etGoodsBarcode.getText().toString());

        RxHttp.postJson(ApiService.API_RFID_GOODS_SAVE)
                .addAll(GsonUtils.toJson(mGoodsBean))
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading("保存中..."))  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    showToastSuccess("保存成功");
                    OttoBus.getInstance().post(new BusData(OTTOTags.REFRESH_TAGS, ""));
                    if (!isContinue) finish();
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
            case ACTION_LEFT_TEXT:
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
//                getSubjectList();
                break;
            case R.id.sv_goods_save_address:
//                getCabinetList(buildingId);
                break;
            case R.id.sv_goods_attribute:
                getSysDict();
                break;
            case R.id.sv_goods_use:
//                if (TextUtils.isEmpty(subjectId)) {
//                    showToastFailure("请先选择科目");
//                    return;
//                }
//                getUsage(subjectId, true);
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
                    runOnUiThread(() -> new XPopup.Builder(InventoryGoodsDetailsActivity.this)
                            .asBottomList("请选择资产属性", strings,
                                    (position, text) ->
                                    {
                                        svGoodsAttribute.setRightString(text);
                                        attributeId = sysDictList.get(position).getId();
                                    })
                            .show());

                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
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
                    runOnUiThread(() -> new XPopup.Builder(InventoryGoodsDetailsActivity.this)
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
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
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
                            runOnUiThread(() -> new XPopup.Builder(InventoryGoodsDetailsActivity.this)
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
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
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
                    runOnUiThread(() -> new XPopup.Builder(InventoryGoodsDetailsActivity.this)
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

}