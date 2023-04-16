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
import com.blankj.utilcode.util.GsonUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.lianyi.app.R;
import com.lianyi.app.adapter.InventorySelectGoodsAdapter;
import com.lianyi.app.adapter.InventorySubjectTagAdapter;
import com.lianyi.app.api.ApiService;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.Constant;
import com.lianyi.app.model.InventoryGoodBean;
import com.lianyi.app.model.InventoryGoodPage;
import com.lianyi.app.model.InventoryRoomListBean;
import com.lianyi.app.model.Response;
import com.lianyi.app.model.SubjectBean;
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
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.forward.androids.views.EasyAdapter;
import rxhttp.RxHttp;
import rxhttp.wrapper.exception.HttpStatusCodeException;
import rxhttp.wrapper.exception.ParseException;

@Route(path = RoutePath.PATH_INVENTORY_SELECT_GOOD)
public class InventorySelectGoodsActivity extends BaseActvity implements OnItemClickListener, OnRefreshLoadMoreListener, EasyAdapter.OnSingleSelectListener, CommonTitleBar.OnTitleBarListener {
    @Autowired(name = Constant.ROOM_BEAN)
    InventoryRoomListBean.RoomBean mRoomBean;
    @Autowired(name = Constant.TASK)
    TaskBean mTaskBean;

    @BindView(R.id.title_bar)
    CommonTitleBar titleBar;
    @BindView(R.id.rv_subject_tag)
    RecyclerView rvSubjectTag;

    @BindView(R.id.et_search_view)
    AppCompatEditText etSearchView;
    @BindView(R.id.rv_select_goods)
    RecyclerView rvSelectGoods;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.iv_search)
    AppCompatImageView ivSearch;

    InventorySubjectTagAdapter mSubjectTagAdapter;
    InventorySelectGoodsAdapter mSelectGoodsAdapter;

    private String mSubjectId;
    private Map<String, InventoryGoodBean> mSelectedMap = new HashMap<>();

    @Override
    public int getLayoutId() {
        return R.layout.inventory_select_goods_layout;
    }

    @Override
    public void initView() {
        titleBar.setListener(this);
        mSubjectTagAdapter = new InventorySubjectTagAdapter(this, null);
        mSubjectTagAdapter.setMaxSelectionCount(1);
        mSubjectTagAdapter.setMode(EasyAdapter.Mode.SINGLE_SELECT);
        rvSubjectTag.setAdapter(mSubjectTagAdapter);
        mSubjectTagAdapter.setOnSingleSelectListener(this);

        initRecycleView();
    }

    @Override
    public void initData() {
        loadGoodsData();

        etSearchView.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                queryStr = s.toString();
            }
        });

    }

    private void loadGoodsData() {
        RxHttp.get(ApiService.API_ASSET_SUBJECT)
                .add("assetType", "INSTRUMENT")
                .asResponseList(SubjectBean.class)
                .as(RxLife.asOnMain(this))
                .subscribe(subjectList -> {
                    mSubjectTagAdapter.setData(subjectList);
                    mSubjectTagAdapter.select(0);
                    getGoodsList(true, queryStr, mSubjectId);
                }, throwable -> {
                    HttpStatusCodeException exception = (HttpStatusCodeException) throwable;
                    String errorMsg = exception.getResult(); //拿到msg字段
                    Response response = GsonUtils.fromJson(errorMsg, Response.class);
                    showToastFailure(response.getMessage());
                });

        mSubjectTagAdapter.setOnSingleSelectListener(position -> {
            mSubjectId = mSubjectTagAdapter.getData().get(position).getId();
            getGoodsList(true, queryStr, mSubjectId);
        });

    }

    @Override
    public boolean isRegisterOttO() {
        return true;
    }


    /**
     * 查询物品
     *
     * @param isRefresh
     * @param query
     * @param subjectId
     */
    public void getGoodsList(boolean isRefresh, String query, String subjectId) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("queryStr", query);
        bodyMap.put("subjectId", subjectId);

        HashMap<String, Integer> page = new HashMap<>();
        page.put("pageNo", pageNo);
        page.put("pageSize", pageSize);
        RxHttp.postJson(ApiService.API_RFID_SELECT_GOODS_LIST)
                .add("queryStr", query)
                .add("subjectId", subjectId)
                .add("page", page)
                .asResponse(InventoryGoodPage.Page.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(() -> {
                    hideLoading();
                    hideKeyboard(etSearchView);
                })//请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(goodsListBean -> {
                    for (InventoryGoodBean goodBean : goodsListBean.getList()) {
                        String key = mSubjectId + goodBean.getCode();
                        boolean selected = mSelectedMap.containsKey(key);
                        goodBean.setSelected(selected);
                        if (selected) mSelectedMap.put(key, goodBean);
                    }
                    if (isRefresh) {
                        refreshLayout.resetNoMoreData();
                        mSelectGoodsAdapter.setList(goodsListBean.getList());
                        rvSelectGoods.scrollToPosition(0);
                        refreshLayout.finishRefresh();
                    } else {
                        if (pageNo > goodsListBean.getPageCount()) {
                            refreshLayout.finishRefreshWithNoMoreData();
                        } else {
                            mSelectGoodsAdapter.addData(goodsListBean.getList());
                            refreshLayout.finishLoadMore();
                        }
                    }

                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }

    /**
     * 物品数据保存
     * 标准内的物品
     *
     * @param goodsBean
     */
    private void saveGoods(List<Map<String, Object>> goodsBean) {

        RxHttp.postJson(ApiService.API_RFID_SELECT_GOODS)
                .add("assetDetails", goodsBean)
                .add("buildingId", mRoomBean.getId())
                .add("taskId", mTaskBean.getId())
                .asResponse(String.class)
                .doOnSubscribe(disposable -> showLoading())  //请求开始，当前在主线程回调
                .doFinally(this::hideLoading) //请求结束，当前在主线程回调
                .as(RxLife.asOnMain(this))
                .subscribe(str -> {
                    showToastSuccess("选入成功");
                    OttoBus.getInstance().post(new BusData(OTTOTags.REFRESH_TAGS, ""));
                    finish();
                }, throwable -> {
                    ParseException exception = (ParseException) throwable;
                    showToastFailure(exception.getMessage());
                });
    }


    /**
     * 初始化recycleview
     */
    private void initRecycleView() {
        mSelectGoodsAdapter = new InventorySelectGoodsAdapter(null);
        rvSelectGoods.setAdapter(mSelectGoodsAdapter);
        refreshLayout.setOnRefreshLoadMoreListener(this);
        mSelectGoodsAdapter.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        InventoryGoodBean goodsBean = mSelectGoodsAdapter.getData().get(position);
        boolean selected = !goodsBean.isSelected();
        goodsBean.setSelected(selected);
        mSelectGoodsAdapter.notifyItemChanged(position);
        String key = mSubjectId + goodsBean.getCode();
        if (selected) {
            mSelectedMap.put(key, goodsBean);
        } else {
            mSelectedMap.remove(key);
        }
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        pageNo++;
        getGoodsList(false, queryStr, mSubjectId);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        pageNo = 1;
        getGoodsList(true, queryStr, mSubjectId);

    }

    @OnClick({R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.iv_search:
                queryStr = TextUtils.isEmpty(etSearchView.getText().toString()) ? "" : etSearchView.getText().toString();
                getGoodsList(true, queryStr, mSubjectId);
                break;

            default:
                break;
        }

    }

    /**
     * 获取选中的物品
     *
     * @return
     */
    public List<Map<String, Object>> getSelectGoods() {
        List<Map<String, Object>> selectGoods = new ArrayList<>();
        for (InventoryGoodBean goods : mSelectedMap.values()) {
            Map<String, Object> goodsJson = new HashMap<>();
            goodsJson.put("inventoryCatalogId", goods.getInventoryCatalogId());
            goodsJson.put("code", goods.getCode());
            goodsJson.put("name", goods.getName());
            goodsJson.put("spec", goods.getSpec());
            goodsJson.put("uom", goods.getUom());
            goodsJson.put("price", goods.getPrice());
            goodsJson.put("subjectId", goods.getSubjectId());
            goodsJson.put("subjectName", goods.getSubjectName());
            goodsJson.put("usageId", goods.getUsageId());
            goodsJson.put("usageName", goods.getUsageName());
            goodsJson.put("schlstageId", goods.getSchlstageId());
            goodsJson.put("amount", goods.getPrice());
            selectGoods.add(goodsJson);
        }
        return selectGoods;
    }

    @Override
    public void onSelected(int position) {
        mSubjectId = mSubjectTagAdapter.getData().get(position).getId();
        getGoodsList(true, queryStr, mSubjectId);
    }

    @Override
    public void onClicked(View v, int action, String extra) {
        switch (action) {
            case ACTION_LEFT_TEXT:
                finish();
                break;
            case ACTION_RIGHT_TEXT:
                //选入
                if (mSelectedMap.size() <= 0) {
                    showToastFailure("请选择选入的物品");
                    return;
                }
                saveGoods(getSelectGoods());
                break;
            default:
                break;
        }
    }
}