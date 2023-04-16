package com.lianyi.app.ui;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_TEXT;
import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_RIGHT_TEXT;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lianyi.app.R;
import com.lianyi.app.adapter.InventoryRoomListAdapter;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.InventoryRoomListBean;
import com.lianyi.app.model.RoomListBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.otto.BusData;
import com.lianyi.app.otto.OTTOTags;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.TextWatcherAdapter;
import com.lxj.xpopup.XPopup;
import com.rxjava.rxlife.RxLife;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.squareup.otto.Subscribe;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.ParseException;

/**
 * RFID盘点 房间列表
 */
@Route(path = RoutePath.PATH_INVENTORY_ROOM)
public class InventoryRoomActivity extends BaseActvity implements CommonTitleBar.OnTitleBarListener, OnItemClickListener, OnRefreshLoadMoreListener {
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;

    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.rv_room_list)
    RecyclerView rvRoomList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.et_search_view)
    AppCompatEditText etSearchView;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;

    InventoryRoomListAdapter mRoomListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_inventory_room;
    }

    @Override
    public void initView() {
        titleBar.setListener(this);
        mRoomListAdapter = new InventoryRoomListAdapter(null);
        rvRoomList.setAdapter(mRoomListAdapter);
        mRoomListAdapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);

        etSearchView.setHint("请输入房间名称");

        etSearchView.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                queryStr = s.toString();
            }
        });
    }

    @Override
    public void initData() {
        refreshLayout.autoRefresh();
    }

    @Override
    public boolean isRegisterOttO() {
        return true;
    }

    @Subscribe()
    public void getSelectGoods(BusData busData) {
        switch (busData.getTag()) {
            case OTTOTags.SELECT_GOODS_TAGS:
            case OTTOTags.REFRESH_TAGS:
                getRoomList(true, queryStr);
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        InventoryRoomListBean.RoomBean roomBean = mRoomListAdapter.getItem(position);
        ARouter.getInstance().build(RoutePath.PATH_INVENTORY_GOODS).withParcelable(Constant.TASK,mTaskBean)
                .withParcelable(Constant.ROOM_BEAN,roomBean).navigation();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getRoomList(false, queryStr);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        getRoomList(true, queryStr);
    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case ACTION_LEFT_TEXT:
                finish();
                break;
            case ACTION_RIGHT_TEXT:
                new XPopup.Builder(this).asConfirm("提示", "您确定要结束当前盘点任务？", this::finishInventory).show();
                break;
            default:
                break;
        }
    }

    /**
     * 结束盘点
     */
    private void finishInventory() {
        RxHttp.postForm(ApiService.API_RFID_INVENTORY_FINISH, mTaskBean.getId())
                .asResponse(String.class)
                .timeout(60 * 3, TimeUnit.SECONDS)//设置总超时时间为180s
                .doOnSubscribe(disposable -> showLoading("正在完成盘点..."))  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading)
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    hideLoading();
                    showToastSuccess("完成盘点成功");
                    finish();
                }, throwable -> {
                    hideLoading();
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        queryStr = TextUtils.isEmpty(etSearchView.getText().toString()) ? "" : etSearchView.getText().toString();
        getRoomList(true, queryStr);
    }

    /**
     * 获取房间列表数据
     * @param isRefresh
     * @param search
     */
    private void getRoomList(boolean isRefresh, String search) {
        RxHttp.get(ApiService.API_RFID_ROOM_LIST)
                .add("keyWords", search)
                .add("pageNo", pageNo)
                .add("pageSize", pageSize)
                .add("taskId", mTaskBean.getId())
                .asResponseList(InventoryRoomListBean.RoomBean.class)
                .doFinally(() -> hideKeyboard(etSearchView))
                .as(RxLife.asOnMain(this))
                .subscribe(roomListBean -> {
                        refreshLayout.finishRefresh();
                        refreshLayout.setNoMoreData(true);
                        mRoomListAdapter.setList(roomListBean);
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }
}