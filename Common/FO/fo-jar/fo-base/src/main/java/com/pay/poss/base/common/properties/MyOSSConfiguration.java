package com.pay.poss.base.common.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author yanshichuan
 * 
 */
public class MyOSSConfiguration {
	
	private final static transient Log log = LogFactory.getLog(MyOSSConfiguration.class);
	
	private final static Properties properties  = new Properties() ;
	
	static void init(){
		FileInputStream fileInputStream = null;
		try{
			StringBuilder stringBuilder = new StringBuilder();
			String separator = File.separator;
			stringBuilder.append(separator).append("opt").append(separator).append("pay").append(separator).append("env").append(separator)
			.append("fstoken.properties");
			fileInputStream = new FileInputStream(new File(stringBuilder.toString()));
			properties.load(fileInputStream);
		}catch (Exception e) {
			log.error("-----------------加载properties失败---------------"+e.getMessage());
		}finally{
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					fileInputStream = null;
				}
			}
		}
	}
	
	static{
		init();
	}
	
	/**
	 * 获取value值
	 * @param key
	 * @return
	 * @author yanshichuan
	 * @param <E>
	 * @see
	 */
	public  static String getStrProperties(String key) {
		String value = properties.getProperty(key);
		if(value == null ) {
			value = "";
		}
		return value ;
	}
	/**
	 * 获取value值
	 * @param key
	 * @return
	 * @author yanshichuan
	 * @param <E>
	 * @see
	 */
	public static Integer getIntProperties(String key) {
		String value = properties.getProperty(key);
		if(value == null ) {
			value = "10";
		}
		return Integer.valueOf(value) ;
	}
	
	
}
