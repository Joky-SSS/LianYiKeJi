// Generated code from Butter Knife. Do not modify!
package com.lianyi.app.ui;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lianyi.app.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GoodsActivity_ViewBinding implements Unbinder {
  private GoodsActivity target;

  private View view7f080204;

  private View view7f080220;

  private View view7f080065;

  private View view7f080063;

  private View view7f0800ff;

  @UiThread
  public GoodsActivity_ViewBinding(GoodsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GoodsActivity_ViewBinding(final GoodsActivity target, View source) {
    this.target = target;

    View view;
    target.titleBar = Utils.findRequiredViewAsType(source, R.id.title_bar, "field 'titleBar'", CommonTitleBar.class);
    target.mSwipeRecyclerView = Utils.findRequiredViewAsType(source, R.id.swipe_recyclerview, "field 'mSwipeRecyclerView'", SwipeRecyclerView.class);
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.refreshLayout, "field 'refreshLayout'", SmartRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_add_goods, "field 'tvAddGoods' and method 'onViewClicked'");
    target.tvAddGoods = Utils.castView(view, R.id.tv_add_goods, "field 'tvAddGoods'", AppCompatTextView.class);
    view7f080204 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tv_new_goods, "field 'tvNewGoods' and method 'onViewClicked'");
    target.tvNewGoods = Utils.castView(view, R.id.tv_new_goods, "field 'tvNewGoods'", AppCompatTextView.class);
    view7f080220 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_save, "field 'btSave' and method 'onViewClicked'");
    target.btSave = Utils.castView(view, R.id.bt_save, "field 'btSave'", Button.class);
    view7f080065 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_label_print, "field 'btLabelPrint' and method 'onViewClicked'");
    target.btLabelPrint = Utils.castView(view, R.id.bt_label_print, "field 'btLabelPrint'", Button.class);
    view7f080063 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.tvTotalOf = Utils.findRequiredViewAsType(source, R.id.tv_total_of, "field 'tvTotalOf'", AppCompatTextView.class);
    target.etSearchView = Utils.findRequiredViewAsType(source, R.id.et_search_view, "field 'etSearchView'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.iv_search, "field 'ivSearch' and method 'onViewClicked'");
    target.ivSearch = Utils.castView(view, R.id.iv_search, "field 'ivSearch'", AppCompatImageView.class);
    view7f0800ff = view;
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
    GoodsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleBar = null;
    target.mSwipeRecyclerView = null;
    target.refreshLayout = null;
    target.tvAddGoods = null;
    target.tvNewGoods = null;
    target.btSave = null;
    target.btLabelPrint = null;
    target.tvTotalOf = null;
    target.etSearchView = null;
    target.ivSearch = null;

    view7f080204.setOnClickListener(null);
    view7f080204 = null;
    view7f080220.setOnClickListener(null);
    view7f080220 = null;
    view7f080065.setOnClickListener(null);
    view7f080065 = null;
    view7f080063.setOnClickListener(null);
    view7f080063 = null;
    view7f0800ff.setOnClickListener(null);
    view7f0800ff = null;
  }
}
