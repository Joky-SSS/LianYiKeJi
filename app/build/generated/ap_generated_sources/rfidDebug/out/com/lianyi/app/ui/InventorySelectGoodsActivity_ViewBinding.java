// Generated code from Butter Knife. Do not modify!
package com.lianyi.app.ui;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lianyi.app.R;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class InventorySelectGoodsActivity_ViewBinding implements Unbinder {
  private InventorySelectGoodsActivity target;

  private View view7f0800ff;

  @UiThread
  public InventorySelectGoodsActivity_ViewBinding(InventorySelectGoodsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public InventorySelectGoodsActivity_ViewBinding(final InventorySelectGoodsActivity target,
      View source) {
    this.target = target;

    View view;
    target.titleBar = Utils.findRequiredViewAsType(source, R.id.title_bar, "field 'titleBar'", CommonTitleBar.class);
    target.rvSubjectTag = Utils.findRequiredViewAsType(source, R.id.rv_subject_tag, "field 'rvSubjectTag'", RecyclerView.class);
    target.etSearchView = Utils.findRequiredViewAsType(source, R.id.et_search_view, "field 'etSearchView'", AppCompatEditText.class);
    target.rvSelectGoods = Utils.findRequiredViewAsType(source, R.id.rv_select_goods, "field 'rvSelectGoods'", RecyclerView.class);
    target.refreshLayout = Utils.findRequiredViewAsType(source, R.id.refreshLayout, "field 'refreshLayout'", SmartRefreshLayout.class);
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
    InventorySelectGoodsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleBar = null;
    target.rvSubjectTag = null;
    target.etSearchView = null;
    target.rvSelectGoods = null;
    target.refreshLayout = null;
    target.ivSearch = null;

    view7f0800ff.setOnClickListener(null);
    view7f0800ff = null;
  }
}
