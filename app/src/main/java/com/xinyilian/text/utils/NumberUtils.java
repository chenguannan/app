package com.xinyilian.text.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by 马天 on 2017/12/2.
 * 功能：数字格式转换
 */

public class NumberUtils {
    /**
     * 保留一位小数
     *
     * @param number
     * @return
     */
    public static String formatOneDecimal(float number) {
        DecimalFormat oneDec = new DecimalFormat("##0.0");
        return oneDec.format(number);
    }

    /**
     * 保留两位小数
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimal(float number) {
        DecimalFormat twoDec = new DecimalFormat("##0.00");
        return twoDec.format(number);
    }

    /**
     * 保留两位小数百分比
     *
     * @param number
     * @return
     */
    public static String formatTwoDecimalPercent(float number) {
        return formatTwoDecimal(number) + "%";
    }

    /**
     * 四舍五入
     *
     * @param number
     * @param scale  scale of the result returned.
     * @return
     */
    public static double roundingNumber(float number, int scale) {
        return roundingNumber(number, scale, RoundingMode.HALF_UP);
    }

    /**
     * 四舍五入
     *
     * @param number
     * @param scale        scale of the result returned.
     * @param roundingMode rounding mode to be used to round the result.
     * @return
     */
    public static double roundingNumber(float number, int scale, RoundingMode roundingMode) {
        BigDecimal b = new BigDecimal(number);
        return b.setScale(scale, roundingMode).doubleValue();
    }
    /**
     * 小数点后只能输入两位数字
     * param EditText
     *
     * @return
     */
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0, s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
