package com.xinyilian.text.utils;

import java.util.regex.Pattern;

/**
 * Created by MT on 2017/8/28.
 * 判断是不是数字
 */

public class IsNumberUtils {

    public static boolean isNumeric(String str) {

        Pattern pattern = Pattern.compile("[0-9]*");
        LogUtils.i("isNumeric：" + pattern.matcher(str).matches());
        return pattern.matcher(str).matches();

    }

    /**
     * 纯字母
     *
     * @param fstrData
     * @return
     */
    public static boolean isChar(String fstrData) {
        boolean ischar = false;
        for (int i = 0; i < fstrData.length(); i++) {
            char c = fstrData.charAt(i);
            if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                ischar = true;
                break;
            } else {
                ischar = false;
            }
        }
        return ischar;
    }

    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            }
            if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }

        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;

    }

    // 判断一个字符是否是中文
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    // 判断一个字符串是否含有中文
    public static boolean isChinese(String str) {
        if (str == null)
            return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c))
                return true;// 有一个中文字符就返回
        }
        return false;
    }
}
