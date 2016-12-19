package com.pay.acc.service.account;

import com.pay.acc.service.account.dto.MaResultDto;

/**
 * 安全问题验证
 * @author jin
 *
 */
public interface SafeQuestionVerifyService {

	static final String ORIGIN = "SafeQuestionVerify";

	/**
	 * 验证
	 * @param memberCode	会员id
	 * @param questionId	安全问题id
	 * @param answer		安全问题答案
	 * @return MaResultDto ：
	 * 	<li>	resultStatus 
	 * 	<ul>		1:成功（并解锁）:</ul>
	 * 	<ul>		2:小于指定次数</ul>
	 * 	<ul>		3:大于等于指定次数（锁定）</ul>
	 * 	<ul>		4:会员不存在</ul>
	 * 	<ul>		5:运行时异常</ul>
	 *  <ul>		* 当返回值为5时，errorMsg异常信息，
	 *	<li>	errorCode 错误编号</li>
	 * 	<li>	errorMsg  错误信息</li>
	 * <li>	Object   object
	 * <ul>返回值1,   object返回对象 Member</ul>
	 * <ul>返回值2-3 object返回对象: VerifyResultDto:
	 * 				leavingTime 剩余次数	,
	 * 				totalTime 总的次数,
	 * 				leavingMinute 剩余分钟</ul>
	 * <ul>返回值4,5 object都为null</ul></li>
	 * @return
	 */
	public MaResultDto doVerify(long memberCode,int questionId,String answer);
	
}
