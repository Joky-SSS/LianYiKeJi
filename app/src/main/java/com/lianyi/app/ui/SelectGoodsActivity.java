package com.lianyi.app.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lianyi.app.R;
import com.lianyi.app.adapter.BuildingAdapter;
import com.lianyi.app.adapter.SelectGoodsAdapter;
import com.lianyi.app.adapter.SubjectTagAdapter;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.BuildingBean;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.GoodsBean;
import com.lianyi.app.model.GoodsListBean;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.RoomListBean;
import com.lianyi.app.model.SubjectBean;
import com.lianyi.app.model.TaskBean;
import com.lianyi.app.otto.BusData;
import com.lianyi.app.otto.OTTOTags;
import com.lianyi.app.otto.OttoBus;
import com.lianyi.app.utils.RoutePath;
import com.lianyi.app.utils.SpacesItemDecoration;
import com.rxjava.rxlife.RxLife;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.forward.androids.views.EasyAdapter;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.HttpStatusCodeException;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_BUTTON;

/**
 * @ClassName: NewGoodsActivity
 * @Description: 新增物品
 * @Author: Lee
 * @CreateDate: 2020/7/5 18:46
 */
@Route(path = RoutePath.PATH_SELECT_GOOD)
public class SelectGoodsActivity extends BaseActvity implements RadioGroup.OnCheckedChangeListener, OnItemClickListener, OnRefreshLoadMoreListener, EasyAdapter.OnSingleSelectListener, CommonTitleBar.OnTitleBarListener {
    @Autowired(name = Constant.ROOM_BEAN)
    RoomListBean.ListBean mListBean;
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;
    SubjectTagAdapter mSubjectTagAdapter;
    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.rv_subject_tag)
    RecyclerView rvSubjectTag;
    @BindView(R.id.rb_goods_none)
    RadioButton rbGoodsNone;
    @BindView(R.id.rb_goods_select)
    RadioButton rbGoodsSelect;
    @BindView(R.id.rg_goods_types)
    RadioGroup rgGoodsTypes;
    @BindView(R.id.et_search_view)
    AppCompatEditText etSearchView;
    @BindView(R.id.rv_select_goods)
    RecyclerView rvSelectGoods;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.bt_select)
    AppCompatButton btSelect;
    @BindView(R.id.tv_total_of)
    AppCompatTextView tvTotalOf;
    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;
    @BindView(R.id.rv_building_tag)
    RecyclerView rvBuildingTag;
    @BindView(R.id.room_type_layout)
    LinearLayout roomTypeLayout;
    @BindView(R.id.rb_goods_all)
    CheckBox rbGoodsAll;

    BuildingAdapter mBuildingAdapter;
    SelectGoodsAdapter mSelectGoodsAdapter;


    private String mSubjectId, mBuildingId;


    @Override
    public int getLayoutId() {
        return R.layout.select_goods_layout;
    }

    @Override
    public void initView() {
        titleBar.setListener(this);
        initLayoutManager(rvSubjectTag, 1, 10);
        mSubjectTagAdapter = new SubjectTagAdapter(this, null);
        mSubjectTagAdapter.setMaxSelectionCount(1);
        mSubjectTagAdapter.setMode(EasyAdapter.Mode.SINGLE_SELECT);
        rvSubjectTag.setAdapter(mSubjectTagAdapter);
        rgGoodsTypes.setOnCheckedChangeListener(this);
        mSubjectTagAdapter.setOnSingleSelectListener(this);


        initRecycleView(rvBuildingTag, 1, 10);
        mBuildingAdapter = new BuildingAdapter(this, null);
        mBuildingAdapter.setMaxSelectionCount(1);
        mBuildingAdapter.setMode(EasyAdapter.Mode.SINGLE_SELECT);
        rvBuildingTag.setAdapter(mBuildingAdapter);


        initRecycleView();
    }

    @Override
    public void initData() {
        loadGoodsData();

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


    public void isCheckTheAllButton(CheckBox checkBox) {
        for (GoodsBean datum : mSelectGoodsAdapter.getData()) {
            if (datum.isSelect() == false) {
                checkBox.setChecked(false);
                return;
            }
        }

    }

    private void loadGoodsData() {
        RxHttp.get(ApiService.API_ASSET_SUBJECT)
                .add("assetType", "INSTRUMENT")
                .asResponseList(SubjectBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(subjectList -> {
                    mSubjectTagAdapter.setData(subjectList);
                    mSubjectTagAdapter.select(0);
                    getGoodsTypes(true, queryStr, searchType, mSubjectId);
                }, throwable -> {
                    HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                    String errorMsg = exception.getResult(); //拿到msg字段
                    Response response = GsonUtils.fromJson(errorMsg, Response.class);
                    showToastFailure(response.getMessage());
                });

        mSubjectTagAdapter.setOnSingleSelectListener(position -> {
            mBuildingAdapter.setData(null);
            mSubjectId = mSubjectTagAdapter.getData().get(position).getId();

            RxHttp.get(ApiService.API_BUILDING_LIST)
                    .add("subjectId", mSubjectId)
                    .asResponseList(BuildingBean.class)
                    .as(RxLife.asOnMain(this))
                    .subscribe(buildingList -> {
                        mBuildingAdapter.setData(buildingList);
                        mBuildingAdapter.select(0);

                    }, throwable -> {

                    });
        });

        //获取房间列表
        mBuildingAdapter.setOnSingleSelectListener(position -> {
            mSelectGoodsAdapter.setList(null);
            mBuildingId = mBuildingAdapter.getData().get(position).getBuildingId();
            getGoodsTypes(true, queryStr, searchType, mSubjectId);

        });
    }

    @Override
    public boolean isRegisterOttO() {
        return true;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mSelectGoodsAdapter.setNewInstance(null);
        switch (checkedId) {
            case R.id.rb_goods_none:
                searchType = 1;
                loadGoodsData();
                btSelect.setText("选入");
                roomTypeLayout.setVisibility(View.GONE);
                rbGoodsAll.setVisibility(View.GONE);
                break;
            case R.id.rb_goods_select:
                searchType = 2;
                loadGoodsData();
                btSelect.setText("复制");
                roomTypeLayout.setVisibility(View.VISIBLE);
                rbGoodsAll.setVisibility(View.VISIBLE);

                break;
            case R.id.rb_goods_standard:
                searchType = 3;
                loadGoodsData();
                btSelect.setText("复制");
                roomTypeLayout.setVisibility(View.GONE);
                rbGoodsAll.setVisibility(View.GONE);

                break;
            default:
                break;
        }
    }

    /**
     * 查询物品
     *
     * @param isRefresh
     * @param query
     * @param type
     * @param subjectId
     */
    public void getGoodsTypes(boolean isRefresh, String query, int type, String subjectId) {
        mSelectGoodsAdapter.setType(type);
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("queryStr", query);
        bodyMap.put("sign", type);
        bodyMap.put("subjectId", subjectId);
        if (type != 3) {
            bodyMap.put("taskId", mTaskBean.getId());
        }
        if (type == 2) {
            bodyMap.put("buildingId", mBuildingId);
        }
        RxHttp.get(ApiService.API_GOODS_TYPES)
                .addAll(bodyMap)
                .asResponse(GoodsListBean.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(() -> {
                    hideLoading();
                    hideKeyboard(etSearchView);
                })//请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(goodsListBean -> {
                    if (isRefresh) {
                        refreshLayout.resetNoMoreData();
                        mSelectGoodsAdapter.setList(goodsListBean.getDataList().getList());
                        rvSelectGoods.scrollToPosition(0);
                        refreshLayout.finishRefresh();
                        rbGoodsAll.setChecked(false);
                    } else {
                        if (pageNo > goodsListBean.getDataList().getPageCount()) {
                            refreshLayout.finishRefreshWithNoMoreData();
                        } else {
                            mSelectGoodsAdapter.addData(goodsListBean.getDataList().getList());
                            refreshLayout.finishLoadMore();
                            rbGoodsAll.setChecked(false);
                        }
                    }

                    tvTotalOf.setText(String.format(SelectGoodsActivity.this.getResources().getString(R.string.str_total_of), goodsListBean.getQuantity(), goodsListBean.getAomunt()));


                }, throwable -> {

                });
    }


    /**
     * 选入物品
     *
     * @param ids
     * @param buildingId
     */
    public void selectGoods(String ids, String buildingId) {
        showLoading();
        RxHttp.postForm(ApiService.API_SELECT_GOODS)
                .add("ids", ids)
                .add("buildingId", buildingId)
                .add("taskId", mTaskBean.getId())
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading)
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    showToastSuccess("选入成功");
                    hideLoading();
                    OttoBus.getInstance().post(new BusData(OTTOTags.SELECT_GOODS_TAGS, ""));
                    finish();
                }, throwable -> {
                    hideLoading();

                });
    }


    /**
     * 复制物品
     *
     * @param goodsId
     */
    public void copyGoods(String goodsId, String buildingId) {
        RxHttp.get(ApiService.API_GOODS_COPY)
                .add("ids", goodsId)
                .add("buildingId", buildingId)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading)
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    showToastSuccess("复制成功");
                    OttoBus.getInstance().post(new BusData(OTTOTags.REFRESH_TAGS, ""));
                    finish();
                }, throwable -> {

                });
    }

    /**
     * 物品数据保存
     * 标准内的物品
     *
     * @param goodsBean
     */
    private void saveGoods(List<GoodsBean> goodsBean) {
        RxHttp.postJsonArray(ApiService.API_INSERT_GOODS)
                .addAll(goodsBean)
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    showToastSuccess("复制成功");
                    OttoBus.getInstance().post(new BusData(OTTOTags.REFRESH_TAGS, ""));
                }, throwable -> {


                });
    }


    /**
     * 初始化recycleview
     */
    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSelectGoods.setLayoutManager(linearLayoutManager);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(10);
        rvSelectGoods.addItemDecoration(spacesItemDecoration);
        mSelectGoodsAdapter = new SelectGoodsAdapter(null);
        rvSelectGoods.setAdapter(mSelectGoodsAdapter);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        mSelectGoodsAdapter.setOnItemClickListener(this);
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
    private void initLayoutManager(RecyclerView recyclerView, int spanCount, int space) {
        StaggeredGridLayoutManager mStatusLayoutManager = new StaggeredGridLayoutManager(spanCount,
                StaggeredGridLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(mStatusLayoutManager);
        SpacesItemDecoration mSpacesItemDecoration = new SpacesItemDecoration(space);
        recyclerView.addItemDecoration(mSpacesItemDecoration);
    }


    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        GoodsBean goodsBean = mSelectGoodsAdapter.getData().get(position);
        goodsBean.setSelect(!goodsBean.isSelect());
        mSelectGoodsAdapter.notifyItemChanged(position);

        isCheckTheAllButton(rbGoodsAll);
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getGoodsTypes(false, queryStr, searchType, mSubjectId);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        getGoodsTypes(true, queryStr, searchType, mSubjectId);

    }

    @OnClick({R.id.bt_select, R.id.iv_search, R.id.rb_goods_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_select:
                switch (searchType) {
                    case 1:
                        if (TextUtils.isEmpty(getSelectGoodsIds(mSelectGoodsAdapter))) {
                            showToastFailure("请选择需要选入的物品");
                        } else {
                            selectGoods(getSelectGoodsIds(mSelectGoodsAdapter), mListBean.getId());
                        }
                        break;
                    case 2:
                        if (getSelectGoods(mSelectGoodsAdapter).size() <= 0) {
                            showToastFailure("请选择需要复制的物品");
                            return;
                        }
                        copyGoods(getSelectGoodsIds(mSelectGoodsAdapter), mListBean.getId());
                        break;
                    case 3:
                        if (getSelectGoods(mSelectGoodsAdapter).size() <= 0) {
                            showToastFailure("请选择需要复制的物品");
                            return;
                        }
                        saveGoods(getSelectGoods(mSelectGoodsAdapter));
                        break;
                    default:
                        break;
                }
                break;

            case R.id.iv_search:
                queryStr = TextUtils.isEmpty(etSearchView.getText().toString()) ? "" : etSearchView.getText().toString();
                getGoodsTypes(true, queryStr, searchType, mSubjectId);
                break;

            case R.id.rb_goods_all:
                boolean checked = ((CheckBox) view).isChecked();
                selectAllGoods(checked);
                break;
            default:
                break;
        }

    }

    /**
     * 全选
     *
     * @param isSelectAll 是否全选
     */
    public void selectAllGoods(boolean isSelectAll) {
        for (GoodsBean datum : mSelectGoodsAdapter.getData()) {
            datum.setSelect(isSelectAll);
        }
        mSelectGoodsAdapter.notifyDataSetChanged();
    }


    /**
     * 获取选中的物品
     *
     * @param goodsAdapter
     * @return
     */
    public List<GoodsBean> getSelectGoods(SelectGoodsAdapter goodsAdapter) {
        List<GoodsBean> selectGoods = new ArrayList<>();
        for (GoodsBean datum : goodsAdapter.getData()) {
            if (datum.isSelect()) {
                datum.setNewMark("Y");
                datum.setBuildingId(mListBean.getId());
                datum.setTargetQuantity("1");
                datum.setTaskId(mTaskBean.getId());
                selectGoods.add(datum);
            }
        }
        return selectGoods;
    }


    /**
     * 获取选中的物品ID
     *
     * @param goodsAdapter
     * @return
     */
    public String getSelectGoodsIds(SelectGoodsAdapter goodsAdapter) {
        if (goodsAdapter.getData().size() <= 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (GoodsBean datum : goodsAdapter.getData()) {
            if (datum.isSelect()) {
                stringBuilder.append(datum.getId() + ",");
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public void onSelected(int position) {
        mSubjectId = mSubjectTagAdapter.getData().get(position).getId();
        getGoodsTypes(true, queryStr, searchType, mSubjectId);

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
}