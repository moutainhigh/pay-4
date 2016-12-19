package com.pay.fundout.withdraw.service.autoriskcontrol;

public interface AutoRiskControlService {

	/**
	 * 反洗钱监控-24小时内企业会员账户出款到银行或体现累计超过200万
	 * @param payerMemberCode
	 * @return
	 */
	public String validateFundoutAmount(Long payerMemberCode);
	
	/**
	 * 系统交易类监控-交易类预警-企业24小时内向同一银行账户出款超过5笔
	 * @param payerMemberCode
	 * @return
	 */
	public String validatePayeeBankAcc(Long payerMemberCode, String payeeBankAccCode);
	
	/**
	 * 系统交易类监控-转账类预警-同一企业账户24小时内收到超过10次个人账户的转账
	 * @param payerMemberCode
	 * @return
	 */
	public String validateReceivedPersonAccTransferTimes(Long payerMemberCode);
	
	/**
	 * 系统交易类监控-转账类预警-同一企业账户24小时内收到个人账户的转账超过20万
	 * @param payerMemberCode
	 * @return
	 */
	public String validateReceivedPersonAccTransferAmount(Long payerMemberCode);
	
	/**
	 * 系统交易类监控-充值类预警-24小时内企业会员账户充值次数超10次【包括10次】
	 * @param payerMemberCode
	 * @return
	 */
	public String validateDepositNum(Long payerMemberCode);
	
	/**
	 * 系统交易类监控-充值类预警-24小时内会员账户单笔充值金额达50000元【包括50000元】
	 * @param payerMemberCode
	 * @return
	 */
	public String validateDepositAmount(Long payerMemberCode);
	
	/**
	 * 账户安全类-同一账户出款前半小时累计登录错误三次
	 * @param payerMemberCode
	 * @return
	 */
	public String validateAccountSecurity(Long payerMemberCode);
}
