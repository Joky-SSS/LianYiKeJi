// Generated code from Butter Knife. Do not modify!
package com.lianyi.app.ui;

import android.view.View;
import android.widget.Button;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.lianyi.app.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import org.angmarch.views.NiceSpinner;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view7f08005f;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.etUsername = Utils.findRequiredViewAsType(source, R.id.et_username, "field 'etUsername'", AppCompatEditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'etPassword'", AppCompatEditText.class);
    view = Utils.findRequiredView(source, R.id.bt_button, "field 'btButton' and method 'onViewClicked'");
    target.btButton = Utils.castView(view, R.id.bt_button, "field 'btButton'", Button.class);
    view7f08005f = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.niceSpinner = Utils.findRequiredViewAsType(source, R.id.nice_spinner, "field 'niceSpinner'", NiceSpinner.class);
    target.tvDevicesCode = Utils.findRequiredViewAsType(source, R.id.tv_devices_code, "field 'tvDevicesCode'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etUsername = null;
    target.etPassword = null;
    target.btButton = null;
    target.niceSpinner = null;
    target.tvDevicesCode = null;

    view7f08005f.setOnClickListener(null);
    view7f08005f = null;
  }
}
