/**
 * 
 */
package com.pay.fo.order.service.validate;

import com.pay.fundout.service.ma.MemberInfo;

/**
 * @author NEW
 *
 */
public interface PaymentValidateService {
	/**
	 * 验证支付密码，返回详细信息
	 * @param memberCode
	 * @param accountType
	 * @param pwd
	 * @return
	 * @throws ValidatePaymentPasswordException
	 */
	String validatePaymentPassword(long memberCode,int accountType,String pwd);
	
	/**
	 * 验证支付密码，返回详细信息
	 * @param memberCode
	 * @param accountType
	 * @param op
	 * @param pwd
	 * @return
	 * @throws ValidatePaymentPasswordException
	 */
	String validatePaymentPassword(long memberCode,int accountType,Long op,String pwd);
	
	/**
	 * 验证收款方会员信息
	 * @param loginName
	 * @param payerMemberCode
	 * @return
	 * @
	 */
	String validatePayeeMemberInfo(String loginName, String payerMemberCode) ;
	
	
	/**
	 * 验证收款方会员信息
	 * @param payeeLoginName
	 * @param payeeName
	 * @param payerMemberCode
	 * @return
	 * @
	 */
	String validatePayeeMemberInfo(String payeeLoginName,String payeeName, String payerMemberCode);
	/**
	 * 验证收款方会员信息
	 * @param payee
	 * @param payerMemberCode
	 * @return
	 * @
	 */
	String validatePayeeMemberInfo(MemberInfo payee, String payerMemberCode) ;
	
	/**
	 * 验证收款方会员信息
	 * @param payee
	 * @param payeeName
	 * @param payerMemberCode
	 * @return
	 * @
	 */
	String validatePayeeMemberInfo(MemberInfo payee,String payeeName , String payerMemberCode) ;
	
	/**
	 * 验证收款方账号信息
	 * @param memberCode
	 * @param acctType
	 * @return
	 * @
	 */
	String validatePayeeAcctInfo(long memberCode, int acctType) ;
	
	/**
	 * 验证收款方账号信息
	 * @param loginName
	 * @param acctType
	 * @return
	 * @
	 */
	String validatePayeeAcctInfo(String loginName,int acctType) ;
	
	/**
	 * 验证付款方会员信息
	 * @param memberCode
	 * @return
	 * @
	 */
	String validatePayerMemberInfo(long memberCode) ;
	
	/**
	 * 验证付款方会员信息
	 * @param paye
	 * @return
	 * @
	 */
	String validatePayerMemberInfo(MemberInfo payer) ;
	
	/**
	 * 验证付款方账号信息
	 * @param memberCode
	 * @param acctType
	 * @return
	 * @
	 */
	String validatePayerAcctInfo(long memberCode, int acctType);
	
	/**
	 * 验证收款方银行账号信息
	 * @param bankName     银行名称
	 * @param bankAcctCode 银行代码
	 * @param bankCode     银行机构号
	 * @param busiType     业务类型(订单类型)
	 * @param fundoutMode  出款方式
	 * @return
	 * @
	 */
	String validatePayeeBankAcctInfo(String bankName,String bankAcctCode, String bankCode,Integer busiType,Integer fundoutMode) ;
	/**
	 * 验证付款方余额信息
	 * @param paymentAmount 付款金额
	 * @param isPayerPayFee 是否是付款方付手续费
	 * @param fee           手续费
	 * @param memberCode    付款方会员号
	 * @param acctType      付款方账户类型
	 * @return
	 */
	String validatePayerBanlance(long paymentAmount,int isPayerPayFee,long fee,long memberCode,int acctType);
	
	/**
	 * 验证是否允许银行出款
	 * @param bankCode
	 * @param busiType
	 * @param fundoutMode
	 * @return
	 */
	boolean isAllowBankFundout(String bankCode,Integer busiType,Integer fundoutMode);
	
}
