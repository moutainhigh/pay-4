package com.pay.app.hesssion.service;


/**
 * 
 * pay对外hession接口
 * @author ddr
 *
 */
public interface PayHessionService {

	//测试接口
	String sayHello();
	
	/**
	 * 公安网认证接口
	 * @param jsonString  {idCardNO:#idCardNO#,name:#name#};
	 * 标准json串，idCardNO身份证，name 姓名
	 * @return 如果是正确返回1，错误返回0，异常情况返回-1
	 */
	String cidVerify(String jsonString);
}
