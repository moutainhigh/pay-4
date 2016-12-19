/**
 * 
 */
package com.pay.wechat.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author PengJiangbo
 *
 */
public class LoadPropertiesUtil {
	
	private static String touser = "" ;
	private static String template_id = "" ;
	
	static{
		Properties prop = new Properties();   
        InputStream is = LoadPropertiesUtil.class.getResourceAsStream("/properties/message.properties");  
        try {   
            prop.load(is);   
            touser = prop.getProperty("touser").trim();   
            template_id = prop.getProperty("template_id").trim();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
	}
	
	//构造器私有化
	private LoadPropertiesUtil() {

	}

	public static String getTouser(){
		return touser ;
	}
	
	public static String getTemplateId(){
		return template_id ;
	}
	
	public static void main(String[] args) {
		System.out.println(getTouser());
		System.out.println(getTemplateId());
	}
	
}
