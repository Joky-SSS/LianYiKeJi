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

    private int printType = 2;
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
            public void onProgress(int pageIndex, int quantityIndex, HashMap<String, Object> customData){
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
            public void onPause(boolean b) {

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
                        case 1:
                            tvPrintType.setText("教学仪器");

                            break;
                        case 2:
                        case 3:
                            tvPrintType.setText("信息电教");
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
                loadLabelCode(printType, getSelectGoodsIds(mLabelPrintAdapter));
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

    private void generateMultiPagePrintData(int index, int cycleIndex, List<String> jsonInfoList, GoodsBean goodsBean) {
        while (index < cycleIndex) {
            //设置画布大小
            PrintUtil.getInstance(getApplication()).drawEmptyLabel(50, 30, 0, "");
            //画最外边的框
            PrintUtil.getInstance(null).drawLabelGraph(1, 1, 48, 28, 3, 0, 2, 0.2f, 1, new float[]{0.7575f, 0.7575f});
            //画中间的线
            PrintUtil.getInstance(null).drawLabelLine(25,1,0.2f,24,0,1,new float[]{0.7575f, 0.7575f});
            //画二维码
            PrintUtil.getInstance(null).drawLabelQrCode(3, 3, 20, 20, goodsBean.getBarcodeUrl(), 31, 0);//二维码
            //字号 2.5f
            PrintUtil.getInstance(null).drawLabelText(26, 3.5f, 22, 3, "名称:"+(TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName()) , "宋体", 2.5f, 0, 0, 0, 3, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance(null).drawLabelLine(25,9,24,0.2f,0,1,new float[]{0.7575f, 0.7575f});
            PrintUtil.getInstance(null).drawLabelText(26, 11.5f, 22, 3, "科目:"+(TextUtils.isEmpty(goodsBean.getSubjectName()) ? "" : goodsBean.getSubjectName()) , "宋体", 2.5f, 0, 0, 0, 3, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance(null).drawLabelLine(25,17,24,0.2f,0,1,new float[]{0.7575f, 0.7575f});
            PrintUtil.getInstance(null).drawLabelText(26, 19.5f, 22, 3, "房间:"+(TextUtils.isEmpty(goodsBean.getBuildingName()) ? "" : goodsBean.getBuildingName()) , "宋体", 2.5f, 0, 0, 0, 3, 0, 1, new boolean[]{false, false, false, false});
            PrintUtil.getInstance(null).drawLabelLine(1,25,48,0.2f,0,1,new float[]{0.7575f, 0.7575f});
            //底部说明 居中对齐 垂直居中
            PrintUtil.getInstance(null).drawLabelText(1, 25.5f, 48, 3, "※请保护学校财产,不要损坏此标签※", "宋体", 2.5f, 0, 1, 0, 3, 0, 1, new boolean[]{false, false, false, false});

            //生成打印数据
            byte[] jsonByte = PrintUtil.getInstance(null).generateLabelJson();

            //转换为jsonStr
            String jsonStr = new String(jsonByte);

            jsonList.add(jsonStr);
            infoList.add(jsonInfoList.get(index));
            quantity = getPrintQuantity(jsonInfoList.get(index));
            index++;

        }

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
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    for (GoodsBean goodsBean : beanList) {
                        System.out.println(goodsBean.toString());
                        executor.submit(() -> {
                            switch (printType) {
                                case 1:
                                    for (int i = 0; i < Integer.parseInt(goodsBean.getQuantity()); i++) {
//                                        for (int i = 0; i < 1; i++) {
                                        runOnUiThread(() -> {
                                            Bitmap jxyqBitmap = BitmapUtils.jxyqLabelView(this, goodsBean);
//                                            mJCAPI.startJob(50, 30, 0);
//                                            mJCAPI.startPage();
//                                            mJCAPI.drawBitmap(jxyqBitmap, 2, 4, 0, 0, 0);
//                                            mJCAPI.endPage();
//                                            mJCAPI.commitJob(1, 1, 10, mPrintCallback);
//                                            ivTest.setImageBitmap(jxyqBitmap);
                                        });
                                    }
                                    break;
                                case 2:
//                                    for (int i = 0; i < Integer.parseInt(goodsBean.getQuantity()); i++) {
//                                        for (int i = 0; i < 1; i++) {
                                        runOnUiThread(() -> {
                                            //重置错误状态变量
                                            isError = false;
                                            //重置取消打印状态变量
                                            isCancel = false;
                                            //清除数据
                                            jsonList.clear();
                                            infoList.clear();
                                            /*
                                             * orientation 旋转角度 0/90/180/270
                                             * margin 边距 「」
                                             * printQuantity 打印份数
                                             * horizontalOffset 水平边距
                                             * verticalOffset 垂直边距
                                             * width 标签宽度（出纸方向,单位mm）
                                             * height 标签宽度（出纸方向,单位mm）
                                             */
                                            String jsonInfoFirst = "{  " +
                                                    "\"printerImageProcessingInfo\": " + "{    " +
                                                    "\"orientation\": 0, " +
                                                    "   \"margin\": [      0,      0,      0,      0    ], " +
                                                    "   \"printQuantity\": "+goodsBean.getQuantity()+",  " +
                                                    "  \"horizontalOffset\": 0,  " +
                                                    "  \"verticalOffset\": 0,  " +
                                                    "  \"width\": 50, " +
                                                    "   \"height\": 30,   " +
                                                    "  \"epc\": \"\"  }}";

//                                            String jsonInfoTwo = "{  " +
//                                                    "\"printerImageProcessingInfo\": " + "{    " +
//                                                    "\"orientation\": 0, " +
//                                                    "   \"margin\": [      0,      0,      0,      0    ], " +
//                                                    "   \"printQuantity\": 3,  " +
//                                                    "  \"horizontalOffset\": 0,  " +
//                                                    "  \"verticalOffset\": 0,  " +
//                                                    "  \"width\": 50, " +
//                                                    "   \"height\": 30,   " +
//                                                    "  \"epc\": \"\"  }}";

                                            List<String> jsonInfoList = new ArrayList<>();
                                            jsonInfoList.add(jsonInfoFirst);
//                                            jsonInfoList.add(jsonInfoTwo);


                                            //单次SDK打印缓存数据为最大为4页，默认首次提交SDK内部缓存为空，可以最多提交4页数据，大于4页需要多次生成数据进行提交
                                            pageCount = jsonInfoList.size();
                                            //总打印份数，每页打印份数之和（当前每页打印1张，打印10页，所以总打印份数为10）
                                            int totalQuantity = 0;
                                            for (int j = 0; j < pageCount; j++) {
                                                totalQuantity += getPrintQuantity(jsonInfoList.get(j));
                                            }


                                            final int[] generatedPrintDataPageCount = {0};


                                            //设置总打印份数。每页份数之和
                                            PrintUtil.getInstance(null).setTotalQuantityOfPrints(totalQuantity);
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
                                                        if (PrintUtil.getInstance(null).endJob()) {
                                                            Log.d(TAG, "结束打印成功");
                                                        } else {
                                                            Log.d(TAG, "结束打印失败");
                                                        }
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
                                                }

                                                /**
                                                 * 错误回调
                                                 *
                                                 * @param errorCode 错误码
                                                 */
                                                @Override
                                                public void onError(int errorCode) {

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
                                                        if ((pageCount - generatedPrintDataPageCount[0]) < bufferSize) {
                                                            //生成长度为(pageCount-generatedPrintDataPageCount[0])的数据
                                                            generateMultiPagePrintData(generatedPrintDataPageCount[0], pageCount, jsonInfoList, goodsBean);
                                                            PrintUtil.getInstance(null).commitData(
                                                                    jsonList.subList(generatedPrintDataPageCount[0], pageCount),
                                                                    infoList.subList(generatedPrintDataPageCount[0], pageCount));

                                                            generatedPrintDataPageCount[0] += pageCount;

                                                        } else {
                                                            generateMultiPagePrintData(generatedPrintDataPageCount[0], generatedPrintDataPageCount[0] + bufferSize, jsonInfoList, goodsBean);
                                                            PrintUtil.getInstance(null).commitData(
                                                                    jsonList.subList(generatedPrintDataPageCount[0], generatedPrintDataPageCount[0] + bufferSize),
                                                                    infoList.subList(generatedPrintDataPageCount[0], generatedPrintDataPageCount[0] + bufferSize));

                                                            generatedPrintDataPageCount[0] += bufferSize;
                                                        }
                                                    }


                                                }

                                                /**
                                                 * 暂停成功与否
                                                 *
                                                 * @param isSuccess 成功与否
                                                 */
                                                @Override
                                                public void onPause(boolean isSuccess) {
                                                    isCancel = true;
                                                    //暂停成功直接调用取消打印（暂未开放暂停打印及恢复打印接口，此处触发一般为异常暂停）
                                                    PrintUtil.getInstance(null).cancelJob();
                                                }

                                                /**
                                                 * 取消回调
                                                 *
                                                 * @param isSuccess 取消成功回调与否
                                                 *
                                                 */
                                                @Override
                                                public void onCancelJob(boolean isSuccess) {
                                                    //取消成功处理
                                                }
                                            });
//                                            Bitmap xxdjBitmap = BitmapUtils.xxdjLabelView(this, goodsBean);
//                                            mJCAPI.startJob(50, 30, 0);
//                                            mJCAPI.startPage();
//                                            mJCAPI.drawBitmap(xxdjBitmap, 1, 2, 0, 0, 0);
//                                            mJCAPI.endPage();
//                                            mJCAPI.commitJob(1, 1, 10, mPrintCallback);
//                                            ivTest.setImageBitmap(xxdjBitmap);
                                        });
//                                    }
                                    break;
                                case 3:
                                    for (int i = 0; i < Integer.parseInt(goodsBean.getQuantity()); i++) {
//                                        for (int i = 0; i < 1; i++) {

                                        runOnUiThread(() -> {
                                            Bitmap xxdjBitmap = BitmapUtils.xxdjLabel1View(this, goodsBean);
//                                            mJCAPI.startJob(50, 30, 0);
//                                            mJCAPI.startPage();
//                                            mJCAPI.drawBitmap(xxdjBitmap, 1, 2, 0, 0, 0);
//                                            mJCAPI.endPage();
//                                            mJCAPI.commitJob(1, 1, 10, mPrintCallback);
//                                            ivTest.setImageBitmap(xxdjBitmap);

                                        });
                                    }
                                    break;
                                default:
                                    break;
                            }
                        });


                    }
                    executor.shutdown();

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