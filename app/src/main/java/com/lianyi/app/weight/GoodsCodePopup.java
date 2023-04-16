package com.lianyi.app.weight;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.allen.library.SuperTextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.lxj.xpopup.core.BottomPopupView;
import com.lianyi.app.R;
import com.lianyi.app.model.GoodsCatalogueBean;
import com.lianyi.app.utils.SpacesItemDecoration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * @ClassName: GoodsCodePopup
 * @Description: 标准目录弹窗
 * @Author: Lee
 * @CreateDate: 2020/7/7 11:46
 */
public class GoodsCodePopup extends BottomPopupView implements OnItemClickListener, View.OnClickListener {
    Context mContext;
    SuperTextView svPopTitle;
    AppCompatEditText etSearchView;
    RecyclerView rvPopupCode;
    AppCompatImageView ivSearch;
    private List<GoodsCatalogueBean> dataList;
    private PopListAdapter mPopListAdapter;
    private String mPopTitle;
    private boolean isShowName;


    public GoodsCodePopup(@NonNull Context context, List<GoodsCatalogueBean> dataList, String title, boolean isShowName) {
        super(context);
        this.mContext = context;
        this.dataList = dataList;
        this.mPopTitle = title;
        this.isShowName = isShowName;
        mPopListAdapter = new PopListAdapter(dataList);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        svPopTitle = findViewById(R.id.sv_pop_title);
        etSearchView = findViewById(R.id.et_search_view);
        rvPopupCode = findViewById(R.id.rv_popup_code);
        ivSearch = findViewById(R.id.iv_search);
        initRecycleView(rvPopupCode, mContext);
        ivSearch.setOnClickListener(this);
        mPopListAdapter.setOnItemClickListener(this);
        rvPopupCode.setAdapter(mPopListAdapter);
        svPopTitle.setCenterString(TextUtils.isEmpty(mPopTitle) ? " " : mPopTitle);
        if (isShowName) {
            etSearchView.setHint("请输入物品名称进行搜索");
            etSearchView.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        } else {
            etSearchView.setHint("请输入物品编号进行搜索");
            etSearchView.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
        }

        if (mOnItemClickListener != null) {
            mOnItemClickListener.initData();
        }
    }

    /**
     * 初始化recycleview
     */
    private void initRecycleView(RecyclerView recyclerView, Context context) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        SpacesItemDecoration spacesItemDecoration = new SpacesItemDecoration(10);
        recyclerView.addItemDecoration(spacesItemDecoration);

    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_code_layout;
    }

    @Override
    public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
        List<GoodsCatalogueBean> data = (List<GoodsCatalogueBean>) adapter.getData();
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(data.get(position));
        }
        dismiss();
    }


    public void setDataList(List<GoodsCatalogueBean> dataList) {
        this.dataList = dataList;
        mPopListAdapter.setNewInstance(dataList);
        rvPopupCode.scrollToPosition(0);
    }

    public List<GoodsCatalogueBean> getDataList() {
        return dataList;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onSearch(etSearchView.getText().toString());
        }
    }

    public class PopListAdapter extends BaseQuickAdapter<GoodsCatalogueBean, BaseViewHolder> {

        public PopListAdapter(@Nullable List<GoodsCatalogueBean> data) {
            super(R.layout.item_popup_code, data);
        }

        @Override
        protected void convert(@NotNull BaseViewHolder viewHolder, GoodsCatalogueBean catalogueBean) {
            viewHolder.setText(R.id.item_tv_goods_code, catalogueBean.getCode());
            viewHolder.setText(R.id.item_tv_goods_name, catalogueBean.getName());
            viewHolder.setText(R.id.item_tv_goods_spec, catalogueBean.getSpec());
            viewHolder.setText(R.id.item_tv_goods_unit, catalogueBean.getUom());
        }
    }

    onItemClickListener mOnItemClickListener;

    public GoodsCodePopup setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
        return this;
    }


    public interface onItemClickListener {
        void onItemClick(GoodsCatalogueBean catalogueBean);

        void onSearch(String search);

        void initData();

    }

//    @Override
//    protected int getMaxHeight() {
//        return XPopupUtils.getWindowHeight(mContext) - 200;
//    }
}
