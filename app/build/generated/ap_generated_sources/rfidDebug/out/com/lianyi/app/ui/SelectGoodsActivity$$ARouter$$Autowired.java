package com.lianyi.app.ui;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.facade.template.ISyringe;
import com.alibaba.android.arouter.launcher.ARouter;
import java.lang.Object;
import java.lang.Override;

/**
 * DO NOT EDIT THIS FILE!!! IT WAS GENERATED BY AROUTER.
 */
public class SelectGoodsActivity$$ARouter$$Autowired implements ISyringe {
  private SerializationService serializationService;

  @Override
  public void inject(Object target) {
    serializationService = ARouter.getInstance().navigation(SerializationService.class);
    SelectGoodsActivity substitute = (SelectGoodsActivity)target;
    substitute.mListBean = substitute.getIntent().getParcelableExtra("room_bean");
    substitute.mTaskBean = substitute.getIntent().getParcelableExtra("task");
  }
}
