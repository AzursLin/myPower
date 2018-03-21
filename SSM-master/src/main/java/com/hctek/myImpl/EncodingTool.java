package com.hctek.myImpl;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/3/20.
 */
public class EncodingTool {
    public static String encodeStr(String str) {
        return  str;
        /*try {
            if (str == null) {
                return null;
            } else {
                return new String(str.getBytes("ISO-8859-1"), "UTF-8");
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    public static Date vueStrToDate(String str) {
        try {
            if (str == null) {
                return null;
            } else {
                str = str.replace("Z"," UTC");
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
                return format.parse(str);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
