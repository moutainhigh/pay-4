/**
 * 
 */
package com.pay.acc.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author wolf_huang 
 * 
 * @date 2010-7-20
 */
public class PayMaApiUtils {

	private static Log log = LogFactory.getLog(PayMaApiUtils.class);
	private static Properties properties;
	static {
		properties = readProperties();
	}

	public static long convertAcctCode(long memberCode, int acctType) {
		StringBuilder sb = new StringBuilder(String.valueOf(memberCode));
		sb.append(String.valueOf(acctType));
		return Long.parseLong(sb.toString());
	}

	private static Properties readProperties() {
		InputStream is = PayMaApiUtils.class.getResourceAsStream("/config/acct.properties");
		Properties p = new Properties();
		try {
			p.load(is);

		} catch (IOException e) {
			log.error("读取配置文件出错", e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				log.error("关闭配置文件出错", e);
			}
		}
		return p;
	}

	public static String getProperties(String key) {
		if (properties == null) {
			log.error("读取配置文件出错");
		}
		if (key == null || key.length() == 0) {
			log.error("输入的key不能为空或者是空字符");
			return null;
		} else {
			return properties.getProperty(key);
		}
	}

}
