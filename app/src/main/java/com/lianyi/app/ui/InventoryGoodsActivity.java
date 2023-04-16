package com.lianyi.app.ui;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_TEXT;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lianyi.app.R;
import com.lianyi.app.adapter.InventoryGoodListAdapter;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.event.BackResult;
import com.lianyi.app.event.GetRFIDThread;
import com.lianyi.app.event.RFIDManager;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.InventoryGoodBean;
import com.lianyi.app.model.InventoryGoodPage;
import com.lianyi.app.model.InventoryRoomListBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.otto.BusData;
import com.lianyi.app.otto.OTTOTags;
import com.lianyi.app.otto.OttoBus;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.TextWatcherAdapter;
import com.rxjava.rxlife.RxLife;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.squareup.otto.Subscribe;
import com.uhf.base.UHFManager;
import com.uhf.base.UHFModuleType;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.yanzhenjie.recyclerview.OnItemMenuClickListener;
import com.yanzhenjie.recyclerview.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.SwipeMenuItem;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;

import java.util.HashMap;
import java.util.HashSet;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.ParseException;

/**
 * RFID标签盘点物品列表
 */
@Route(path = RoutePath.PATH_INVENTORY_GOODS)
public class InventoryGoodsActivity extends BaseActvity implements CommonTitleBar.OnTitleBarListener, OnItemMenuClickListener, OnRefreshLoadMoreListener, OnItemClickListener, BackResult {
    @Autowired(name = Constant.ROOM_BEAN)
    InventoryRoomListBean.RoomBean mRoomBean;
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
    @BindView(R.id.tv_rfid)
    AppCompatTextView tvRFID;
    @BindView(R.id.et_search_view)
    AppCompatEditText etSearchView;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    private InventoryGoodListAdapter mGoodsAdapter;
    private boolean ifRequesetPermission = true;
    private final int requestPermissionCode = 10;
    private HashSet<String> rfidSet = new HashSet<>();
    private SoundPool soundPool;
    private int soundID;
    @Override
    public int getLayoutId() {
        return R.layout.activity_inventory_goods;
    }

    @Override
    public void initView() {
        titleBar.setListener(this);
        titleBar.getCenterTextView().setText(mRoomBean.getName());
        etSearchView.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                queryStr = s.toString();
            }
        });
        createSwipeMenu();
        mGoodsAdapter = new InventoryGoodListAdapter(null);
        mSwipeRecyclerView.setAdapter(mGoodsAdapter);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        mGoodsAdapter.setOnItemClickListener(this);
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
    public void initData() {

        getSearchGood(true, queryStr, mRoomBean.getId());
        if (Build.VERSION.SDK_INT > 22)
            requestPermission();
        else
            RFIDManager.init();
        GetRFIDThread.getInstance().setBackResult(this);
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundID = soundPool.load(this, R.raw.beep, 1);
    }

    @Override
    public boolean isRegisterOttO() {
        return true;
    }

    @Subscribe()
    public void getSelectGoods(BusData busData) {
        switch (busData.getTag()) {
            case OTTOTags.SELECT_GOODS_TAGS:
                getSearchGood(true, queryStr, mRoomBean.getId());
                break;
            case OTTOTags.REFRESH_TAGS:
                String tags = (String) busData.getData();
                if (!tags.equals("this")) {
                    getSearchGood(true, queryStr, mRoomBean.getId());
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
        HashMap<String, Integer> page = new HashMap<>();
        page.put("pageNo", pageNo);
        page.put("pageSize", pageSize);
        RxHttp.postJson(ApiService.API_RFID_GOODS_LIST)
                .add("queryStr", query)
                .add("buildingId", buildingId)
                .add("taskId", mTaskBean.getId())
                .add("page", page)
                .asResponse(InventoryGoodPage.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(() -> {
                    hideLoading();
                    hideKeyboard(etSearchView);
                })//请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(goodsPage -> {
                    if (isRefresh) {
                        mGoodsAdapter.setList(goodsPage.getPage().getList());
                        mSwipeRecyclerView.scrollToPosition(0);
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();
                    } else {
                        if (pageNo > goodsPage.getPage().getPageCount()) {
                            refreshLayout.finishRefreshWithNoMoreData();
                        } else {
                            mGoodsAdapter.addData(goodsPage.getPage().getList());
                            refreshLayout.finishLoadMore();
                        }
                    }
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        InventoryGoodBean goodBean = mGoodsAdapter.getItem(position);
        if ("NEW".equalsIgnoreCase(goodBean.getNewOldSign())) {
            //新增的物品
            ARouter.getInstance().build(RoutePath.PATH_INVENTORY_NEW_GOOD)
                    .withString(Constant.GOODS_ID, goodBean.getId())
                    .withParcelable(Constant.ROOM_BEAN, mRoomBean)
                    .withParcelable(Constant.TASK, mTaskBean)
                    .navigation();
        } else {
            ARouter.getInstance().build(RoutePath.PATH_INVENTORY_GOODS_DETAILS)
                    .withString(Constant.GOODS_ID, goodBean.getId())
                    .withParcelable(Constant.ROOM_BEAN, mRoomBean)
                    .withParcelable(Constant.TASK, mTaskBean)
                    .navigation();
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getSearchGood(false, queryStr, mRoomBean.getId());
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        getSearchGood(true, queryStr, mRoomBean.getId());
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
    public void onItemClick(SwipeMenuBridge menuBridge, int adapterPosition) {
        menuBridge.closeMenu();
        int menuPosition = menuBridge.getPosition();
        String id = mGoodsAdapter.getData().get(adapterPosition).getId();
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

    @OnClick({R.id.tv_add_goods, R.id.tv_new_goods, R.id.tv_scan, R.id.iv_search, R.id.tv_rfid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_add_goods:
                ARouter.getInstance().build(RoutePath.PATH_INVENTORY_SELECT_GOOD)
                        .withParcelable(Constant.ROOM_BEAN, mRoomBean)
                        .withParcelable(Constant.TASK, mTaskBean)
                        .navigation();
                break;
            case R.id.tv_new_goods:
                ARouter.getInstance().build(RoutePath.PATH_INVENTORY_NEW_GOOD)
                        .withParcelable(Constant.ROOM_BEAN, mRoomBean)
                        .withParcelable(Constant.TASK, mTaskBean)
                        .navigation();
                break;
            case R.id.tv_scan:
                ARouter.getInstance().build(RoutePath.PATH_QRCODE_SCAN)
                        .withParcelable(Constant.ROOM_BEAN, mRoomBean)
                        .withParcelable(Constant.TASK, mTaskBean)
                        .navigation();
                break;
            case R.id.iv_search:
                queryStr = TextUtils.isEmpty(etSearchView.getText().toString()) ? "" : etSearchView.getText().toString();
                getSearchGood(true, queryStr, mRoomBean.getId());
                break;
            case R.id.tv_rfid:

                startOrStopRFID();

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
        RxHttp.get(ApiService.API_RFID_GOODS_COPY, goodsId)
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
        RxHttp.get(ApiService.API_RFID_GOODS_DELETE, goodsId)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading("正在删除..."))  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading)
                .as(RxLife.asOnMain(this))
                .subscribe(response -> {
                    showToastSuccess("删除成功");
                    mGoodsAdapter.removeAt(adapterPosition);
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!ifRequesetPermission) {
            recycleResource();
        }
        GetRFIDThread.getInstance().setBackResult(null);
    }

    /**
     * 向服务器匹配读取到的RFID标签
     * @param rfidArray
     */
    private void matchRFID(String[] rfidArray) {
        RxHttp.postJson(ApiService.API_RFID_MATCH)
                .add("rfidCodes", rfidArray)
                .add("buildingId", mRoomBean.getId())
                .add("taskId", mTaskBean.getId())
                .asResponse(String.class)
                .as(RxLife.asOnMain(this))
                .subscribe(response -> {
                    pageNo = 1;
                    getSearchGood(true, queryStr, mRoomBean.getId());
                    OttoBus.getInstance().post(new BusData(OTTOTags.REFRESH_TAGS, "this"));
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }

    /**
     * 枪把按钮监听
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_F8 || keyCode == KeyEvent.KEYCODE_F4 || keyCode == KeyEvent.KEYCODE_BUTTON_4 || keyCode == KeyEvent.KEYCODE_PROG_RED /*|| keyCode ==KeyEvent.KEYCODE_F9 || keyCode == KeyEvent.KEYCODE_F10*/) {
            //枪把按钮按下，开始或停止读取RFID标签
            startOrStopRFID();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void startOrStopRFID() {
        boolean flag = !GetRFIDThread.getInstance().isIfPostMsg();
        UHFManager manager = RFIDManager.getUhfMangerImpl();
        if (manager == null) return;
        if (flag) {
            //开始读取RFID标签
            rfidSet.clear();
            if (UHFModuleType.SLR_MODULE == UHFManager.getType() && RFIDManager.if5100Module) {
                manager.slrInventoryModeSet(0);
            }
            if ((UHFModuleType.SLR_MODULE == UHFManager.getType()) && RFIDManager.if7100Module) {
                manager.slrInventoryModeSet(4);
            }
            GetRFIDThread.getInstance().setIfPostMsg(true);
            //播放声音
            soundPool.play(soundID, 1, 1, 0, 1, 1);
            manager.startInventoryTag();
        } else {
            //停止读取RFID标签
            manager.stopInventory();
            GetRFIDThread.getInstance().setIfPostMsg(false);
            String[] rfidArray = new String[rfidSet.size()];
            //调用接口匹配标签
            matchRFID(rfidSet.toArray(rfidArray));
            rfidSet.clear();
        }

    }

    /**
     * RFID标签读取回调
     * @param tagData
     */
    @Override
    public void postResult(String[] tagData) {
        if (tagData == null) return;
        //获取TID
        String tid = tagData[0];
        String usr = tagData[0];
        String rfu = tagData[0];
        String epc = tagData[1];
        if (!TextUtils.isEmpty(epc)) rfidSet.add(epc);
    }

    @Override
    public void postInventoryRate(long rate) {

    }


    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, requestPermissionCode);
            } else {
                ifRequesetPermission = false;
                RFIDManager.init();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == requestPermissionCode) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                recycleResource();
            } else {
                ifRequesetPermission = false;
                RFIDManager.init();
            }
        }
    }

    /**
     * 回收资源
     */
    private void recycleResource() {
        GetRFIDThread.getInstance().setIfPostMsg(false);
        UHFManager manager = RFIDManager.getUhfMangerImpl();
        if (manager != null) {
            manager.stopInventory();
            manager.powerOff();
        }
        if (soundPool!=null){
            soundPool.release();
        }
    }

}