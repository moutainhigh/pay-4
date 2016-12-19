package com.pay.app.cidverify.cid2gov;

/**
 * 实名认证服务代理接口
 * @author lei.jiangl 
 * @version 
 * @data 2010-9-12 下午03:05:54
 */
public interface ServiceInf{
	
	/**
	 * 获取公安网的服务条件模板
	 * @param inLicense 客户身份校验文件
	 * @return
	 * @throws Exception
	 */
	public String nciicGetCondition(String inLicense) throws Exception;

	/**
	 * 根据身份文件以及条件进行实名认证
	 * @param inLicense 客户身份校验文件
	 * @param inConditions 服务条件模板
	 * @return
	 */
	public String nciicCheck(String inLicense, String inConditions);
	
}



	