package com.lianyi.app.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;

import androidx.appcompat.widget.AppCompatTextView;

import com.lianyi.app.R;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * @author Lee
 * @name DecimalTextView
 * @describe 自动补位的TextView
 * @time 2019/3/22 15:39
 */
public class DecimalTextView extends AppCompatTextView {

    /**
     * 默认数字符号 // "¥"
     */
    private String mSymbol = "¥"; // Currency.getInstance(Locale.getDefault()).getSymbol();
    /**
     * 数字符号的字体大小 默认与字体大小一致
     */
    private float mSymbolSize = 0;
    /**
     * 是否显示数字符号 默认false
     */
    private boolean mShowSymbol = false;
    /**
     * 是否显示数字分号 默认false
     */
    private boolean mShowCommas = false;
    /**
     * 上限数字 默认1000000
     */
    private double mUpperDecimal = 9999999.99F;

    /**
     * 小数点后位数 默认2位
     */
    private int mDecimalScale = 2;

    /**
     * 小数点后是否用0填充 默认true
     */
    private boolean mDecimalFill = true;

    public DecimalTextView(Context context) {
        super(context);
        initView(context, null);
    }

    public DecimalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * 初始化
     * 获取自定义参数
     */
    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.DecimalTextView, 0, 0);
            try {
                String symbol = attrArray.getString(R.styleable.DecimalTextView_public_decimal_symbol);
                if (symbol != null) {
                    mSymbol = symbol;
                }
                mSymbolSize = attrArray.getDimensionPixelSize(R.styleable.DecimalTextView_public_decimal_symbol_size, 0);
                mShowSymbol = attrArray.getBoolean(R.styleable.DecimalTextView_public_decimal_show_symbol, false);
                mShowCommas = attrArray.getBoolean(R.styleable.DecimalTextView_public_decimal_show_commas, false);
                mUpperDecimal = attrArray.getFloat(R.styleable.DecimalTextView_public_decimal_upper, 9999999.99F);
                mDecimalScale = attrArray.getInteger(R.styleable.DecimalTextView_public_decimal_scale, 2);
                mDecimalFill = attrArray.getBoolean(R.styleable.DecimalTextView_public_decimal_fill_zero, true);
            } finally {
                attrArray.recycle();
            }
        }
        setDecimalValue("1");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setDecimalValue(getText().toString());
    }

    /**
     * 设置数字
     *
     * @param format  格式化
     * @param decimal
     */
    public void setDecimalValue(String format, Double decimal) {
        setText(String.format(format, formatDecimal2String(decimal)));
    }

    /**
     * 设置数字
     *
     * @param format     格式化
     * @param decimalStr
     */
    public void setDecimalValue(String format, String decimalStr) {

        setText(String.format(format, formatDecimal2String(formatDecimal2Double(decimalStr))));
    }

    /**
     * 设置数字
     */
    public void setDecimalValue(Double decimal) {
        setText(formatDecimal2String(decimal));
    }

    /**
     * 设置数字
     */
    public void setDecimalValue(String decimalStr) {

        setText(formatDecimal2String(formatDecimal2Double(decimalStr)));

    }

    /**
     * 格式化数字 double2string
     */
    private CharSequence formatDecimal2String(Double decimal) {
        if (null == decimal) {
            return null;
        }

        StringBuilder decimalScaleStr = new StringBuilder();
        String s = mDecimalFill ? "0" : "#";
        for (int i = 0; i < mDecimalScale; i++) {
            decimalScaleStr.append(s);
        }

        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(Locale.getDefault());
        if (mShowCommas && !mShowSymbol) { // 显示数字分号 && 不显示数字符号
            formatter.applyPattern(",##0." + decimalScaleStr);
        } else if (mShowCommas) { // 显示数字分号 && 显示数字符号
            formatter.applyPattern(mSymbol + ",##0." + decimalScaleStr);
        } else if (mShowSymbol) { // 不显示数字分号 && 显示数字符号
            formatter.applyPattern(mSymbol + "#0." + decimalScaleStr);
        } else { // 不显示数字分号 && 不显示数字符号
            formatter.applyPattern("#0." + decimalScaleStr);
        }

        SpannableStringBuilder result = new SpannableStringBuilder(formatter.format(decimal));
        if (mShowSymbol) {
            if (mSymbolSize == 0) {
                mSymbolSize = getTextSize();
            }
            result.setSpan(new AbsoluteSizeSpan((int) mSymbolSize), 0, mSymbol.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return result;
    }

    /**
     * 格式化数字 string2double
     */
    private Double formatDecimal2Double(String decimalStr) {
        if (null == decimalStr || "".equals(decimalStr)) {
            return null;
        }

        if (decimalStr.contains(",")) { // 移除逗号
            decimalStr = decimalStr.replace(",", "");
        }
        if (decimalStr.contains(mSymbol)) {
            decimalStr = decimalStr.replace(mSymbol, "");
        }

        Double decimal = 0d;
        try {
            decimal = Double.parseDouble(decimalStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return decimal;
    }

    public Double getDecimalValue() {
        return formatDecimal2Double(getText().toString());
    }

    public void setSymbol(String symbol) {
        Double value = formatDecimal2Double(getText().toString());
        this.mSymbol = symbol;
        setDecimalValue(value);
    }

    public String getSymbol() {
        return this.mSymbol;
    }

    public void setSymbolSize(float symbolSize) {
        setSymbolSize(symbolSize, TypedValue.COMPLEX_UNIT_SP);
    }

    public void setSymbolSize(float symbolSize, int unit) {
        this.mSymbolSize = TypedValue.applyDimension(unit, symbolSize, getResources().getDisplayMetrics());
        setDecimalValue(getText().toString());
    }

    public float getSymbolSize() {
        return this.mSymbolSize;
    }

    public void setShowSymbol(boolean showSymbol) {
        this.mShowSymbol = showSymbol;
        setDecimalValue(getText().toString());
    }

    public boolean isShowSymbol() {
        return this.mShowSymbol;
    }

    public void setShowCommas(boolean showCommas) {
        this.mShowCommas = showCommas;
        setDecimalValue(getText().toString());
    }

    public boolean isShowCommas() {
        return this.mShowCommas;
    }

    public void setUpperDecimal(double upperDecimal) {
        this.mUpperDecimal = upperDecimal;
    }

    public double getUpperDecimal() {
        return this.mUpperDecimal;
    }

    public void setDecimalScale(int decimalScale) {
        this.mDecimalScale = decimalScale;
    }

    public int getDecimalScale() {
        return this.mDecimalScale;
    }

    public void setDecimalFill(boolean decimalFill) {
        this.mDecimalFill = decimalFill;
        setDecimalValue(getText().toString());
    }

    public boolean isDecimalFill() {
        return this.mDecimalFill;
    }
}