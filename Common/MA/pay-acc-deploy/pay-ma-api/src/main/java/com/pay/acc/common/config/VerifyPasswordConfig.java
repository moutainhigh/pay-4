package com.pay.acc.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.common.utils.PayMaApiUtils;

/**
 * 验证密码配置
 * 
 * @author jerry_jin
 *
 */
public class VerifyPasswordConfig {
	
	private static Log log = LogFactory.getLog(VerifyPasswordConfig.class);

	private static VerifyPasswordConfig instance = null;
	
	private static void init(){
		log.info("开始初始化验证密码配置 -- /config/verifypwd.properties");
		Properties prop = new Properties();
		InputStream is = PayMaApiUtils.class.getResourceAsStream("/config/verifypwd.properties");
		try {
			prop.load(is);
			instance = new VerifyPasswordConfig();
			instance.setPayTotalCount(Integer.parseInt(prop.getProperty("pay.totalcount")));
			log.info("加载pay.totalcount="+instance.getPayTotalCount());
			instance.setPayFrozenMinute(Integer.parseInt(prop.getProperty("pay.frozenminute")));
			log.info("加载pay.frozenminute="+instance.getPayFrozenMinute());
			instance.setLoginTotalCount(Integer.parseInt(prop.getProperty("login.totalcount")));
			log.info("加载login.totalcount="+instance.getLoginTotalCount());
			instance.setLoginFrozenMinute(Integer.parseInt(prop.getProperty("login.frozenminute")));
			log.info("加载login.frozenminute="+instance.getLoginFrozenMinute());
			instance.setSafeQuestion4LoginFrozenMinute(Integer.parseInt(prop.getProperty("safequestion.login.frozenminute")));
			log.info("加载safequestion.login.frozenminute＝"+instance.getSafeQuestion4LoginFrozenMinute());
			instance.setSafeQuestion4LoginTotalCount(Integer.parseInt(prop.getProperty("safequestion.login.totalcount")));
			log.info("加载safequestion.login.totalcount="+instance.getSafeQuestion4LoginTotalCount());
			instance.setSafeQuestion4PayFrozenMinute(Integer.parseInt(prop.getProperty("safequestion.pay.frozenminute")));
			log.info("加载safequestion.pay.frozenminute="+instance.getSafeQuestion4PayFrozenMinute());
			instance.setSafeQuestion4PayTotalCount(Integer.parseInt(prop.getProperty("safequestion.pay.totalcount")));
			log.info("加载safequestion.pay.totalcount="+instance.getSafeQuestion4PayTotalCount());
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(is!=null && is.available()!=0){
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			log.info("初始化验证密码配置结束");
		}
	}
	
	public static VerifyPasswordConfig getInstance(){
		if(instance==null){
			init();
		}
		return instance;
	}
	
	private VerifyPasswordConfig(){
		
	}
	
	/**
	 * 登录密码－允许错误总次数
	 */
	private int loginTotalCount = 0;
	
	/**
	 * 登录密码－冻结时间（单位：分钟)
	 */
	private int loginFrozenMinute = 0;
	
	/**
	 * 支付密码－允许错误总次数
	 */
	private int payTotalCount = 0;
	
	/**
	 *  支付密码－冻结时间（单位：分钟)
	 */
	private int payFrozenMinute = 0;
	
	/**
	 * 登录密码安全问题－允许错误总次数
	 */
	private int safeQuestion4LoginTotalCount = 0;
	
	/**
	 * 登录密码安全问题－冻结时间（单位：分钟）
	 */
	private int safeQuestion4LoginFrozenMinute = 0;
	
	/**
	 * 支付密码安全问题－允许错误总次数
	 */
	private int safeQuestion4PayTotalCount = 0;
	
	/**
	 * 支付密码安全问题－冻结时间（单位：分钟）
	 */
	private int safeQuestion4PayFrozenMinute = 0;

	public int getLoginTotalCount() {
		return loginTotalCount;
	}

	private void setLoginTotalCount(int loginTotalCount) {
		this.loginTotalCount = loginTotalCount;
	}

	public int getLoginFrozenMinute() {
		return loginFrozenMinute;
	}

	private void setLoginFrozenMinute(int loginFrozenMinute) {
		this.loginFrozenMinute = loginFrozenMinute;
	}

	public int getPayTotalCount() {
		return payTotalCount;
	}

	private void setPayTotalCount(int payTotalCount) {
		this.payTotalCount = payTotalCount;
	}

	public int getPayFrozenMinute() {
		return payFrozenMinute;
	}

	private void setPayFrozenMinute(int payFrozenMinute) {
		this.payFrozenMinute = payFrozenMinute;
	}
	
	public int getSafeQuestion4LoginTotalCount() {
		return safeQuestion4LoginTotalCount;
	}

	public int getSafeQuestion4LoginFrozenMinute() {
		return safeQuestion4LoginFrozenMinute;
	}

	public int getSafeQuestion4PayTotalCount() {
		return safeQuestion4PayTotalCount;
	}

	public int getSafeQuestion4PayFrozenMinute() {
		return safeQuestion4PayFrozenMinute;
	}

	private void setSafeQuestion4LoginTotalCount(int safeQuestion4LoginTotalCount) {
		this.safeQuestion4LoginTotalCount = safeQuestion4LoginTotalCount;
	}

	private void setSafeQuestion4LoginFrozenMinute(int safeQuestion4LoginFrozenMinute) {
		this.safeQuestion4LoginFrozenMinute = safeQuestion4LoginFrozenMinute;
	}

	private void setSafeQuestion4PayTotalCount(int safeQuestion4PayTotalCount) {
		this.safeQuestion4PayTotalCount = safeQuestion4PayTotalCount;
	}

	private void setSafeQuestion4PayFrozenMinute(int safeQuestion4PayFrozenMinute) {
		this.safeQuestion4PayFrozenMinute = safeQuestion4PayFrozenMinute;
	}

	public static void main(String[] args){
		VerifyPasswordConfig config = VerifyPasswordConfig.getInstance();
		VerifyPasswordConfig config1 = VerifyPasswordConfig.getInstance();
		VerifyPasswordConfig config2 = VerifyPasswordConfig.getInstance();
	}
	
}
