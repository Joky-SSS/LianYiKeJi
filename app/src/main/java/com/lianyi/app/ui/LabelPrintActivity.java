package com.lianyi.app.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.allen.library.SuperTextView;
import com.allenliu.classicbt.BleManager;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.gengcon.www.jcprintersdk.JCPrintApi;
import com.gengcon.www.jcprintersdk.callback.PrintCallback;
import com.google.gson.reflect.TypeToken;
import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.lianyi.app.utils.PrintUtil;
import com.lxj.xpopup.XPopup;
import com.lianyi.app.R;
import com.lianyi.app.adapter.LabelPrintAdapter;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.callback.SearchCallback;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.GoodsBean;
import com.lianyi.app.model.GoodsListBean;
import com.lianyi.app.model.RoomListBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.utils.BitmapUtils;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.SpacesItemDecoration;
import com.lianyi.app.weight.LabelTemplatePopup;
import com.lianyi.app.weight.PrintSettingPopup;
import com.rxjava.rxlife.RxLife;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mmkv.MMKV;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.RxHttp;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_BUTTON;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @ClassName: LabelPrintActivity
 * @Description: 标签打印
 * @Author: Lee
 * @CreateDate: 2020/7/5 21:11
 */
@Route(path = RoutePath.PATH_LABEL_PRINT)
public class LabelPrintActivity extends BaseActvity implements OnRefreshLoadMoreListener, OnItemClickListener, PrintSettingPopup.OnPrintSettingListener, SearchCallback.OnPrintSearchListener {
    private static final String TAG = "LabelPrintActivity";

    @Autowired(name = Constant.ROOM_BEAN)
    RoomListBean.ListBean mListBean;
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;

    LabelPrintAdapter mLabelPrintAdapter;
    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.et_search_view)
    AppCompatEditText etSearchView;
    @BindView(R.id.swipe_recyclerview)
    RecyclerView swipeRecyclerview;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_print_type)
    AppCompatTextView tvPrintType;
    @BindView(R.id.rb_goods_none)
    CheckBox rbGoodsNone;
    @BindView(R.id.bt_print_setting)
    AppCompatButton btPrintSetting;
    @BindView(R.id.bt_label_print)
    AppCompatButton btLabelPrint;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @BindView(R.id.iv_test)
    AppCompatImageView ivTest;
    /**
     * 图像数据
     */
    private ArrayList<String> jsonList;
    /**
     * 图像处理数据
     */
    private ArrayList<String> infoList;
    /**
     * 总份数
     */
    private int quantity;
    /**
     * 当前页打印份数
     */
    private int pageCount;
    /**
     * 是否打印错误
     */
    private boolean isError;
    /**
     * 是否取消打印
     */
    private boolean isCancel;

    private int printType = Constant.XXDJ_TYPE_3;
    /**
     * B3S系列打印机打印接口
     */
//    private JCAPI mJCAPI = null;
    private JCPrintApi mJCAPI = null;
    private PrintSettingPopup mPrintSettingPopup;
    private PrintCallback mPrintCallback;
    private BluetoothClient mBluetoothClient;

    private SearchCallback mSearchCallback;
    private LabelTemplatePopup mLabelTemplatePopup;
    private ExecutorService printExecutor = Executors.newSingleThreadExecutor();

    @Override
    public int getLayoutId() {
        return R.layout.label_print_layout;
    }

    @Override
    public void initView() {
        mBluetoothClient = new BluetoothClient(this);
        mSearchCallback = new SearchCallback();
        mSearchCallback.setOnPrintSearchListener(this);
        mPrintSettingPopup = new PrintSettingPopup(this, mBluetoothClient);
        mPrintSettingPopup.setOnPrintSettingListener(this);
        initRecycleView();
        refreshLayout.setOnRefreshLoadMoreListener(this);
    }

    @Override
    public void initData() {
        titleBar.setListener((v, action, extra) -> {
            switch (action) {
                case ACTION_LEFT_BUTTON:
                    finish();
                    break;
                default:
                    break;
            }
        });
        initPrint();
        refreshLayout.autoRefresh();
        mLabelTemplatePopup = new LabelTemplatePopup(this);

        etSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                queryStr = s.toString();
            }
        });
    }

    /**
     * 初始化打印机
     */
    @SuppressLint("CheckResult")
    private void initPrint() {
        jsonList = new ArrayList<>();
        infoList = new ArrayList<>();
        isError = false;
        isCancel = false;
        mPrintCallback = new PrintCallback() {
            @Override
            public void onProgress(int pageIndex, int quantityIndex, HashMap<String, Object> customData) {
            }

            @Override
            public void onError(int i, int i1) {

            }

            @Override
            public void onError(int i) {

            }

            @Override
            public void onBufferFree(int i, int i1) {

            }

            @Override
            public void onCancelJob(boolean b) {

            }
        };
        mJCAPI = PrintUtil.getInstance(getApplication());
    }

    /**
     * 搜索物品
     *
     * @param isRefresh  是否是刷新
     * @param query
     * @param buildingId
     */
    private void loadGoodsList(boolean isRefresh, String query, String buildingId) {
        RxHttp.get(ApiService.API_GOODS_LIST)
                .add("queryStr", query)
                .add("buildingId", buildingId)
                .add("taskId", mTaskBean.getId())
                .add("pageNo", pageNo)
                .add("pageSize", pageSize)
                .asResponse(GoodsListBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(() -> {
                    hideLoading();
                    hideKeyboard(etSearchView);
                })//请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(goodsListBean -> {
                    if (isRefresh) {
                        mLabelPrintAdapter.setList(goodsListBean.getDataList().getList());
                        swipeRecyclerview.scrollToPosition(0);
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();
                    } else {
                        if (pageNo > goodsListBean.getDataList().getPageCount()) {
                            refreshLayout.finishRefreshWithNoMoreData();
                        } else {
                            mLabelPrintAdapter.addData(goodsListBean.getDataList().getList());
                            refreshLayout.finishLoadMore();
                        }
                    }
                    rbGoodsNone.setChecked(false);

                }, throwable -> {

                });

    }

    @Override
    public boolean isRegisterOttO() {
        return false;
    }

    /**
     * 初始化recycleView
     */
    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        swipeRecyclerview.setLayoutManager(linearLayoutManager);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(10);
        swipeRecyclerview.addItemDecoration(spacesItemDecoration);
        mLabelPrintAdapter = new LabelPrintAdapter(null);
        swipeRecyclerview.setAdapter(mLabelPrintAdapter);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        mLabelPrintAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        loadGoodsList(false, queryStr, mListBean.getId());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        loadGoodsList(true, queryStr, mListBean.getId());
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        GoodsBean goodsBean = mLabelPrintAdapter.getData().get(position);
        goodsBean.setSelect(!goodsBean.isSelect());
        mLabelPrintAdapter.notifyItemChanged(position);
        isCheckTheAllButton(rbGoodsNone);
    }

    @OnClick({R.id.tv_print_type, R.id.bt_print_setting, R.id.bt_label_print, R.id.iv_search, R.id.rb_goods_none})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_print_type:
                new XPopup.Builder(this).asCustom(mLabelTemplatePopup.setPrintTypeListener(type -> {
                    printType = type;

                    switch (printType) {
                        case Constant.JXYQ_TYPE:
                            tvPrintType.setText("教学仪器1");
                            break;
                        case Constant.JXYQ_TYPE2:
                            tvPrintType.setText("教学仪器2");
                            break;
                        case Constant.JXYQ_TYPE3:
                            tvPrintType.setText("教学仪器3");
                            break;
                        case Constant.XXDJ_TYPE_1:
                            tvPrintType.setText("信息电教1");
                            break;
                        case Constant.XXDJ_TYPE_2:
                            tvPrintType.setText("信息电教2");
                            break;
                        case Constant.XXDJ_TYPE_3:
                            tvPrintType.setText("信息电教3");
                            break;
                        default:
                            break;
                    }

                })).show();
                break;
            case R.id.bt_print_setting:
                new RxPermissions(this)
                        .request(Manifest.permission.ACCESS_COARSE_LOCATION
                                , Manifest.permission.ACCESS_FINE_LOCATION
                                , Manifest.permission.BLUETOOTH_ADMIN
                        )
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                new XPopup.Builder(this)
                                        .asCustom(mPrintSettingPopup).show();
                            } else {
                                showToastFailure("权限申请被拒绝,无法搜索打印机");
                            }
                        });
                break;
            case R.id.bt_label_print:
                if (mJCAPI.isConnection() == 0) {
                    List<GoodsBean> goodsBeanList = getSelectGoodsIds(mLabelPrintAdapter);
                    if (goodsBeanList.isEmpty()){
                        showToastFailure("请选择物品");
                    }else{
                        loadLabelCode(printType, goodsBeanList);
                    }
                } else {
                    showToastFailure("请先连接打印机");
                }

                break;
            case R.id.iv_search:
                queryStr = TextUtils.isEmpty(etSearchView.getText().toString()) ? "" : etSearchView.getText().toString();
                loadGoodsList(true, queryStr, mListBean.getId());
                break;
            case R.id.rb_goods_none:
                boolean checked = ((CheckBox) view).isChecked();
                selectAllGoods(checked);
                break;
            default:
                break;
        }
    }

    /**
     * 解析图像处理信息中的打印份数
     *
     * @param jsonInfo 图像处理数据
     * @return 获取到的打印份数
     */
    private int getPrintQuantity(String jsonInfo) {
        try {
            JSONObject json = new JSONObject(jsonInfo);
            return json.getJSONObject("printerImageProcessingInfo").getInt("printQuantity");
        } catch (JSONException ignore) {
        }
        return 0;
    }

    private void generateMultiPagePrintData(int index, int cycleIndex, List<String> jsonInfoList, List<GoodsBean> goodsBeanList) {
        while (index < cycleIndex) {
            GoodsBean goodsBean = goodsBeanList.get(index);
            String jsonStr = null;
            switch (printType) {
                case Constant.JXYQ_TYPE:
                    jsonStr = getPrintJsonJXYQ1(goodsBean);
                    break;
                case Constant.JXYQ_TYPE2:
                    jsonStr = getPrintJsonJXYQ2(goodsBean);
                    break;
                case Constant.JXYQ_TYPE3:
                    jsonStr = getPrintJsonJXYQ3(goodsBean);
                    break;
                case Constant.XXDJ_TYPE_1:
                    jsonStr = getPrintJsonXXDJ1(goodsBean);
                    break;
                case Constant.XXDJ_TYPE_2:
                    jsonStr = getPrintJsonXXDJ2(goodsBean);
                    break;
                case Constant.XXDJ_TYPE_3:
                    jsonStr = getPrintJsonXXDJ3(goodsBean);
                    break;
                default:
                    break;
            }
            jsonList.add(jsonStr);
            infoList.add(jsonInfoList.get(index));
            quantity = getPrintQuantity(jsonInfoList.get(index));
            index++;

        }

    }

    /**
     * 教学仪器1
     *
     * @param goodsBean
     * @return
     */
    private String getPrintJsonJXYQ1(GoodsBean goodsBean) {
        //设置画布大小
        mJCAPI.drawEmptyLabel(50, 20, 0, "");
        //画二维码
        mJCAPI.drawLabelQrCode(32, 2, 16, 16, goodsBean.getBarcodeUrl(), 31, 0);//二维码
        //字号 2.5f
        mJCAPI.drawLabelText(2, 2, 22, 5, "编号:" + (TextUtils.isEmpty(goodsBean.getCode()) ? "" : goodsBean.getCode()), "宋体", 2.5f, 0, 0, 0, 3, 0, 1, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(2, 7, 22, 6, "名称:" + (TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName()), "宋体", 2.5f, 0, 0, 1, 3, 0, 1, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(2, 13, 22, 5, "规格:" + (TextUtils.isEmpty(goodsBean.getSpec()) ? "" : goodsBean.getSpec()), "宋体", 2.5f, 0, 0, 2, 3, 0, 1, new boolean[]{false, false, false, false});

        //生成打印数据
        byte[] jsonByte = mJCAPI.generateLabelJson();

        //转换为jsonStr
        String jsonStr = new String(jsonByte);
        return jsonStr;
    }

    /**
     * 教学仪器2
     *
     * @param goodsBean
     * @return
     */
    private String getPrintJsonJXYQ2(GoodsBean goodsBean) {
        //设置画布大小
        mJCAPI.drawEmptyLabel(50, 20, 0, "");
        //画二维码
        mJCAPI.drawLabelQrCode(32, 2, 16, 16, goodsBean.getBarcodeUrl(), 31, 0);//二维码
        //字号 2.5f
        mJCAPI.drawLabelText(2, 2, 22, 4, "编号:" + (TextUtils.isEmpty(goodsBean.getCode()) ? "" : goodsBean.getCode()), "宋体", 2.5f, 0, 0, 1, 3, 0, 1, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(2, 6, 22, 4, "名称:" + (TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName()), "宋体", 2.5f, 0, 0, 1, 3, 0, 1, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(2, 10, 22, 4, "规格:" + (TextUtils.isEmpty(goodsBean.getSpec()) ? "" : goodsBean.getSpec()), "宋体", 2.5f, 0, 0, 1, 3, 0, 1, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(2, 14, 22, 4, "房间:" + (TextUtils.isEmpty(goodsBean.getBuildingName()) ? "" : goodsBean.getBuildingName()), "宋体", 2.5f, 0, 0, 1, 3, 0, 1, new boolean[]{false, false, false, false});

        //生成打印数据
        byte[] jsonByte = mJCAPI.generateLabelJson();

        //转换为jsonStr
        String jsonStr = new String(jsonByte);
        return jsonStr;
    }

    /**
     * 教学仪器3
     *
     * @param goodsBean
     * @return
     */
    private String getPrintJsonJXYQ3(GoodsBean goodsBean) {
        //设置画布大小
        mJCAPI.drawEmptyLabel(50, 20, 0, "");
        //画最外边的框
        mJCAPI.drawLabelGraph(1, 1, 48, 18, 3, 0, 2, 0.2f, 1, new float[]{0.7575f, 0.7575f});
        //画中间的线
        mJCAPI.drawLabelLine(1, 7, 48, 0.2F, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelLine(1, 10, 36, 0.2F, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelLine(1, 13, 36, 0.2F, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelLine(1, 16, 36, 0.2F, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelLine(9, 7, 0.2F, 12, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelLine(37, 7, 0.2F, 12, 0, 1, new float[]{0.7575f, 0.7575f});
        //画二维码
        mJCAPI.drawLabelQrCode(38, 8, 10, 10, goodsBean.getBarcodeUrl(), 31, 0);//二维码
        //字号 2.5f
        mJCAPI.drawLabelText(2, 1, 46, 6, TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName(), "宋体", 2.5f, 0, 1, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(1, 7, 8, 3, "编号:", "宋体", 2.5f, 0, 1, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(10, 7, 27, 3, (TextUtils.isEmpty(goodsBean.getCode()) ? "" : goodsBean.getCode()), "宋体", 2.5f, 0, 0, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(1, 10, 8, 3, "规格:", "宋体", 2.5f, 0, 1, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(10, 10, 27, 3, (TextUtils.isEmpty(goodsBean.getSpec()) ? "" : goodsBean.getSpec()), "宋体", 2.5f, 0, 0, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(1, 13, 8, 3, "房间:", "宋体", 2.5f, 0, 1, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(10, 13, 27, 3, (TextUtils.isEmpty(goodsBean.getBuildingName()) ? "" : goodsBean.getBuildingName()), "宋体", 2.5f, 0, 0, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(1, 16, 8, 3, "柜子:", "宋体", 2.5f, 0, 1, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelText(10, 16, 27, 3, (TextUtils.isEmpty(goodsBean.getContainerName()) ? "" : goodsBean.getContainerName()), "宋体", 2.5f, 0, 0, 1, 3, 0, 0, new boolean[]{false, false, false, false});

        //生成打印数据
        byte[] jsonByte = mJCAPI.generateLabelJson();

        //转换为jsonStr
        String jsonStr = new String(jsonByte);
        return jsonStr;
    }

    /**
     * 信息电教1
     *
     * @param goodsBean
     * @return
     */
    private String getPrintJsonXXDJ1(GoodsBean goodsBean) {
        //设置画布大小
        mJCAPI.drawEmptyLabel(50, 30, 0, "");
        //画最外边的框
        mJCAPI.drawLabelGraph(1, 1, 48, 28, 3, 0, 2, 0.2f, 1, new float[]{0.7575f, 0.7575f});
        //画中间的线
        mJCAPI.drawLabelLine(25, 1, 0.2f, 24, 0, 1, new float[]{0.7575f, 0.7575f});
        //画二维码
        mJCAPI.drawLabelQrCode(3, 3, 20, 20, goodsBean.getBarcodeUrl(), 31, 0);//二维码
        //字号 2.5f
        mJCAPI.drawLabelText(26, 3.5f, 22, 3, "名称:" + (TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName()), "宋体", 2.5f, 0, 0, 0, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelLine(25, 9, 24, 0.2f, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelText(26, 11.5f, 22, 3, "科目:" + (TextUtils.isEmpty(goodsBean.getSubjectName()) ? "" : goodsBean.getSubjectName()), "宋体", 2.5f, 0, 0, 0, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelLine(25, 17, 24, 0.2f, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelText(26, 19.5f, 22, 3, "房间:" + (TextUtils.isEmpty(goodsBean.getBuildingName()) ? "" : goodsBean.getBuildingName()), "宋体", 2.5f, 0, 0, 0, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelLine(1, 25, 48, 0.2f, 0, 1, new float[]{0.7575f, 0.7575f});
        //底部说明 居中对齐 垂直居中
        mJCAPI.drawLabelText(1, 25.5f, 48, 3, "※请保护学校财产,不要损坏此标签※", "宋体", 2.5f, 0, 1, 0, 3, 0, 0, new boolean[]{false, false, false, false});

        //生成打印数据
        byte[] jsonByte = mJCAPI.generateLabelJson();

        //转换为jsonStr
        String jsonStr = new String(jsonByte);
        return jsonStr;
    }

    /**
     * 信息电教2
     *
     * @param goodsBean
     * @return
     */
    private String getPrintJsonXXDJ2(GoodsBean goodsBean) {
        //设置画布大小
        mJCAPI.drawEmptyLabel(50, 30, 0, "");
        //画最外边的框
        mJCAPI.drawLabelGraph(1, 1, 48, 28, 3, 0, 2, 0.2f, 1, new float[]{0.7575f, 0.7575f});
        //画中间的线
        mJCAPI.drawLabelLine(25, 1, 0.2f, 24, 0, 1, new float[]{0.7575f, 0.7575f});
        //画二维码
        mJCAPI.drawLabelQrCode(3, 3, 20, 20, goodsBean.getBarcodeUrl(), 31, 0);//二维码
        //字号 2.5f
        mJCAPI.drawLabelText(26, 3.5f, 22, 3, "名称:" + (TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName()), "宋体", 2.5f, 0, 0, 0, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelLine(25, 9, 24, 0.2f, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelText(26, 11.5f, 22, 3, "规格:" + (TextUtils.isEmpty(goodsBean.getSpec()) ? "" : goodsBean.getSpec()), "宋体", 2.5f, 0, 0, 0, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelLine(25, 17, 24, 0.2f, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelText(26, 19.5f, 22, 3, "保管人:" + (TextUtils.isEmpty(goodsBean.getUserName()) ? "" : goodsBean.getUserName()), "宋体", 2.5f, 0, 0, 0, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelLine(1, 25, 48, 0.2f, 0, 1, new float[]{0.7575f, 0.7575f});
        //底部说明 居中对齐 垂直居中
        mJCAPI.drawLabelText(1, 25.5f, 48, 3, "※请保护学校财产,不要损坏此标签※", "宋体", 2.5f, 0, 1, 0, 3, 0, 0, new boolean[]{false, false, false, false});

        //生成打印数据
        byte[] jsonByte = mJCAPI.generateLabelJson();

        //转换为jsonStr
        String jsonStr = new String(jsonByte);
        return jsonStr;
    }

    /**
     * 信息电教1
     *
     * @param goodsBean
     * @return
     */
    private String getPrintJsonXXDJ3(GoodsBean goodsBean) {
        //设置画布大小
        mJCAPI.drawEmptyLabel(50, 30, 0, "");
        //画最外边的框
        mJCAPI.drawLabelGraph(1, 1, 48, 28, 3, 0, 2, 0.2f, 1, new float[]{0.7575f, 0.7575f});
        //画中间的线
        mJCAPI.drawLabelLine(25, 1, 0.2f, 24, 0, 1, new float[]{0.7575f, 0.7575f});
        //画二维码
        mJCAPI.drawLabelQrCode(3, 3, 20, 20, goodsBean.getBarcodeUrl(), 31, 0);//二维码
        //字号 2.5f
        mJCAPI.drawLabelText(26, 1f, 22, 12, "名称:" + (TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName()), "宋体", 2.5f, 0, 0, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelLine(25, 13, 24, 0.2f, 0, 1, new float[]{0.7575f, 0.7575f});
        mJCAPI.drawLabelText(26, 13, 22, 12, "房间:" + (TextUtils.isEmpty(goodsBean.getBuildingName()) ? "" : goodsBean.getBuildingName()), "宋体", 2.5f, 0, 0, 1, 3, 0, 0, new boolean[]{false, false, false, false});
        mJCAPI.drawLabelLine(1, 25, 48, 0.2f, 0, 1, new float[]{0.7575f, 0.7575f});
        //底部说明 居中对齐 垂直居中
        mJCAPI.drawLabelText(1, 25.5f, 48, 3, "※请保护学校财产,不要损坏此标签※", "宋体", 2.5f, 0, 1, 0, 3, 0, 0, new boolean[]{false, false, false, false});

        //生成打印数据
        byte[] jsonByte = mJCAPI.generateLabelJson();

        //转换为jsonStr
        String jsonStr = new String(jsonByte);
        return jsonStr;
    }

    /**
     * 获取选中物品的标签
     *
     * @param printType 标签类型
     */
    private void loadLabelCode(int printType, List<GoodsBean> list) {
        RxHttp.postJsonArray(ApiService.API_LABEL_PRINT)
                .addAll(list)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    List<GoodsBean> beanList = GsonUtils.fromJson(str, new TypeToken<List<GoodsBean>>() {
                    }.getType());

                    printExecutor.submit(() -> {
                        runOnUiThread(()->{
                            showLoading("");
                        });
                        //重置错误状态变量
                        isError = false;
                        //重置取消打印状态变量
                        isCancel = false;
                        //清除数据
                        jsonList.clear();
                        infoList.clear();
                        String jsonInfoFirst = null;
                        List<String> jsonInfoList = new ArrayList<>();
                        List<GoodsBean> goodsBeanList = new ArrayList<>();
                        int totalQuantity = 0;
                        /*
                         * orientation 旋转角度 0/90/180/270
                         * margin 边距 「」
                         * printQuantity 打印份数
                         * horizontalOffset 水平边距
                         * verticalOffset 垂直边距
                         * width 标签宽度（出纸方向,单位mm）
                         * height 标签宽度（出纸方向,单位mm）
                         */
                        for (GoodsBean bean : beanList){
                            System.out.println(bean.toString());
                            switch (printType) {
                                case Constant.JXYQ_TYPE:
                                case Constant.JXYQ_TYPE2:
                                case Constant.JXYQ_TYPE3:
                                    jsonInfoFirst = "{  " +
                                            "\"printerImageProcessingInfo\": " + "{    " +
                                            "\"orientation\": 0, " +
                                            "   \"margin\": [      0,      0,      0,      0    ], " +
                                            "   \"printQuantity\": " + bean.getQuantity() + ",  " +
                                            "  \"horizontalOffset\": 0,  " +
                                            "  \"verticalOffset\": 0,  " +
                                            "  \"width\": 50, " +
                                            "   \"height\": 20,   " +
                                            "  \"epc\": \"\"  }}";
                                    break;
                                case Constant.XXDJ_TYPE_1:
                                case Constant.XXDJ_TYPE_2:
                                case Constant.XXDJ_TYPE_3:
                                    jsonInfoFirst = "{  " +
                                            "\"printerImageProcessingInfo\": " + "{    " +
                                            "\"orientation\": 0, " +
                                            "   \"margin\": [      0,      0,      0,      0    ], " +
                                            "   \"printQuantity\": " + bean.getQuantity() + ",  " +
                                            "  \"horizontalOffset\": 0,  " +
                                            "  \"verticalOffset\": 0,  " +
                                            "  \"width\": 50, " +
                                            "   \"height\": 30,   " +
                                            "  \"epc\": \"\"  }}";
                                    break;
                                default:
                                    break;
                            }
                            jsonInfoList.add(jsonInfoFirst);
                            //总打印份数，每页打印份数之和（当前每页打印1张，打印10页，所以总打印份数为10）
                            totalQuantity += getPrintQuantity(jsonInfoFirst);
                        }

                        //单次SDK打印缓存数据为最大为4页，默认首次提交SDK内部缓存为空，可以最多提交4页数据，大于4页需要多次生成数据进行提交
                        pageCount = jsonInfoList.size();
                        final int[] generatedPrintDataPageCount = {0};


                        //设置总打印份数。每页份数之和
                        mJCAPI.setTotalQuantityOfPrints(totalQuantity);
                        mJCAPI.startPrintJob(5, 1, 2, new PrintCallback() {
                            /**
                             * 打印进度回调
                             * @param pageIndex 页序号
                             * @param quantityIndex 份序号
                             * @param customData 自定义数据（无需处理）
                             */
                            @Override
                            public void onProgress(int pageIndex, int quantityIndex, HashMap<String, Object> customData) {
                                //打印进度回调
                                Log.d(TAG, "onProgress: pageIndex" + pageIndex + ",quantityIndex:" + quantityIndex);
                                if (pageIndex == pageCount && quantityIndex == quantity) {
                                    if (mJCAPI.endJob()) {
                                        Log.d(TAG, "结束打印成功");
                                    } else {
                                        Log.d(TAG, "结束打印失败");
                                    }
                                    runOnUiThread(()->{
                                        hideLoading();
                                    });
                                }
                            }

                            /**
                             *错误回调
                             * @param errorCode 错误码
                             * @param printState 打印状态
                             */
                            @Override
                            public void onError(int errorCode, int printState) {
                                Log.d(TAG, " 错误回调:错误码：" + errorCode);
                                isError = true;
                                runOnUiThread(()->{
                                    hideLoading();
                                });
                            }

                            /**
                             * 错误回调
                             *
                             * @param errorCode 错误码
                             */
                            @Override
                            public void onError(int errorCode) {
                                isError = true;
                                runOnUiThread(()->{
                                    hideLoading();
                                });
                            }

                            @Override
                            public void onBufferFree(int pageIndex, int bufferSize) {


                                if (isError || isCancel) {
                                    return;
                                }

                                if (pageIndex > pageCount) {
                                    return;
                                }
                                //已生成数据的页数小于总页数才生成数据
                                if (generatedPrintDataPageCount[0] < pageCount) {
                                    //需要生成的数据长度小于可缓存的长度
                                    int count = 0;
                                    int endIndex = 0;
                                    if ((pageCount - generatedPrintDataPageCount[0]) < bufferSize) {
                                        //生成长度为(pageCount-generatedPrintDataPageCount[0])的数据
                                        count = pageCount;
                                        endIndex = pageCount;

                                    } else {
                                        count = bufferSize;
                                        endIndex = generatedPrintDataPageCount[0] + bufferSize;

                                    }
                                    generateMultiPagePrintData(generatedPrintDataPageCount[0], endIndex, jsonInfoList, beanList.subList(generatedPrintDataPageCount[0],endIndex));
                                    mJCAPI.commitData(
                                            jsonList.subList(generatedPrintDataPageCount[0], endIndex),
                                            infoList.subList(generatedPrintDataPageCount[0], endIndex));
                                    generatedPrintDataPageCount[0] += count;
                                }


                            }

                            /**
                             * 暂停成功与否
                             *
                             * @param isSuccess 成功与否
                             */
//                                                @Override
//                                                public void onPause(boolean isSuccess) {
//                                                    isCancel = true;
//                                                    //暂停成功直接调用取消打印（暂未开放暂停打印及恢复打印接口，此处触发一般为异常暂停）
//                                                    mJCAPI.cancelJob();
//                                                }

                            /**
                             * 取消回调
                             *
                             * @param isSuccess 取消成功回调与否
                             *
                             */
                            @Override
                            public void onCancelJob(boolean isSuccess) {
                                //取消成功处理
                                isCancel = true;
                                runOnUiThread(()->{
                                    hideLoading();
                                });
                            }
                        });
                    });
                    System.out.println("打印机连接成功");
                }, throwable -> {

                });
    }

    /**
     * 全选
     *
     * @param isSelectAll 是否全选
     */
    public void selectAllGoods(boolean isSelectAll) {
        for (GoodsBean datum : mLabelPrintAdapter.getData()) {
            datum.setSelect(isSelectAll);
        }
        mLabelPrintAdapter.notifyDataSetChanged();
    }


    public void isCheckTheAllButton(CheckBox checkBox) {
        for (GoodsBean datum : mLabelPrintAdapter.getData()) {
            if (datum.isSelect() == false) {
                checkBox.setChecked(false);
                return;
            }
        }

    }


    /**
     * 获取选中的物品ID
     *
     * @param labelPrintAdapter
     * @return
     */
    public List<GoodsBean> getSelectGoodsIds(LabelPrintAdapter labelPrintAdapter) {
        List<GoodsBean> list = new ArrayList<>();
        if (labelPrintAdapter.getData().size() <= 0) {
            return list;
        }
        for (GoodsBean datum : labelPrintAdapter.getData()) {
            if (datum.isSelect()) {
                list.add(datum);
            }
        }
        return list;
    }

    @Override
    public void onShow(SuperTextView superTextView) {
        if (mJCAPI != null && mJCAPI.isConnection() == 0) {
            BluetoothDevice bluetoothDevice = MMKV.defaultMMKV().decodeParcelable(Constant.BLUETOOTH_DEVICE, BluetoothDevice.class);
            superTextView.setRightString(bluetoothDevice.getName());
        }
    }

    @Override
    public void onSearchDevices(SuperTextView svPrintDevices) {
        if (mBluetoothClient.isBluetoothOpened()) {
            BluetoothDevice bluetoothDevice = MMKV.defaultMMKV().decodeParcelable(Constant.BLUETOOTH_DEVICE, BluetoothDevice.class);
            if (bluetoothDevice != null) {//DC:0D:30:81:A9:61
                mJCAPI.openPrinterByAddress(bluetoothDevice.getAddress());
//                mJCAPI.openPrinterByAddress(AppHolder.getInstance(), bluetoothDevice.getAddress(), 0);
                svPrintDevices.setRightString(bluetoothDevice.getName());
//                showLoading("正在连接打印机....", false);
            } else {
                mBluetoothClient.search(new SearchRequest.Builder()
                        .searchBluetoothClassicDevice(5000) // 经典蓝牙5s
                        .build(), mSearchCallback);
            }

        } else {
            showToastFailure("请先开启蓝牙");
        }

    }

    @Override
    public void onOpenBluetooth(boolean isChecked) {
        if (isChecked) {
            mBluetoothClient.openBluetooth();
        } else {

            if (mJCAPI.isConnection() == 0) {
                mJCAPI.close();
            }
            mBluetoothClient.closeBluetooth();
        }
    }

    @Override
    public void onPrintPaperType(int paperType) {

    }

    @Override
    public void onPrintConcentration(int printConcentration) {

    }

    @Override
    public void onPrintSpeed(int speed) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mJCAPI.close();
//        BluetoothDevice bluetoothDevice = MMKV.defaultMMKV().decodeParcelable(Constant.BLUETOOTH_DEVICE, BluetoothDevice.class);
//        if (bluetoothDevice != null) {
//            mBluetoothClient.disconnect(bluetoothDevice.getAddress());
//        }
    }


    @Override
    public void onSearchStarted() {
        showLoading("正在搜索....", false);
    }

    @Override
    public void onSearchStopped(List<SearchResult> bleDeviceList) {
        hideLoading();
        String[] deviceArray = new String[bleDeviceList.size()];
        for (int i = 0; i < bleDeviceList.size(); i++) {
            deviceArray[i] = bleDeviceList.get(i).getName();
        }
        new XPopup.Builder(this).asBottomList("请选择需要连接的打印机", deviceArray, (position, text) -> {
            String address = bleDeviceList.get(position).getAddress();
            BluetoothDevice bluetoothDevice = BluetoothUtils.getRemoteDevice(address);
            if (BleManager.getInstance().getPairedDevices().contains(bluetoothDevice)) {
                mBluetoothClient.connect(bluetoothDevice.getAddress(), (code, data) -> {
                    mJCAPI.openPrinterByAddress(address);
//                    mJCAPI.openPrinterByAddress(AppHolder.getInstance(), address, 0);
                    MMKV.defaultMMKV().encode(Constant.BLUETOOTH_DEVICE, bluetoothDevice);

                });
            } else {
                BleManager.getInstance().pin(bluetoothDevice, device -> {
                    mBluetoothClient.connect(bluetoothDevice.getAddress(), (code, data) -> {
                        mJCAPI.openPrinterByAddress(address);
//                        mJCAPI.openPrinterByAddress(AppHolder.getInstance(), address, 0);
                    });
                });
            }
            //showLoading("正在连接打印机....", false);
        }).show();
    }

    @Override
    public void onSearchCanceled() {
        hideLoading();
    }


}