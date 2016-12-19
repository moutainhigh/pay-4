package com.pay.acc.common.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.common.utils.PayMaApiUtils;

/**
 * 提现金额配置
 * 
 * @author jerry_jin
 *
 */
public class WithdrawalsConfig {
	
	private static Log log = LogFactory.getLog(WithdrawalsConfig.class);

	private static WithdrawalsConfig instance = null;
	
	private static void init(){
		log.info("开始初始化提现金额配置 -- /config/withdrawalsrule.properties");
		Properties prop = new Properties();
		InputStream is = PayMaApiUtils.class.getResourceAsStream("/config/withdrawalsrule.properties");
		try {
			prop.load(is);
			instance = new WithdrawalsConfig();
			instance.setWhiteDate(Long.parseLong(prop.getProperty("white.withdrawals.date")));
			log.info("加载white.withdrawals.date="+instance.getWhiteDate());
			instance.setWhiteRule(Integer.parseInt(prop.getProperty("white.withdrawals.rule")));
			log.info("加载white.withdrawals.rule="+instance.getWhiteRule());
			instance.setBlackDate(Long.parseLong(prop.getProperty("black.withdrawals.date")));
			log.info("加载black.withdrawals.date="+instance.getBlackDate());
			instance.setBlackRule(Integer.parseInt(prop.getProperty("black.withdrawals.rule")));
			log.info("加载black.withdrawals.rule ="+instance.getBlackRule());
			
			instance.setCommonDate(Long.parseLong(prop.getProperty("common.withdrawals.date")));
			log.info("加载common.withdrawals.date="+instance.getCommonDate());
			instance.setCommonRule(Integer.parseInt(prop.getProperty("common.withdrawals.rule")));
			log.info("加载common.withdrawals.rule ="+instance.getCommonRule());		
			
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
			log.info("初始化提现金额配置结束");
		}
	}
	
	public static WithdrawalsConfig getInstance(){
		if(instance==null){
			init();
		}
		return instance;
	}
	
	private WithdrawalsConfig(){
		
	}
	
	/**
	 * 白名单提现 天数
	 */
	private long whiteDate = 1;
	

	/**
	 *  白名单提现规则
	 */
	private int whiteRule=1;
	
	/**
	 * 黑名单提现 天数
	 */
	private long blackDate=1;
	
	/**
	 * 黑名单提现规则
	 */
	private int blackRule=1;
	
	/**
	 * 黑名单提现 天数
	 */
	private long commonDate=14;
	
	/**
	 * 黑名单提现规则
	 */
	private int commonRule=3;
	
	public long getCommonDate() {
    	return commonDate;
    }

	public void setCommonDate(long commonDate) {
    	this.commonDate = commonDate;
    }

	public int getCommonRule() {
    	return commonRule;
    }

	public void setCommonRule(int commonRule) {
    	this.commonRule = commonRule;
    }

	public long getWhiteDate() {
    	return whiteDate;
    }

	public void setWhiteDate(long whiteDate) {
    	this.whiteDate = whiteDate;
    }

	public int getWhiteRule() {
    	return whiteRule;
    }

	public void setWhiteRule(int whiteRule) {
    	this.whiteRule = whiteRule;
    }

	public long getBlackDate() {
    	return blackDate;
    }

	public void setBlackDate(long blackDate) {
    	this.blackDate = blackDate;
    }

	public int getBlackRule() {
    	return blackRule;
    }

	public void setBlackRule(int blackRule) {
    	this.blackRule = blackRule;
    }

	public static void main(String[] args){
		WithdrawalsConfig config = WithdrawalsConfig.getInstance();
		WithdrawalsConfig config1 = WithdrawalsConfig.getInstance();
		WithdrawalsConfig config2 = WithdrawalsConfig.getInstance();
	}
	
}
