package com.pay.txncore.utils;

import com.pay.util.StringUtil;

/**
 * Created by cuber on 2016/9/19.
 */
public class Luhn {
    public static int getLastNumber(String context){
        if((!StringUtil.isEmpty(context))&& StringUtil.isNumber(context)){
            int sum = 0;
            for (int i = context.length() - 1; i >= 0; i=i-2){
                int curValue = Integer.parseInt(context.charAt(i) + "") * 2;
                sum += curValue >= 10 ? curValue-9 : curValue;
            }

            for (int i = context.length() - 2; i >= 0; i=i-2){
                int curValue = Integer.parseInt(context.charAt(i) + "");
                sum += curValue;
            }
            int padding10 = sum % 10;

            return 10 - (padding10 == 0 ? 10 : padding10 );
        }
        return -1;
    }

    public static boolean legalCardNo(String cardNo){
        if((!StringUtil.isEmpty(cardNo))&& StringUtil.isNumber(cardNo)){
            return getLastNumber(cardNo.substring(0, cardNo.length() - 1))
                    == Integer.parseInt(cardNo.charAt(cardNo.length() - 1) + "");
        }
        return false;
    }

}
