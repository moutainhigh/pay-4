/**
 *  File: ParamUtil.java
 *  Description:
 *  Copyright © 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2010-7-13   terry_ma     Create
 *
 */
package com.pay.app.controller.common.pagination;

import javax.servlet.http.HttpServletRequest;

public class ParamUtil {
    public ParamUtil() {
    }

    /**
     * 跟据参数名,从request对象中获取值,若为空,则返回为空
     * 
     * @param request
     *            HttpServletRequest对象
     * @param paramName
     *            参数名
     * @return String
     */
    public static String getString(HttpServletRequest request, String paramName) {
        String temp = request.getParameter(paramName);
        if (temp != null && !temp.equals("")) {
            return temp;
        } else {
            return null;
        }
    }

    /**
     * 跟据参数名,从request对象中获取值,若为空,则返回为传入的默认值
     * 
     * @param request
     *            HttpServletRequest对象
     * @param paramName
     *            参数名
     * @param defaultString
     *            若参数取值为空,返回为传入的默认值
     * @return String
     */
    public static String getString(HttpServletRequest request,
            String paramName, String defaultString) {
        String temp = getString(request, paramName);
        if (temp == null) {
            temp = defaultString;
        }
        return temp;
    }

    /**
     * 跟据参数名,从request对象中获取值,若为空,则抛出NumberFormatException
     * 
     * @param request
     *            HttpServletRequest对象
     * @param paramName
     *            参数名
     * @return int
     * @throws NumberFormatException
     */
    public static int getInt(HttpServletRequest request, String paramName)
            throws NumberFormatException {
        return Integer.parseInt(getString(request, paramName));
    }

    /**
     * 跟据参数名,从request对象中获取值,若为空,则返回传入的defaultInt,若补获NumberFormatException，则返回为0
     * 
     * @param request
     *            HttpServletRequest对象
     * @param paramName
     *            参数名
     * @param defaultInt
     * @return int
     */
    public static int getInt(HttpServletRequest request, String paramName,
            int defaultInt) {
        try {
            String temp = getString(request, paramName);
            if (temp == null) {
                return defaultInt;
            } else {
                return Integer.parseInt(temp);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 跟据参数名,从request对象中获取值,若为空,则抛出NumberFormatException
     * 
     * @param request
     *            HttpServletRequest对象
     * @param paramName
     *            参数名
     * @return long
     * @throws NumberFormatException
     */
    public static long getLong(HttpServletRequest request, String paramName)
            throws NumberFormatException {
        return Long.parseLong(getString(request, paramName));
    }

    /**
     * 跟据参数名,从request对象中获取值,若为空,则返回传入的defaultLong,若补获NumberFormatException，则返回为0
     * 
     * @param request
     *            HttpServletRequest对象
     * @param paramName
     *            参数名
     * @param defaultLong
     * @return long
     */
    public static long getLong(HttpServletRequest request, String paramName,
            long defaultLong) {
        try {
            String temp = getString(request, paramName);
            if (temp == null) {
                return defaultLong;
            } else {
                return Long.parseLong(temp);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
