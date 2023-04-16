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

public class NewGoodsActivity_ViewBinding implements Unbinder {
  private NewGoodsActivity target;

  private View view7f080065;

  private View view7f080062;

  private View view7f080063;

  @UiThread
  public NewGoodsActivity_ViewBinding(NewGoodsActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewGoodsActivity_ViewBinding(final NewGoodsActivity target, View source) {
    this.target = target;

    View view;
    target.titleBar = Utils.findRequiredViewAsType(source, R.id.title_bar, "field 'titleBar'", CommonTitleBar.class);
    target.etGoodsName = Utils.findRequiredViewAsType(source, R.id.et_goods_name, "field 'etGoodsName'", AppCompatEditText.class);
    target.etGoodsSerialNumber = Utils.findRequiredViewAsType(source, R.id.et_goods_serial_number, "field 'etGoodsSerialNumber'", AppCompatEditText.class);
    target.etGoodsSpecifications = Utils.findRequiredViewAsType(source, R.id.et_goods_specifications, "field 'etGoodsSpecifications'", AppCompatEditText.class);
    target.etGoodsUnit = Utils.findRequiredViewAsType(source, R.id.et_goods_unit, "field 'etGoodsUnit'", AppCompatEditText.class);
    target.etGoodsNumber = Utils.findRequiredViewAsType(source, R.id.et_goods_number, "field 'etGoodsNumber'", AppCompatEditText.class);
    target.etGoodsUnitPrice = Utils.findRequiredViewAsType(source, R.id.et_goods_unit_price, "field 'etGoodsUnitPrice'", DecimalEditTextView.class);
    target.svGoodsSubject = Utils.findRequiredViewAsType(source, R.id.sv_goods_subject, "field 'svGoodsSubject'", SuperTextView.class);
    target.svGoodsUse = Utils.findRequiredViewAsType(source, R.id.sv_goods_use, "field 'svGoodsUse'", SuperTextView.class);
    target.svGoodsLocation = Utils.findRequiredViewAsType(source, R.id.sv_goods_location, "field 'svGoodsLocation'", SuperTextView.class);
    target.etGoodsDepository = Utils.findRequiredViewAsType(source, R.id.et_goods_depository, "field 'etGoodsDepository'", AppCompatEditText.class);
    target.svGoodsDate = Utils.findRequiredViewAsType(source, R.id.sv_goods_date, "field 'svGoodsDate'", SuperTextView.class);
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
    view = Utils.findRequiredView(source, R.id.bt_continue, "field 'btContinue' and method 'onViewClicked'");
    target.btContinue = Utils.castView(view, R.id.bt_continue, "field 'btContinue'", AppCompatButton.class);
    view7f080062 = view;
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
    target.nestedScrollview = Utils.findRequiredViewAsType(source, R.id.nested_scrollview, "field 'nestedScrollview'", NestedScrollView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewGoodsActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.titleBar = null;
    target.etGoodsName = null;
    target.etGoodsSerialNumber = null;
    target.etGoodsSpecifications = null;
    target.etGoodsUnit = null;
    target.etGoodsNumber = null;
    target.etGoodsUnitPrice = null;
    target.svGoodsSubject = null;
    target.svGoodsUse = null;
    target.svGoodsLocation = null;
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

    view7f080065.setOnClickListener(null);
    view7f080065 = null;
    view7f080062.setOnClickListener(null);
    view7f080062 = null;
    view7f080063.setOnClickListener(null);
    view7f080063 = null;
  }
}
