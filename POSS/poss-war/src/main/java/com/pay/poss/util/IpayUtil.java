package com.pay.poss.util;

import com.pay.poss.ipayconst.IpayConst;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

/**
 * 通用共通
 * Created by ZHW on 2015/5/11.
 */
public class IpayUtil {

    private static Logger logger = Logger.getLogger(IpayUtil.class);

    /**
     * 转换为小写
     *
     * @param obj
     * @return
     */
    public static String toLower(String obj) {
        if (obj == null) {
            return IpayConst.EMPTY;
        } else {
            return obj.toLowerCase().trim();
        }
    }

    /**
     * 转换为大写
     *
     * @param obj
     * @return
     */
    public static String toUpper(String obj) {
        if (obj == null) {
            return IpayConst.EMPTY;
        } else {
            return obj.toUpperCase().trim();
        }
    }

    /**
     * 转换为String
     *
     * @param obj
     * @return
     */
    public static String toStr(Object obj) {
        if (obj == null) {
            return StringUtils.EMPTY;
        } else {
            return obj.toString().trim();
        }
    }

    /**
     * 转换为Long
     *
     * @param obj
     * @return
     */
    public static Long toLong(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).longValue();
        } else {
            return Long.parseLong(obj.toString().trim());
        }
    }

    /**
     * 转换为Int
     *
     * @param obj
     * @return
     */
    public static Integer toInt(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).intValue();
        } else {
            return Integer.parseInt(obj.toString().trim());
        }
    }

    /**
     * 转换为Double
     *
     * @param obj
     * @return
     */
    public static Double toDouble(Object obj) {
        if (obj == null) {
            return 0.0;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).doubleValue();
        } else {
            try {
                return Double.parseDouble(obj.toString().trim());
            } catch (Exception e) {
                return 0.0;
            }
        }
    }

    /**
     * 转换为BigDecimal
     *
     * @param obj
     * @return
     */
    public static BigDecimal toBigDecimal(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return (BigDecimal) obj;
        } else {
            return new BigDecimal(Double.parseDouble(obj.toString().trim()));
        }
    }

    /**
     * 转换为Date
     *
     * @param obj
     * @return
     */
    public static Date toDate(Object obj) {
        if (obj == null) {
            return null;
        } else {
            try {
                return ((Date) obj);
            } catch (Exception e) {
                return null;
            }
        }
    }

    /**
     * 转换为UTF8
     *
     * @param str
     * @return
     */
    public static String toUTF8(String str) {

        if (StringUtils.length(str) < 1) {
            return str;
        }

        try {
            String utf8Str = new String(str.getBytes(), IpayConst.CHARSET_UTF8);
            return utf8Str;
        } catch (UnsupportedEncodingException e) {
            return str;
        }
    }

    /**
     * 判断是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(Object a, Object b) {

        if (a == b) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else if (a.equals(b)) {
                return true;
            } else {
                return a.toString().equals(b.toString());
            }
        }
    }

    /**
     * 获取地域信息
     *
     * @return
     */
    public static String getLocale() {
        return Locale.getDefault().toString();
    }

    /**
     * 判断是否空集合
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> boolean isEmptyList(List<T> list) {
        if (list == null) {
            return true;
        } else if (list.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否空Map
     *
     * @param map
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> boolean isEmptyMap(Map<T, V> map) {
        if (map == null) {
            return true;
        } else if (map.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否空数组
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> boolean isEmptyArray(T[] array) {
        if (array == null) {
            return true;
        } else if (array.length == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 转换为Byte
     *
     * @param obj
     * @return
     */
    public static Byte toByte(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof BigDecimal) {
            return ((BigDecimal) obj).byteValue();
        } else {
            DecimalFormat format = new DecimalFormat("0000000000000000000");
            try {
                return format.parse(obj.toString().trim()).byteValue();
            } catch (ParseException e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    /**
     * 获取第几页
     *
     * @param length
     * @param unit
     * @return
     */
    public static int getUnitCount(int length, int unit) {
        if (length % unit == 0) {
            return length / unit;
        } else {
            return length / unit + 1;
        }
    }

    /**
     * 获取Like查询字符串
     *
     * @param str
     * @return
     */
    public static String getLikeStr(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("%");
        sb.append(IpayUtil.toStr(str));
        sb.append("%");

        return sb.toString();
    }

    /**
     * 格式化金额
     *
     * @param amount
     * @return
     */
    public static String getAmount(Object amount) {
        NumberFormat formatter = new DecimalFormat("###,###,###,##0.00");
        try {
            return formatter.format(amount);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return "0.00";
        }
    }


    /**
     * 获取字符串的Byte数组
     *
     * @param obj
     * @param charset
     * @return
     */
    public static byte[] toByteArray(Object obj, String charset) {
        if (obj == null) {
            return null;
        } else {
            try {
                return obj.toString().trim().getBytes(charset);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return null;
            }
        }
    }

    /**
     * 暂停
     *
     * @param millis
     */
    public static void sleep(long millis) {
        if (millis < 1) {
            millis = 1;
        } else {
            try {
                Thread.sleep(millis);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 将字符串分解为数组
     *
     * @param str
     * @param sign
     * @return
     */
    public static String[] toArray(String str, String sign) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            String[] strArray = str.trim().split(sign);
            List<String> result = new ArrayList<String>();

            for (int i = 0; i < strArray.length; ++i) {
                if (!StringUtils.isBlank(strArray[i])) {
                    result.add(strArray[i]);
                }
            }

            return result.toArray(new String[result.size()]);
        }
    }

    /**
     * 复制对象到Map
     * @param src
     * @param target
     */
    public static void copyPropertys(Object src, Map<String, Object> target){
        if(null != src){
            if (target == null) {
                target = new HashMap<String, Object>();
            }

            BeanWrapper beanWrapper = new BeanWrapperImpl(src);
            PropertyDescriptor[] descriptor = beanWrapper.getPropertyDescriptors();
            for (int i = 0; i < descriptor.length; i++) {
                String key = descriptor[i].getName();
                if(!key.equals("class")){
                    target.put(key, beanWrapper.getPropertyValue(key));
                }
            }
        }
    }

    /**
     * 复制Map到对象
     * @param src
     * @param target
     */
    public static void copyPropertys(Map<String, Object> src, Object target){
        if(null != src && null != target){
            BeanWrapper beanWrapper = new BeanWrapperImpl(target);
            beanWrapper.setPropertyValues(src);
        }
    }

    

}
