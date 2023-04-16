// Generated code from Butter Knife. Do not modify!
package com.lianyi.app.ui;

import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lianyi.app.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SelectGoodsActivity_ViewBinding implements Unbinder {
  private SelectGoodsActivity target;

  private View view7f080066;

  private View view7f0800ff;

  private View view7f08015e;

  @UiThread
  public SelectGoodsActivity_ViewBinding(SelectGoodsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SelectGoodsActivity_ViewBinding(final SelectGoodsActivity target, View source) {
    this.target = target;

    View view;
    target.titleBar = Utils.findRequiredViewAsType(source, R.id.title_bar, "field 'titleBar'", CommonTitleBar.class);
    target.rvSubjectTag = Utils.findRequiredViewAsType(source, R.id.rv_subject_tag, "field 'rvSubjectTag'", RecyclerView.class);
    target.rbGoodsNone = Utils.findRequiredViewAsType(source, R.id.rb_goods_none, "field 'rbGoodsNone'", RadioButton.class);
    target.rbGoodsSelect = Utils.findRequiredViewAsType(source, R.id.rb_goods_select, "field 'rbGoodsSelect'", RadioButton.class);
    target.rgGoodsTypes = Utils.findRequiredViewAsType(source, R.id.rg_goods_types, "field 'rgGoodsTypes'", RadioGroup.class);
    target.etSearchView = Utils.findRequiredViewAsType(source, R.id.et_search_view, "field 'etSearchView'", AppCompatEditText.class);
    target.rvSelectGoods = Utils.findRequiredViewAsType(source, R.id.rv_select_goods, "field 'rvSelectGoods'", RecyclerView.class);
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.refreshLayout, "field 'refreshLayout'", SmartRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.bt_select, "field 'btSelect' and method 'onViewClicked'");
    target.btSelect = Utils.castView(view, R.id.bt_select, "field 'btSelect'", AppCompatButton.class);
    view7f080066 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTotalOf = Utils.findRequiredViewAsType(source, R.id.tv_total_of, "field 'tvTotalOf'", AppCompatTextView.class);
    view = Utils.findRequiredView(source, R.id.iv_search, "field 'ivSearch' and method 'onViewClicked'");
    target.ivSearch = Utils.castView(view, R.id.iv_search, "field 'ivSearch'", AppCompatImageView.class);
    view7f0800ff = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.rvBuildingTag = Utils.findRequiredViewAsType(source, R.id.rv_building_tag, "field 'rvBuildingTag'", RecyclerView.class);
    target.roomTypeLayout = Utils.findRequiredViewAsType(source, R.id.room_type_layout, "field 'roomTypeLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.rb_goods_all, "field 'rbGoodsAll' and method 'onViewClicked'");
    target.rbGoodsAll = Utils.castView(view, R.id.rb_goods_all, "field 'rbGoodsAll'", CheckBox.class);
    view7f08015e = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    SelectGoodsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleBar = null;
    target.rvSubjectTag = null;
    target.rbGoodsNone = null;
    target.rbGoodsSelect = null;
    target.rgGoodsTypes = null;
    target.etSearchView = null;
    target.rvSelectGoods = null;
    target.refreshLayout = null;
    target.btSelect = null;
    target.tvTotalOf = null;
    target.ivSearch = null;
    target.rvBuildingTag = null;
    target.roomTypeLayout = null;
    target.rbGoodsAll = null;

    view7f080066.setOnClickListener(null);
    view7f080066 = null;
    view7f0800ff.setOnClickListener(null);
    view7f0800ff = null;
    view7f08015e.setOnClickListener(null);
    view7f08015e = null;
  }
}
