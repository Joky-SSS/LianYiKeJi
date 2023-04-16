// Generated code from Butter Knife. Do not modify!
package com.lianyi.app.ui;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.widget.NestedScrollView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.allen.library.SuperTextView;
import com.lianyi.app.R;
import com.lianyi.app.weight.DecimalEditTextView;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GoodsDetailsActivity_ViewBinding implements Unbinder {
  private GoodsDetailsActivity target;

  private View view7f0801bc;

  private View view7f080065;

  private View view7f080063;

  @UiThread
  public GoodsDetailsActivity_ViewBinding(GoodsDetailsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GoodsDetailsActivity_ViewBinding(final GoodsDetailsActivity target, View source) {
    this.target = target;

    View view;
    target.titleBar = Utils.findRequiredViewAsType(source, R.id.title_bar, "field 'titleBar'", CommonTitleBar.class);
    target.svGoodsSerialNumber = Utils.findRequiredViewAsType(source, R.id.sv_goods_serial_number, "field 'svGoodsSerialNumber'", SuperTextView.class);
    target.svGoodsName = Utils.findRequiredViewAsType(source, R.id.sv_goods_name, "field 'svGoodsName'", SuperTextView.class);
    target.svGoodsSpecifications = Utils.findRequiredViewAsType(source, R.id.sv_goods_specifications, "field 'svGoodsSpecifications'", SuperTextView.class);
    target.svGoodsUnit = Utils.findRequiredViewAsType(source, R.id.sv_goods_unit, "field 'svGoodsUnit'", SuperTextView.class);
    target.etGoodsNumber = Utils.findRequiredViewAsType(source, R.id.et_goods_number, "field 'etGoodsNumber'", AppCompatEditText.class);
    target.etGoodsUnitPrice = Utils.findRequiredViewAsType(source, R.id.et_goods_unit_price, "field 'etGoodsUnitPrice'", DecimalEditTextView.class);
    target.svGoodsSubject = Utils.findRequiredViewAsType(source, R.id.sv_goods_subject, "field 'svGoodsSubject'", SuperTextView.class);
    target.svGoodsUse = Utils.findRequiredViewAsType(source, R.id.sv_goods_use, "field 'svGoodsUse'", SuperTextView.class);
    target.svGoodsSaveAddress = Utils.findRequiredViewAsType(source, R.id.sv_goods_save_address, "field 'svGoodsSaveAddress'", SuperTextView.class);
    target.etGoodsDepository = Utils.findRequiredViewAsType(source, R.id.et_goods_depository, "field 'etGoodsDepository'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.sv_goods_date, "field 'svGoodsDate' and method 'onViewClicked'");
    target.svGoodsDate = Utils.castView(view, R.id.sv_goods_date, "field 'svGoodsDate'", SuperTextView.class);
    view7f0801bc = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.etGoodsExp = Utils.findRequiredViewAsType(source, R.id.et_goods_exp, "field 'etGoodsExp'", AppCompatEditText.class);
    target.etGoodsMfg = Utils.findRequiredViewAsType(source, R.id.et_goods_mfg, "field 'etGoodsMfg'", AppCompatEditText.class);
    target.etGoodsBrand = Utils.findRequiredViewAsType(source, R.id.et_goods_brand, "field 'etGoodsBrand'", AppCompatEditText.class);
    target.etGoodsSupplier = Utils.findRequiredViewAsType(source, R.id.et_goods_supplier, "field 'etGoodsSupplier'", AppCompatEditText.class);
    target.etGoodsContact = Utils.findRequiredViewAsType(source, R.id.et_goods_contact, "field 'etGoodsContact'", AppCompatEditText.class);
    target.etGoodsContactPhone = Utils.findRequiredViewAsType(source, R.id.et_goods_contact_phone, "field 'etGoodsContactPhone'", AppCompatEditText.class);
    target.svGoodsAttribute = Utils.findRequiredViewAsType(source, R.id.sv_goods_attribute, "field 'svGoodsAttribute'", SuperTextView.class);
    view = Utils.findRequiredView(source, R.id.bt_save, "field 'btSave' and method 'onViewClicked'");
    target.btSave = Utils.castView(view, R.id.bt_save, "field 'btSave'", AppCompatButton.class);
    view7f080065 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.btContinue = Utils.findRequiredViewAsType(source, R.id.bt_continue, "field 'btContinue'", AppCompatButton.class);
    view = Utils.findRequiredView(source, R.id.bt_label_print, "field 'btLabelPrint' and method 'onViewClicked'");
    target.btLabelPrint = Utils.castView(view, R.id.bt_label_print, "field 'btLabelPrint'", AppCompatButton.class);
    view7f080063 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.nestedScrollview = Utils.findRequiredViewAsType(source, R.id.nested_scrollview, "field 'nestedScrollview'", NestedScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GoodsDetailsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleBar = null;
    target.svGoodsSerialNumber = null;
    target.svGoodsName = null;
    target.svGoodsSpecifications = null;
    target.svGoodsUnit = null;
    target.etGoodsNumber = null;
    target.etGoodsUnitPrice = null;
    target.svGoodsSubject = null;
    target.svGoodsUse = null;
    target.svGoodsSaveAddress = null;
    target.etGoodsDepository = null;
    target.svGoodsDate = null;
    target.etGoodsExp = null;
    target.etGoodsMfg = null;
    target.etGoodsBrand = null;
    target.etGoodsSupplier = null;
    target.etGoodsContact = null;
    target.etGoodsContactPhone = null;
    target.svGoodsAttribute = null;
    target.btSave = null;
    target.btContinue = null;
    target.btLabelPrint = null;
    target.nestedScrollview = null;

    view7f0801bc.setOnClickListener(null);
    view7f0801bc = null;
    view7f080065.setOnClickListener(null);
    view7f080065 = null;
    view7f080063.setOnClickListener(null);
    view7f080063 = null;
  }
}
