package com.pay.pe.accumulated.chargeback.service;

public interface TokenOnFactory {

	/**
	* @Title: getChargeBackService
	* @Description: 1-付款方;2-收款方; 工厂类
	* @param @param takenOn
	* @param @return    设定文件
	* @return ChargeBackService    返回类型
	* @throws
	*/ 
	public ChargeBackService getChargeBackService(int takenOn);
}
