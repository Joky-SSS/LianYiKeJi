package com.lianyi.app.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.lianyi.app.R;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.AddressUrlBean;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.NodeBean;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.TokenBean;
import com.lianyi.app.utils.DoubleClicks;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.SystemBarUtil;
import com.rxjava.rxlife.RxLife;
import com.tencent.mmkv.MMKV;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;
import org.angmarch.views.SpinnerTextFormatter;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;


/**
 * @ClassName: LoginActivity
 * @Description:
 * @Author: Lee
 * @CreateDate: 2020/6/28 11:05
 */

@Route(path = RoutePath.PATH_LOGIN)
public class LoginActivity extends BaseActvity implements OnSpinnerItemSelectedListener, TextWatcher {

    @BindView(R.id.et_username)
    AppCompatEditText etUsername;
    @BindView(R.id.et_password)
    AppCompatEditText etPassword;
    @BindView(R.id.bt_button)
    Button btButton;
    @BindView(R.id.nice_spinner)
    NiceSpinner niceSpinner;
    @BindView(R.id.tv_devices_code)
    AppCompatTextView tvDevicesCode;

    @Override
    public int getLayoutId() {
        return R.layout.login_layout;
    }

    @Override
    public void initView() {
        SystemBarUtil.setStatusBarColor(this, Color.WHITE);
        SystemBarUtil.setStatusBarDarkMode(this,true);
        String name = MMKV.defaultMMKV().decodeString(Constant.USER_NAME);
        String passWord = MMKV.defaultMMKV().decodeString(Constant.PASS_WORD);
        etUsername.setText(name);
        etPassword.setText(passWord);

        if (!TextUtils.isEmpty(etUsername.getText().toString()) || !TextUtils.isEmpty(etPassword.getText().toString())) {
            btButton.setEnabled(true);
        } else {
            btButton.setEnabled(false);
        }


        niceSpinner.setOnSpinnerItemSelectedListener(this);
        etUsername.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);


    }

    /**
     * 获取节点信息
     */
    private void loadAddress() {
        RxHttp.get(ApiService.API_ADDRESS)
                .setDomainToIportalIfAbsent()
                //TODO test
                .add("snCode", DeviceUtils.getAndroidID())
//                .add("snCode", "baa50d7b655fa9c5")
                .asResponseList(NodeBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(nodeBean -> {
                    SpinnerTextFormatter textFormatter = (SpinnerTextFormatter<NodeBean>) node -> new SpannableString(node.getNodeName());
                    niceSpinner.setSpinnerTextFormatter(textFormatter);
                    niceSpinner.setSelectedTextFormatter(textFormatter);
                    niceSpinner.attachDataSource(nodeBean);
                    isSelectNode = true;
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage() + "节点信息");
                });

    }

    @SuppressLint("CheckResult")
    @Override
    public void initData() {
        tvDevicesCode.setText("设备唯一识别码:" + DeviceUtils.getAndroidID());
        loadAddress();
    }

    @Override
    public boolean isRegisterOttO() {
        return false;
    }

    @SuppressLint("CheckResult")
    @OnClick(R.id.bt_button)
    public void onViewClicked(View view) {
        if (DoubleClicks.isDoubleClick(view)){
            return;
        }

        if (TextUtils.isEmpty(etUsername.getText().toString())) {
            showToastFailure("请输入用户名");
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText().toString())) {
            showToastFailure("请输入密码");
            return;
        }
        if (!isSelectNode) {
            showToastFailure("请选择节点");
            return;
        }
        login(etUsername.getText().toString(), etPassword.getText().toString());
    }

    /**
     * 登录获取token
     *
     * @param name
     * @param password
     */
    private void login(String name, String password) {
        RxHttp.postForm(ApiService.API_LOGIN)
                .setDomainToLoginIfAbsent()
                .add("username", name)
                .add("password", password)
                .asClass(TokenBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(tokenBean -> {
                    tokenBean.setUserName(name);
                    tokenBean.setPassWord(password);
                    RxHttp.get(ApiService.API_NODE)
                            .add("token", tokenBean.getToken_type() + " " + tokenBean.getAccess_token())
                            .add("url", ((NodeBean) niceSpinner.getSelectedItem()).getNodeUrl())
                            .asString()
                            .doOnSubscribe(disposable -> showLoading("登录中..."))  //请求开始，当前在主线程回调
                            .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                            .as(RxLife.asOnMain(this))
                            .subscribe(str -> {
                                AddressUrlBean urlBean = GsonUtils.fromJson(str, AddressUrlBean.class);
                                ApiService.API_BASE = urlBean.getBackAccessUrl();
                                MMKV.defaultMMKV().encode(Constant.DATA, tokenBean);
                                MMKV.defaultMMKV().encode(Constant.USER_NAME, name);
                                MMKV.defaultMMKV().encode(Constant.PASS_WORD, password);
                                ARouter.getInstance().build(RoutePath.PATH_HOME).navigation();
                                finish();
                            }, throwable -> {
                                showToastFailure("获取地址失败");
                            });

                }, throwable -> {
                    HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                    String errorMsg = exception.getResult(); //拿到msg字段
                    Response response = GsonUtils.fromJson(errorMsg, Response.class);
                    showToastFailure(response.getMessage());
                });

    }

    boolean isSelectNode = false;

    @Override
    public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
        isSelectNode = true;
        NodeBean nodeBean = (NodeBean) parent.getSelectedItem();
        ApiService.API_LOGIN = nodeBean.getLoginUrl();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        if (TextUtils.isEmpty(str)) {
            btButton.setEnabled(false);
        } else {
            btButton.setEnabled(true);
        }
    }

}