package com.lianyi.app.ui;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.tabs.TabLayout;
import com.lianyi.app.model.CabinetBean;
import com.lxj.xpopup.XPopup;
import com.lianyi.app.R;
import com.lianyi.app.adapter.GoodsListAdapter;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.GoodsBean;
import com.lianyi.app.model.GoodsListBean;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.RoomListBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.otto.BusData;
import com.lianyi.app.otto.OTTOTags;
import com.lianyi.app.otto.OttoBus;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.SpacesItemDecoration;
import com.lianyi.app.weight.CreateCabinetPopup;
import com.rxjava.rxlife.RxLife;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.squareup.otto.Subscribe;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_BUTTON;
import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_RIGHT_TEXT;


/**
 * @ClassName: GoodsActivity
 * @Description: 物品列表
 * @Author: Lee
 * @CreateDate: 2020/6/29 10:39
 */
@Route(path = RoutePath.PATH_GOOD)
public class GoodsActivity extends BaseActvity implements CommonTitleBar.OnTitleBarListener, OnItemMenuClickListener, GoodsListAdapter.OnChangeValueListener, OnRefreshLoadMoreListener, OnItemClickListener {

    @Autowired(name = Constant.ROOM_BEAN)
    RoomListBean.ListBean mListBean;
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;
    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.swipe_recyclerview)
    SwipeRecyclerView mSwipeRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_add_goods)
    AppCompatTextView tvAddGoods;
    @BindView(R.id.tv_new_goods)
    AppCompatTextView tvNewGoods;
    @BindView(R.id.bt_save)
    Button btSave;
    @BindView(R.id.bt_label_print)
    Button btLabelPrint;
    @BindView(R.id.tv_total_of)
    AppCompatTextView tvTotalOf;
    GoodsListAdapter mGoodsListAdapter;
    @BindView(R.id.et_search_view)
    AppCompatEditText etSearchView;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @BindView(R.id.cabinetTabLayout)
    TabLayout cabinetTabLayout;
    private List<CabinetBean> cabinetBeanList = new ArrayList<>();
    private CabinetBean selectCabinet;

    @Override
    public int getLayoutId() {
        return R.layout.good_list_layout;
    }

    @Override
    public void initView() {
        titleBar.setListener(this);
        titleBar.getCenterTextView().setText(mListBean.getName());
        initRecycleView();
        initTabLayout();
    }

    @Override
    public void initData() {
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
        getCabinetList(mListBean.getId());
    }

    @Override
    public boolean isRegisterOttO() {
        return true;
    }


    @Subscribe()
    public void getSelectGoods(BusData busData) {
        switch (busData.getTag()) {
            case OTTOTags.SELECT_GOODS_TAGS:
                getSearchGood(true, queryStr, mListBean.getId());
                break;
            case OTTOTags.REFRESH_TAGS:
                String tags = (String) busData.getData();
                if (!tags.equals("this")) {
                    refreshLayout.autoRefresh();
                }
                break;
            default:
                break;
        }

    }

    /**
     * 搜索物品
     *
     * @param isRefresh  是否是刷新
     * @param query
     * @param buildingId
     */
    private void getSearchGood(boolean isRefresh, String query, String buildingId) {
        RxHttp.get(ApiService.API_GOODS_LIST)
                .add("queryStr", query)
                .add("buildingId", buildingId)
                .add("taskId", mTaskBean.getId())
                .add("pageNo", pageNo)
                .add("pageSize", pageSize)
                .add("cabinetId", selectCabinet.getId())
                .asResponse(GoodsListBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(() -> {
                    hideLoading();
                    hideKeyboard(etSearchView);
                })//请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(goodsListBean -> {
                    if (isRefresh) {
                        mGoodsListAdapter.setList(goodsListBean.getDataList().getList());
                        mSwipeRecyclerView.scrollToPosition(0);
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();
                    } else {
                        if (pageNo > goodsListBean.getDataList().getPageCount()) {
                            refreshLayout.finishRefreshWithNoMoreData();
                        } else {
                            mGoodsListAdapter.addData(goodsListBean.getDataList().getList());
                            refreshLayout.finishLoadMore();
                        }
                    }
//                    totalCount();
                    tvTotalOf.setText(String.format(GoodsActivity.this.getResources().getString(R.string.str_total_of), goodsListBean.getQuantity(), goodsListBean.getAomunt()));
                }, throwable -> {
                });
    }

    /**
     * 初始化recycleview
     */
    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        mSwipeRecyclerView.setLayoutManager(linearLayoutManager);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(10);
        mSwipeRecyclerView.addItemDecoration(spacesItemDecoration);
        createSwipeMenu();
        mGoodsListAdapter = new GoodsListAdapter(null);
        mSwipeRecyclerView.setAdapter(mGoodsListAdapter);
        mGoodsListAdapter.setOnChangeValueListener(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        mGoodsListAdapter.setOnItemClickListener(this);
    }

    private void initTabLayout() {
        cabinetTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = cabinetTabLayout.getSelectedTabPosition();
                selectCabinet = cabinetBeanList.get(position);
                refreshLayout.autoRefresh();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        setTabData();
    }

    private void setTabData() {
        cabinetTabLayout.removeAllTabs();
        for (CabinetBean cabinet : cabinetBeanList) {
            TabLayout.Tab tab = cabinetTabLayout.newTab();
            tab.setText(cabinet.getCabinetName());
            cabinetTabLayout.addTab(tab);
        }
    }

    /**
     * 创建菜单 要在setAdapter之前设置
     */
    private void createSwipeMenu() {
        SwipeMenuCreator mSwipeMenuCreator = (leftMenu, rightMenu, position) -> {
            SwipeMenuItem copyItem = new SwipeMenuItem(this);
            copyItem.setText("复制");
            copyItem.setBackgroundColor(Color.parseColor("#009AFF"));
            copyItem.setTextColor(Color.rgb(255, 255, 255));
            copyItem.setWidth(150);
            copyItem.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            SwipeMenuItem deleteItem = new SwipeMenuItem(this);
            deleteItem.setText("删除");
            deleteItem.setWidth(150);
            deleteItem.setTextColor(Color.rgb(255, 255, 255));
            deleteItem.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            deleteItem.setBackgroundColor(Color.parseColor("#FF0000"));
            rightMenu.addMenuItem(copyItem);
            rightMenu.addMenuItem(deleteItem);
        };
        // 设置监听器。
        mSwipeRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mSwipeRecyclerView.setOnItemMenuClickListener(this);

    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case ACTION_LEFT_BUTTON:
                finish();
                break;
            case ACTION_RIGHT_TEXT:
                getCabinetCode(mListBean.getId());

                break;

            default:
                break;
        }
    }

    /**
     * 获取柜子编码
     *
     * @param buildingId
     */
    private void getCabinetList(String buildingId) {
        RxHttp.get(ApiService.API_GET_CABINET_LIST)
                .add("buildingId", buildingId)
                .asList(CabinetBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(cabinetList -> {
                    int currentTabIndex = cabinetTabLayout.getSelectedTabPosition();
                    cabinetBeanList.clear();
                    cabinetBeanList.addAll(cabinetList);
                    setTabData();
                    if (currentTabIndex != 0) {
                        cabinetTabLayout.selectTab(cabinetTabLayout.getTabAt(0));
                    }
                }, throwable -> {
                    HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                    String errorMsg = exception.getResult(); //拿到msg字段
                    Response response = GsonUtils.fromJson(errorMsg, Response.class);
                    showToastFailure(response.getMessage());
                });
    }

    /**
     * 获取柜子编码
     *
     * @param buildingId
     */
    private void getCabinetCode(String buildingId) {
        RxHttp.get(ApiService.API_CABINET_CODE)
                .add("buildingId", buildingId)
                .asString()
                .as(RxLife.asOnMain(this))
                .subscribe(cabinetCode -> {
                    new XPopup.Builder(this).asCustom(new CreateCabinetPopup(this, mListBean).setOnCreateCabinetListener(cabinetName -> {
                        RxHttp.postJson(ApiService.API_CREATE_CABINET)
                                .add("code", cabinetCode.replace("\"", ""))
                                .add("name", cabinetName)
                                .add("buildingId", mListBean.getId())
                                .add("buildingName", mListBean.getName())
                                .asString()
                                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                                .as(RxLife.asOnMain(this))
                                .subscribe(cabinetBean -> {
                                    Response response = GsonUtils.fromJson(cabinetBean, Response.class);
                                    if (response.getCode() == 0) {
                                        showToastSuccess("柜子创建成功");
                                    } else {
                                        showToastFailure(response.getMessage());
                                    }
                                }, throwable -> {

                                });

                    })).show();
                }, throwable -> {
                    HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                    String errorMsg = exception.getResult(); //拿到msg字段
                    Response response = GsonUtils.fromJson(errorMsg, Response.class);
                    showToastFailure(response.getMessage());

                });
    }


    @OnClick({R.id.tv_add_goods, R.id.tv_new_goods, R.id.bt_save, R.id.bt_label_print, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_goods:
                ARouter.getInstance().build(RoutePath.PATH_SELECT_GOOD)
                        .withParcelable(Constant.ROOM_BEAN, mListBean)
                        .withParcelable(Constant.TASK, mTaskBean)
                        .navigation();
                break;
            case R.id.tv_new_goods:
                ARouter.getInstance().build(RoutePath.PATH_NEW_GOOD)
                        .withParcelable(Constant.ROOM_BEAN, mListBean)
                        .withParcelable(Constant.TASK, mTaskBean)
                        .withBoolean(Constant.INSERT, true)
                        .navigation();
                break;
            case R.id.bt_save:
                saveGoodsList(mGoodsListAdapter.getData());

                break;
            case R.id.bt_label_print:
                ARouter.getInstance().build(RoutePath.PATH_LABEL_PRINT)
                        .withParcelable(Constant.ROOM_BEAN, mListBean)
                        .withParcelable(Constant.TASK, mTaskBean)
                        .navigation();
                break;
            case R.id.iv_search:
                queryStr = TextUtils.isEmpty(etSearchView.getText().toString()) ? "" : etSearchView.getText().toString();
                getSearchGood(true, queryStr, mListBean.getId());
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
        // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
        menuBridge.closeMenu();
        // 菜单在Item中的Position：
        int menuPosition = menuBridge.getPosition();
        String id = mGoodsListAdapter.getData().get(adapterPosition).getId();
        switch (menuPosition) {
            case 0:

                copyGoods(TextUtils.isEmpty(id) ? "" : id, adapterPosition);
                break;
            case 1:
                deleteGoods(TextUtils.isEmpty(id) ? "" : id, adapterPosition);
                break;
            default:
                break;
        }
    }

    /**
     * 复制物品
     *
     * @param goodsId
     * @param adapterPosition
     */
    public void copyGoods(String goodsId, int adapterPosition) {
        RxHttp.get(ApiService.API_GOODS_COPY)
                .add("ids", goodsId)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading)
                .as(RxLife.asOnMain(this))
                .subscribe(response -> {
                    showToastSuccess("复制成功");
                    refreshLayout.autoRefresh();
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }

    /**
     * 删除物品
     *
     * @param goodsId
     * @param adapterPosition
     */
    public void deleteGoods(String goodsId, int adapterPosition) {
        RxHttp.get(ApiService.API_GOODS_DELETE)
                .add("assetId", goodsId)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading("正在删除..."))  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading)
                .as(RxLife.asOnMain(this))
                .subscribe(response -> {
                    showToastSuccess("删除成功");
                    mGoodsListAdapter.removeAt(adapterPosition);
//                    totalCount();
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());

                });
    }

    /**
     * 保存数据
     *
     * @param goodsBeans
     */
    public void saveGoodsList(List<GoodsBean> goodsBeans) {
        RxHttp.postJsonArray(ApiService.API_GOODS_LIST_SAVE)
                .addAll(goodsBeans)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading("保存中..."))  //请求开始，当前在主线程回调
                .doFinally(() -> {
                    hideLoading();
                    refreshLayout.finishRefresh();
                }) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(response -> {
                    showToastSuccess("保存成功");
                    OttoBus.getInstance().post(new BusData(OTTOTags.REFRESH_TAGS, "this"));

                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    ToastUtils.showLong(exception.getMessage());

                });
    }


    @Override
    public void onChangeValue() {
//        totalCount();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getSearchGood(false, queryStr, mListBean.getId());

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        getSearchGood(true, queryStr, mListBean.getId());

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        GoodsBean goodsBean = mGoodsListAdapter.getData().get(position);
        if (goodsBean.getNewMark().equalsIgnoreCase("Y")) { //是新增的
            ARouter.getInstance().build(RoutePath.PATH_NEW_GOOD)
                    .withString(Constant.GOODS_ID, goodsBean.getId())
                    .withParcelable(Constant.ROOM_BEAN, mListBean)
                    .withParcelable(Constant.TASK, mTaskBean)
                    .withBoolean(Constant.INSERT, false)
                    .navigation();
        } else {
            ARouter.getInstance().build(RoutePath.PATH_GOOD_DETAILS)
                    .withString(Constant.GOODS_ID, goodsBean.getId())
                    .withParcelable(Constant.ROOM_BEAN, mListBean)
                    .withParcelable(Constant.TASK, mTaskBean)
                    .navigation();
        }


    }

//    /**
//     * 计算商品的总数量和总价格
//     *
//     * @return
//     */
//    private void totalCount() {
//        //总数
//        BigDecimal totalInvest = BigDecimal.ZERO;
//        totalInvest.setScale(2, BigDecimal.ROUND_HALF_UP);
//        //总价格
//        BigDecimal totalInvestMoney = BigDecimal.ZERO;
//        totalInvestMoney.setScale(2, BigDecimal.ROUND_HALF_UP);
//        for (GoodsBean datum : mGoodsListAdapter.getData()) {
//            totalInvest = totalInvest.add(new BigDecimal(datum.getTargetQuantity()));
//            totalInvestMoney = totalInvestMoney.add(new BigDecimal(datum.getTargetQuantity()).multiply(new BigDecimal(datum.getPrice())));
//        }
//        tvTotalOf.setText(String.format(GoodsActivity.this.getResources().getString(R.string.str_total_of), totalInvest.stripTrailingZeros().toPlainString(), totalInvestMoney.stripTrailingZeros().toPlainString()));
//    }

}