package com.lianyi.app.ui;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lianyi.app.R;
import com.lianyi.app.adapter.RoomListAdapter;
import com.lianyi.app.adapter.RoomTypeAdapter;
import com.lianyi.app.adapter.SubjectTagAdapter;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.RoomListBean;
import com.lianyi.app.model.RoomTypeBean;
import com.lianyi.app.model.SubjectBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.otto.BusData;
import com.lianyi.app.otto.OTTOTags;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.SpacesItemDecoration;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.rxjava.rxlife.RxLife;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.squareup.otto.Subscribe;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.forward.androids.views.EasyAdapter;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.HttpStatusCodeException;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_BUTTON;
import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_RIGHT_TEXT;

/**
 * @ClassName: RoomActivity
 * @Description: 房间
 * @Author: Lee
 * @CreateDate: 2020/6/28 13:20
 */
@Route(path = RoutePath.PATH_ROOM)
public class RoomActivity extends BaseActvity implements CommonTitleBar.OnTitleBarListener, OnItemClickListener, OnRefreshLoadMoreListener {
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;
    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.rv_room_type_tag)
    RecyclerView rvRoomTypeTag;
    @BindView(R.id.rv_subject_tag)
    RecyclerView rvSubjectTag;
    @BindView(R.id.rv_room_list)
    RecyclerView rvRoomList;
    @BindView(R.id.et_search_view)
    AppCompatEditText etSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    SubjectTagAdapter mSubjectTagAdapter;
    RoomTypeAdapter mRoomTypeAdapter;
    RoomListAdapter mRoomListAdapter;

    String roomType, subjectId;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;

    @Override
    public int getLayoutId() {
        return R.layout.room_layout;
    }

    @Override
    public void initView() {
        titleBar.setListener(this);
        initRecycleView(rvSubjectTag, 1, 10);
        mSubjectTagAdapter = new SubjectTagAdapter(this, null);
        mSubjectTagAdapter.setMaxSelectionCount(1);
        mSubjectTagAdapter.setMode(EasyAdapter.Mode.SINGLE_SELECT);
        rvSubjectTag.setAdapter(mSubjectTagAdapter);

        initRecycleView(rvRoomTypeTag, 1, 10);
        mRoomTypeAdapter = new RoomTypeAdapter(this, null);
        mRoomTypeAdapter.setMaxSelectionCount(1);
        mRoomTypeAdapter.setMode(EasyAdapter.Mode.SINGLE_SELECT);
        rvRoomTypeTag.setAdapter(mRoomTypeAdapter);

        initGridRecycleView(rvRoomList, 3, 5);
        mRoomListAdapter = new RoomListAdapter(null);
        rvRoomList.setAdapter(mRoomListAdapter);

        mRoomListAdapter.setOnItemClickListener(this);
        refreshLayout.setOnRefreshLoadMoreListener(this);

        etSearchView.setHint("请输入房间名称");

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

    @Override
    public void initData() {
        getSubjectDatas();
    }

    @Override
    public boolean isRegisterOttO() {
        return true;
    }


    @Subscribe()
    public void getSelectGoods(BusData busData) {
        switch (busData.getTag()) {
            case OTTOTags.REFRESH_TAGS:
                refreshLayout.autoRefresh();
                break;
            default:
                break;
        }

    }


    /**
     * 获取科目
     */
    private void getSubjectDatas() {
        RxHttp.get(ApiService.API_ALL_SUBJECT)
                .asResponseList(SubjectBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(subjectList -> {
                    mSubjectTagAdapter.setData(subjectList);
                    mSubjectTagAdapter.select(0);
                }, throwable -> {
                    HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                    String errorMsg = exception.getResult(); //拿到msg字段
                    Response response = GsonUtils.fromJson(errorMsg, Response.class);
                    showToastFailure(response.getMessage());
                });

        mSubjectTagAdapter.setOnSingleSelectListener(position -> {
            mRoomTypeAdapter.setData(null);
            subjectId = mSubjectTagAdapter.getData().get(position).getId();
            RxHttp.get(ApiService.API_CATEGORY)
                    .add("subjectId", subjectId)
                    .asResponseList(RoomTypeBean.class)
                    .as(RxLife.asOnMain(this))
                    .subscribe(roomTypeBeans -> {
                        if (roomTypeBeans.size() <= 0) {
                            roomType = "";
                        }
                        mRoomTypeAdapter.setData(roomTypeBeans);
                        mRoomTypeAdapter.select(0);

                    }, throwable -> {

                    });
        });

        //获取房间列表
        mRoomTypeAdapter.setOnSingleSelectListener(position -> {
            mRoomListAdapter.setList(null);
            roomType = mRoomTypeAdapter.getData().get(position).getId();
            getRoomList(true, queryStr, roomType, subjectId);
        });
    }

    /**
     * @param roomType
     * @param subjectId
     */
    private void getRoomList(boolean isRefresh, String search, String roomType, String subjectId) {

        RxHttp.get(ApiService.API_ROOM_LIST)
                .add("name", search)
                .add("subjectId", subjectId)
                .add("categoryId", roomType)
                .add("pageNo", pageNo)
                .add("pageSize", pageSize)
                .add("taskId", mTaskBean.getId())
                .asResponse(RoomListBean.class)
                .doFinally(() -> hideKeyboard(etSearchView))
                .as(RxLife.asOnMain(this))
                .subscribe(roomListBean -> {
                    if (isRefresh) {
                        refreshLayout.finishRefresh();
                        refreshLayout.resetNoMoreData();
                        mRoomListAdapter.setList(roomListBean.getList());
                    } else {
                        if (pageNo > roomListBean.getPageCount()) {
                            refreshLayout.finishLoadMoreWithNoMoreData();
                        } else {
                            mRoomListAdapter.addData(roomListBean.getList());
                            refreshLayout.finishLoadMore();
                        }

                    }
                }, throwable -> {

                });
    }

    /**
     * 完成并入库
     *
     * @param taskId
     */
    private void finishTask(String taskId) {
        RxHttp.postForm(ApiService.API_FINISH_TASK, taskId)
                .asResponse(String.class)
                .timeout(60 * 3, TimeUnit.SECONDS)//设置总超时时间为180s
                .doOnSubscribe(disposable -> showLoading("正在入库..."))  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading)
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    hideLoading();
                    showToastSuccess("入库成功");
                    finish();
                }, throwable -> {
                    hideLoading();
                    showToastSuccess("入库失败");
                });
    }


    /**
     * @param recyclerView
     * @param spanCount
     * @param space
     */
    private void initRecycleView(RecyclerView recyclerView, int spanCount, int space) {
        StaggeredGridLayoutManager mStatusLayoutManager = new StaggeredGridLayoutManager(spanCount,
                StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mStatusLayoutManager);
        SpacesItemDecoration mSpacesItemDecoration = new SpacesItemDecoration(space);
        recyclerView.addItemDecoration(mSpacesItemDecoration);
    }


    /**
     * @param recyclerView
     * @param spanCount
     * @param space
     */
    private void initGridRecycleView(RecyclerView recyclerView, int spanCount, int space) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);
        SpacesItemDecoration mSpacesItemDecoration = new SpacesItemDecoration(space);
        recyclerView.addItemDecoration(mSpacesItemDecoration);
    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case ACTION_LEFT_BUTTON:
                finish();
                break;
            case ACTION_RIGHT_TEXT:
                new XPopup.Builder(this).asConfirm("提示", "确定完成并入库吗？", () -> {
                    finishTask(mTaskBean.getId());
                }).show();

                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        RoomListBean.ListBean listBean = mRoomListAdapter.getData().get(position);
        ARouter.getInstance().build(RoutePath.PATH_GOOD)
                .withParcelable(Constant.ROOM_BEAN, listBean)
                .withParcelable(Constant.TASK, mTaskBean)
                .navigation();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getRoomList(false, queryStr, roomType, subjectId);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        getRoomList(true, queryStr, roomType, subjectId);
    }

    @OnClick(R.id.iv_search)
    public void onViewClicked() {
        queryStr = TextUtils.isEmpty(etSearchView.getText().toString()) ? "" : etSearchView.getText().toString();

        getRoomList(true, queryStr, roomType, subjectId);
    }

}