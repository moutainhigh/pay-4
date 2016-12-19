package com.pay.poss.featuremenu.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CheckUtil {
	
	
	/**
	 * 校验登录密码 ，长度在8-20位，
	 * @param loginPwd
	 * @return
	 */
	public static boolean checkLoginPwd(String loginPwd){
		if(null==loginPwd){
			return false;
		}
		Pattern p = Pattern.compile("^(?![0-9]+$)[a-zA-Z0-9]{8,20}$");
		return p.matcher(loginPwd).matches()&&loginPwd.length()>=8&&loginPwd.length()<=32;
	}
	
	/**
	 * 支付密码校验，长度8-20，必须字母数字混合
	 * @param payPwd
	 * @return
	 */
	public static boolean checkPayPwd(String payPwd){
		if(null == payPwd){
			return false;
		}
		Pattern p = Pattern.compile("^(?![a-zA-Z]+$)(?![0-9]+$)[a-zA-Z0-9]{8,20}$");
		return p.matcher(payPwd).matches();
	}
	
	//=====================判断邮件email是否正确格式
	public static boolean checkEmail(String email) {
        boolean flag = false;
         String check = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"; 
         Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(email.trim());
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }



	//=====================判断手机号phone是否正确格式
    public static boolean checkPhone(String phone){
        Pattern p =Pattern.compile("^(13|15|18)\\d{9}$");
        Matcher matcher = p.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
    
    
    public static boolean isContainsChinese(String s){
    	Pattern p =Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = p.matcher(s);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
    
  //检查字符串是否为数字
    public static boolean isNumber(String str)
       {
        if(StringUtils.isEmpty(str))
            return false;
           java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
           java.util.regex.Matcher match=pattern.matcher(str);
           if(match.matches()==false)
           {
                return false;
           }
           else
           {
                return true;
           }
       }

    public static void main(String[] args) {
		//System.out.println(CheckUtil.isContainsChinese("d425345阿斯顿飞"));
    	//System.out.print(CheckUtil.checkEmail("a111@staff.com.cn"));
//        System.out.print(CheckUtil.isNumber(""));
//        System.out.print(CheckUtil.checkLoginPwd("aaaaaaaaaaaaaaaaaaaa"));
//        System.out.print(CheckUtil.checkPayPwd("asda1111111111111111"));
        System.out.println(CheckUtil.checkPhone("15221155718"));
    }


}
