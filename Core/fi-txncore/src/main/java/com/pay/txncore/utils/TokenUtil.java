package com.pay.txncore.utils;

import com.pay.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cuber on 2016/9/18.
 */
public class TokenUtil {
    private static String ZERO="0000000";
    private static Date YEAR2015 = new Date();
    static {
        try {
            YEAR2015 = new SimpleDateFormat("yyyyMMdd").parse("20150101");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static String genToken( String tradeOrderNo, String cardHolderNumber){
        if(cardHolderNumber != null
                && cardHolderNumber.length() > 6
                && tradeOrderNo != null
                && tradeOrderNo.length() > 6){
            String cardBin = cardHolderNumber.substring(0, 6);
            String changePartCardBin = cardBin.substring(2);
            String tokenHead = cardBin.substring(0,2) + getNumber427(changePartCardBin);
            String tokenBody = getTokenBody(tradeOrderNo);
            String tokenConText = tokenHead + tokenBody;
            return tokenConText + Luhn.getLastNumber(tokenConText);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(genToken("1021507081658085574","6222620110013161246"));
    }

    private static String getTokenBody(String tradeOrderNo){
        long timeAfter = new Date().getTime() - YEAR2015.getTime();
        long tranTime = Long.highestOneBit(timeAfter) << 1 | timeAfter;
        String returnValue  = String.valueOf(tranTime);
        System.out.println(returnValue);
        int count = returnValue.length();
        if(count < 14){
            returnValue += tradeOrderNo.substring(tradeOrderNo.length() - (14 - count));
        }
        return returnValue;
    }


    private static String getNumber427(String changePartCardBin){
        if(StringUtil.isNumber(changePartCardBin)){
            long finalNumber = 0l;
            for (int i = 0; i < changePartCardBin.length(); i++){
                int leftInt = changePartCardBin.length() - i;
                int number  = Integer.parseInt(changePartCardBin.charAt(i) + "");
                long afterNumber = (long)Math.pow(number,leftInt);
                if(i == 0){
                    afterNumber = get3Number(afterNumber);
                }
                finalNumber = finalNumber + (long)(afterNumber * Math.pow(10, leftInt - 1));
            }
            System.out.println(finalNumber);
            return finalNumber + "";
        }
        return ZERO;
    }

    private static long get3Number(long number){
        if(number / 1000 >= 1){
            return number;
        }else{
            return get3Number(number << 1);
        }
    }
}
