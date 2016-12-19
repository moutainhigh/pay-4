package com.pay.acc.identityverify;

import java.util.Map;

/**
 * 实名认证服务接口
 */
public interface IdentityVerifyService {

	/**
	 * http 请求调用
	 * @param params 请求参数
	 */
	String invoke(Map<String, String> params);

	/**
	 * 处理响应
	 * @param response 响应报文
	 */
	String handleResponse(String response);

	/**
	 * 根据姓名及身份证号生成请求参数
	 * @param name 姓名
	 * @param cardNo 身份证号
	 * @return
	 */
	Map<String, String> generateReqParams(String name, String cardNo);

	/**
	 * 实名认证
	 * @param name 姓名
	 * @param cardNo 身份证号
	 * @return true - 认证成功 false - 认证失败
	 */
	boolean validate(String name, String cardNo);
}