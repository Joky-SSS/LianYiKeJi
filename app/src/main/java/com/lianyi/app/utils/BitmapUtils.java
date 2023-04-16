package com.lianyi.app.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import com.blankj.utilcode.util.SizeUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.zxing.BarcodeFormat;
import com.king.zxing.util.CodeUtils;
import com.lianyi.app.R;
import com.lianyi.app.base.BaseActvity;
import com.lianyi.app.model.GoodsBean;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * @ClassName: BitmapUtils
 * @Description: 不可见View生成Bitmap
 * @Author: Lee
 * @CreateDate: 2020/7/12 19:59
 */
public class BitmapUtils {


    /**
     * 信息电教标签
     *
     * @param context
     * @param goodsBean
     * @return
     */
    public static Bitmap xxdjLabelView(Context context, GoodsBean goodsBean) {
        View mPrintView = LayoutInflater.from(context).inflate(R.layout.print_xxdj_layout, null);
        AppCompatImageView ivQrCode = mPrintView.findViewById(R.id.iv_qrcode);
        AppCompatTextView tvGoodsQrSubject = mPrintView.findViewById(R.id.tv_goods_qr_subject);
        AppCompatTextView tvGoodsQrAddress = mPrintView.findViewById(R.id.tv_goods_qr_address);
        AppCompatTextView tvGoodsQrName = mPrintView.findViewById(R.id.tv_goods_qr_name);

        tvGoodsQrName.setText(String.format(context.getResources().getString(R.string.str_qr_name),TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName()));
        tvGoodsQrSubject.setText(String.format(context.getResources().getString(R.string.str_qr_subject), TextUtils.isEmpty(goodsBean.getSubjectName()) ? "" : goodsBean.getSubjectName()));
        tvGoodsQrAddress.setText(String.format(context.getResources().getString(R.string.str_qr_building), TextUtils.isEmpty(goodsBean.getBuildingName()) ? "" : goodsBean.getBuildingName()));

        ivQrCode.setImageBitmap(CodeUtils.createQRCode(goodsBean.getBarcodeUrl(),SizeUtils.dp2px(120),Color.BLACK));


//        tvGoodsQrName.setText(R.string.str_qr_code);
//        tvGoodsQrSubject.setText(R.string.str_depository);
//        tvGoodsQrAddress.setText(R.string.str_qr_space);
//        tvGoodsQrName.setText(R.string.str_qr_name);
//        ivQrCode.setImageBitmap(CodeUtils.createQRCode("https://ys5.lianyitech.com.cn/emsschool/eems/api/asset/asset-detail/list?queryStr=&buildingId=efea33c9badf4a6da2e8287575012841&taskId=d1c87f12527442518847b013b3aef637&pageNo=1&pageSize=20", 78, Color.BLACK));


        //将布局转化成view对象
        LinearLayout linearLayout = mPrintView.findViewById(R.id.root_view);
        WindowManager manager = ((BaseActvity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);

        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
        return layoutView(linearLayout, width, height);
    }

    /**
     * 信息电教标签
     *
     * @param context
     * @param goodsBean
     * @return
     */
    public static Bitmap xxdjLabel1View(Context context, GoodsBean goodsBean) {
        View mPrintView = LayoutInflater.from(context).inflate(R.layout.print_xxdj_1_layout, null);
        AppCompatImageView ivQrCode = mPrintView.findViewById(R.id.iv_qrcode);
        AppCompatTextView tvGoodsQrCode = mPrintView.findViewById(R.id.tv_goods_qr_code);
        AppCompatTextView tvGoodsQrUser = mPrintView.findViewById(R.id.tv_goods_qr_user);
        AppCompatTextView tvGoodsQrSpac = mPrintView.findViewById(R.id.tv_goods_qr_spac);
        AppCompatTextView tvGoodsQrName = mPrintView.findViewById(R.id.tv_goods_qr_name);

        tvGoodsQrCode.setText(String.format(context.getResources().getString(R.string.str_qr_code), TextUtils.isEmpty(goodsBean.getCode()) ? "" : goodsBean.getCode()));
        tvGoodsQrUser.setText(String.format(context.getResources().getString(R.string.str_depository), goodsBean.getUserName()));
        tvGoodsQrSpac.setText(String.format(context.getResources().getString(R.string.str_qr_space), goodsBean.getSpec()));
        tvGoodsQrName.setText(String.format(context.getResources().getString(R.string.str_qr_name), goodsBean.getName()));
//        ivQrCode.setImageBitmap(QRCodeEncoder.syncEncodeQRCode(goodsBean.getBarcodeUrl(), SizeUtils.px2dp(110)));
        ivQrCode.setImageBitmap(CodeUtils.createQRCode(goodsBean.getBarcodeUrl(),SizeUtils.dp2px(120),Color.BLACK));



//        tvGoodsQrCode.setText(R.string.str_qr_code);
//        tvGoodsQrUser.setText(R.string.str_depository);
//        tvGoodsQrSpac.setText(R.string.str_qr_space);
//        tvGoodsQrName.setText(R.string.str_qr_name);
//        ivQrCode.setImageBitmap(QRCodeEncoder.syncEncodeQRCode("https://ys5.lianyitech.com.cn/emsschool/eems/api/asset/asset-detail/list?queryStr=&buildingId=efea33c9badf4a6da2e8287575012841&taskId=d1c87f12527442518847b013b3aef637&pageNo=1&pageSize=20", SizeUtils.dp2px(110), Color.BLACK));

        //将布局转化成view对象
        LinearLayout linearLayout = mPrintView.findViewById(R.id.root_view);
        WindowManager manager = ((BaseActvity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
//
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
        return layoutView(linearLayout, width, height);
    }

    /**
     * 教学仪器标签
     *
     * @param context
     * @param goodsBean
     * @return
     */
    public static Bitmap jxyqLabelView(Context context, GoodsBean goodsBean) {
        View mPrintView = LayoutInflater.from(context).inflate(R.layout.print_jxyq_layout, null);
        AppCompatImageView ivQrCode = mPrintView.findViewById(R.id.iv_qrcode);
        AppCompatTextView tvGoodsQrCode = mPrintView.findViewById(R.id.tv_goods_qr_code);
        AppCompatTextView tvGoodsQrName = mPrintView.findViewById(R.id.tv_goods_qr_name);
        AppCompatTextView tvGoodsQrSpac = mPrintView.findViewById(R.id.tv_goods_qr_spac);

        tvGoodsQrCode.setText(String.format(context.getResources().getString(R.string.str_qr_code), TextUtils.isEmpty(goodsBean.getCode()) ? "" : goodsBean.getCode()));
        tvGoodsQrName.setText(String.format(context.getResources().getString(R.string.str_qr_name), TextUtils.isEmpty(goodsBean.getName()) ? "" : goodsBean.getName()));
        tvGoodsQrSpac.setText(String.format(context.getResources().getString(R.string.str_qr_space), TextUtils.isEmpty(goodsBean.getSpec()) ? "" : goodsBean.getSpec()));
        ivQrCode.setImageBitmap(CodeUtils.createQRCode(goodsBean.getBarcodeUrl(),SizeUtils.dp2px(120),Color.BLACK));

//        tvGoodsQrCode.setText(R.string.str_qr_code);
//        tvGoodsQrName.setText(R.string.str_qr_name);
//        tvGoodsQrSpac.setText(R.string.str_qr_space);
//        ivQrCode.setImageBitmap(QRCodeEncoder.syncEncodeQRCode("https://ys5.lianyitech.com.cn/emsschool/eems/api/asset/asset-detail/list?queryStr=&buildingId=efea33c9badf4a6da2e8287575012841&taskId=d1c87f12527442518847b013b3aef637&pageNo=1&pageSize=20", SizeUtils.dp2px(110), Color.BLACK));

        //将布局转化成view对象
        View viewById = mPrintView.findViewById(R.id.root_view);
        WindowManager manager = ((BaseActvity) context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;
        //然后View和其内部的子View都具有了实际大小，也就是完成了布局，相当与添加到了界面上。接着就可以创建位图并在上面绘制了：
        return layoutView(viewById, width, height);
    }


    /**
     * 填充布局内容
     */
    private static Bitmap layoutView(final View view, int width, int height) {
        int widthSpec = 0;
        int heightSpec = 0;
        System.out.println("width----" + width);
        System.out.println("height----" + height);
        if (width > height) {
            // 测量
            widthSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED);
            heightSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.UNSPECIFIED);
        } else {
            // 测量
            widthSpec = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.UNSPECIFIED);
            heightSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        // 布局
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();

        System.out.println("measuredWidth----" + measuredWidth);
        System.out.println("measuredHeight----" + measuredHeight);

        view.layout(0, 0, measuredWidth, measuredHeight);
        // 绘制
        int bitmapWidth = view.getWidth();
        int bitmapHeight = view.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
