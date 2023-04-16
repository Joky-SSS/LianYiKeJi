// Generated code from Butter Knife. Do not modify!
package com.lianyi.app.ui;

import android.view.View;
import android.widget.CheckBox;
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

public class LabelPrintActivity_ViewBinding implements Unbinder {
  private LabelPrintActivity target;

  private View view7f080224;

  private View view7f08015f;

  private View view7f080064;

  private View view7f080063;

  private View view7f0800ff;

  @UiThread
  public LabelPrintActivity_ViewBinding(LabelPrintActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LabelPrintActivity_ViewBinding(final LabelPrintActivity target, View source) {
    this.target = target;

    View view;
    target.titleBar = Utils.findRequiredViewAsType(source, R.id.title_bar, "field 'titleBar'", CommonTitleBar.class);
    target.etSearchView = Utils.findRequiredViewAsType(source, R.id.et_search_view, "field 'etSearchView'", AppCompatEditText.class);
    target.swipeRecyclerview = Utils.findRequiredViewAsType(source, R.id.swipe_recyclerview, "field 'swipeRecyclerview'", RecyclerView.class);
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.refreshLayout, "field 'refreshLayout'", SmartRefreshLayout.class);
    view = Utils.findRequiredView(source, R.id.tv_print_type, "field 'tvPrintType' and method 'onViewClicked'");
    target.tvPrintType = Utils.castView(view, R.id.tv_print_type, "field 'tvPrintType'", AppCompatTextView.class);
    view7f080224 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.rb_goods_none, "field 'rbGoodsNone' and method 'onViewClicked'");
    target.rbGoodsNone = Utils.castView(view, R.id.rb_goods_none, "field 'rbGoodsNone'", CheckBox.class);
    view7f08015f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_print_setting, "field 'btPrintSetting' and method 'onViewClicked'");
    target.btPrintSetting = Utils.castView(view, R.id.bt_print_setting, "field 'btPrintSetting'", AppCompatButton.class);
    view7f080064 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.bt_label_print, "field 'btLabelPrint' and method 'onViewClicked'");
    target.btLabelPrint = Utils.castView(view, R.id.bt_label_print, "field 'btLabelPrint'", AppCompatButton.class);
    view7f080063 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.iv_search, "field 'ivSearch' and method 'onViewClicked'");
    target.ivSearch = Utils.castView(view, R.id.iv_search, "field 'ivSearch'", AppCompatImageView.class);
    view7f0800ff = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.ivTest = Utils.findRequiredViewAsType(source, R.id.iv_test, "field 'ivTest'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LabelPrintActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleBar = null;
    target.etSearchView = null;
    target.swipeRecyclerview = null;
    target.refreshLayout = null;
    target.tvPrintType = null;
    target.rbGoodsNone = null;
    target.btPrintSetting = null;
    target.btLabelPrint = null;
    target.ivSearch = null;
    target.ivTest = null;

    view7f080224.setOnClickListener(null);
    view7f080224 = null;
    view7f08015f.setOnClickListener(null);
    view7f08015f = null;
    view7f080064.setOnClickListener(null);
    view7f080064 = null;
    view7f080063.setOnClickListener(null);
    view7f080063 = null;
    view7f0800ff.setOnClickListener(null);
    view7f0800ff = null;
  }
}
