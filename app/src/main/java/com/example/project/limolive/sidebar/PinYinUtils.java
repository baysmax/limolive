package com.example.project.limolive.sidebar;

import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

/**
 * 作用: 获取拼音工具类
 *
 * @author LITP
 * @date 2016/11/8
 */

public class PinYinUtils {

    /**
     * 获取第一个字的拼音
     *
     * @param str
     * @return
     */
    public static String getFirstPiniYin(String str) {

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        //大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = str.trim().toCharArray();// 把字符串转化成字符数组

        String output = "";  //要返回的拼音字符串
        try {
            // \\u4E00是unicode编码，判断是不是中文
            if (Character.toString(input[0]).matches("[\\u4E00-\\u9FA5]+")) {
                // 将汉语拼音的全拼存到temp数组
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(
                        input[0], format);

                // 取拼音的第一个字母
                output = temp[0];

            }else if(Character.isLowerCase(input[0])){
                output = (input[0]+"").toUpperCase();
            }else{
                output = Character.toString(input[0]);
            }

        } catch (Exception e) {
            Log.e("tpnet","转换拼音错误"+e.toString());
        }

        return output;
    }

}
